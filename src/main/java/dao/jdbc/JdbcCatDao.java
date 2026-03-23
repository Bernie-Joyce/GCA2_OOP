package dao.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CatDao;
import domain.Cat;
import domain.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


public class JdbcCatDao implements CatDao {
    private final String _url;
    private final String _user;
    private final String _pass;
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public JdbcCatDao(String url, String user, String pass) {
        if (url == null || url.isBlank())
            throw new IllegalArgumentException("url is required");

        _url = url.trim();
        _user = user;
        _pass = pass;
    }

    private Connection open() throws SQLException {
        return DriverManager.getConnection(_url, _user, _pass);
    }

    private static Cat mapRow(ResultSet rs) throws SQLException {
        int Id = rs.getInt("id");
        int OwnerId = rs.getInt("OwnerId");
        String Name = rs.getString("Name");
        Gender gender = Gender.valueOf(rs.getString("Gender").toUpperCase());
        String Breed = rs.getString("Breed");
        Date DateOfBirth = rs.getDate("DateOfBirth");
        String Color = rs.getString("Color");
        String IdentifyingMarkings = rs.getString("IdentifyingMarkings");

        return new Cat(Id, OwnerId, Name, gender, Breed, DateOfBirth, Color, IdentifyingMarkings);
    }

    @Override
    public int insert(Cat cat) throws Exception {
        if (cat.getName() == null || cat.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (cat.getGender() == null) {
            throw new IllegalArgumentException("Gender is required");
        }
        if (cat.getColour() == null || cat.getColour().isBlank()) {
            throw new IllegalArgumentException("Color is required");
        }
        if (cat.getIdentifyingMarkings() == null || cat.getIdentifyingMarkings().isBlank()) {
            throw new IllegalArgumentException("Identifying Markings is required");
        }
        if (cat.getOwnerId() < 0) {
            throw new IllegalArgumentException("OwnerId is required");
        }
        if (cat.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of birth is required");
        }

        String sql = "INSERT INTO cats(OwnerId,Name, Gender, Breed, DateOfBirth, Color, IdentifyingMarkings) VALUES (? , ? , ? , ? , ? , ? , ?)";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, cat.getOwnerId());
            ps.setString(2, cat.getName().trim());
            ps.setString(3, cat.getGender().name());
            ps.setString(4, cat.getBreed().trim());
            ps.setDate(5, cat.getDateOfBirth());
            ps.setString(6, cat.getColour().trim());
            ps.setString(7, cat.getIdentifyingMarkings().trim());

            int rows = ps.executeUpdate();
            if (rows != 1)
                throw new IllegalArgumentException("insert failed, rows = " + rows);

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next())
                    throw new IllegalArgumentException("no generated keys returned");
                return keys.getInt(1);
            }
        }
    }

    @Override
    public Optional<Cat> findById(int id) throws Exception {
        if (id <= 0)
            return Optional.empty();

        String sql = "SELECT * FROM cats WHERE id = ?";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();

                return Optional.of(mapRow(rs));
            }
        }
    }

    @Override
    public List<Cat> findAll() throws Exception {

        String sql = "SELECT * FROM cats ORDER BY Id";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<Cat> out = new ArrayList<>();
            while (rs.next())
                out.add(mapRow(rs));
            return out;
        }
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        if (id <= 0)
            return false;

        String sql = "DELETE FROM cats WHERE Id = ?";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }


    @Override
    public Cat update(int id, Cat cat) throws SQLException {
        String sql = "UPDATE cats SET OwnerId = ?, Name = ?, Gender = ?, Breed = ?, DateOfBirth = ?, Color = ?, IdentifyingMarkings = ? WHERE CatId = ?";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cat.getOwnerId());
            ps.setString(2, cat.getName().trim());
            ps.setString(3, cat.getGender().name());
            ps.setString(4, cat.getBreed().trim());
            ps.setDate(5, cat.getDateOfBirth());
            ps.setString(6, cat.getColour().trim());
            ps.setString(7, cat.getIdentifyingMarkings().trim());
            ps.setInt(8,id);

            ps.executeUpdate();
        }
        return cat;
    }

    @Override
    public List<Cat> filter(List<Cat> cats, Predicate<Cat> keep) {
        var result = new ArrayList<Cat>();
        for (Cat cat : cats)
            if (keep.test(cat))
                result.add(cat);
        return result;
    }

    @Override
    public String serialise(Cat cat) throws JsonProcessingException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(cat);
    }

    @Override
    public Cat deSerialise(String json) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, Cat.class);
    }

    @Override
    public String serialiseList(List<Cat> catList) throws JsonProcessingException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(catList);
    }

    @Override
    public List<Cat> deSerialiseList(String json) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, new TypeReference<List<Cat>>() {
        });
    }
}