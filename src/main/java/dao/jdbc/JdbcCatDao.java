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

/**
 * JDBC implementation of {@link CatDao}.
 * Connects to a relational database to perform cat CRUD operations.
 * @author Bernard Joyce
 */
public class JdbcCatDao implements CatDao {
    private final String _url;
    private final String _user;
    private final String _pass;
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * Creates a new JdbcCatDao with the given database credentials.
     * @param url  the JDBC connection URL
     * @param user the database username
     * @param pass the database password
     * @throws IllegalArgumentException if the URL is null or blank
     */
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
        return new Cat.Builder()
                .id(rs.getInt("Id"))
                .ownerId(rs.getInt("OwnerID"))
                .name(rs.getString("Name"))
                .gender(Gender.valueOf(rs.getString("Gender").toUpperCase()))
                .breed(rs.getString("Breed"))
                .dateOfBirth(rs.getDate("DateOfBirth"))
                .colour(rs.getString("Color"))
                .identifyingMarkings(rs.getString("IdentifyingMarkings"))
                .fileName(rs.getString("file_name"))
                .contentType(rs.getString("content_type"))
                .fileSize(rs.getInt("file_size"))
                .catImage(rs.getBytes("cat_image"))
                .build();
    }
    private static Cat mapCatWithoutImage(ResultSet rs) throws SQLException {
        return new Cat.Builder()
                .id(rs.getInt("id"))
                .ownerId(rs.getInt("OwnerID"))
                .name(rs.getString("Name"))
                .gender(Gender.valueOf(rs.getString("Gender").toUpperCase()))
                .breed(rs.getString("Breed"))
                .dateOfBirth(rs.getDate("DateOfBirth"))
                .colour(rs.getString("Color"))
                .identifyingMarkings(rs.getString("IdentifyingMarkings"))
                .build();
    }

    @Override
    public int insert(Cat cat) throws Exception {

        String sql = "INSERT INTO cats(OwnerId,Name, Gender, Breed, DateOfBirth, Color, IdentifyingMarkings,file_name, content_type, file_size, cat_image) VALUES (? , ? , ? , ? , ? , ? , ?, ? , ? , ? , ?)";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            settingStatement(cat, ps);

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

        String sql = "SELECT Id,OwnerID,Name, Gender, Breed, DateOfBirth, Color, IdentifyingMarkings FROM cats order by Id";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<Cat> out = new ArrayList<>();
            while (rs.next())
                out.add(mapCatWithoutImage(rs));
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
        String sql = "UPDATE cats SET OwnerId = ?, Name = ?, Gender = ?, Breed = ?, DateOfBirth = ?, Color = ?, IdentifyingMarkings = ?, file_name = ?, content_type = ?, file_size = ?, cat_image = ? WHERE Id = ?";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            settingStatement(cat, ps);
            ps.setInt(12, id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Update failed; no cat found with ID: " + id);
            }
        }
        return cat;
    }

    private void settingStatement(Cat cat, PreparedStatement ps) throws SQLException {
        ps.setInt(1, cat.getOwnerId());
        ps.setString(2, cat.getName().trim());
        ps.setString(3, cat.getGender().name());
        ps.setString(4, cat.getBreed().trim());
        ps.setDate(5, cat.getDateOfBirth());
        ps.setString(6, cat.getColour().trim());
        ps.setString(7, cat.getIdentifyingMarkings().trim());
        ps.setString(8, cat.getFileName());
        ps.setString(9, cat.getContentType());
        ps.setInt(10, cat.getFileSize());
        ps.setBytes(11, cat.getCatImage());
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
        return JSON_MAPPER.readValue(json, new TypeReference<>() {
        });
    }
}