import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCatDao implements CatDao {
    private String _url;
    private String _user;
    private String _pass;

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

    @Override
    public int insert(int OwnerId, String Name, String Gender, String Breed, Date DateOfBirth, String Color, String IdentifyingMarkings) throws Exception {
        if (Name == null || Name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (Gender == null || Gender.isBlank()) {
            throw new IllegalArgumentException("Gender is required");
        }
        if (Color == null || Color.isBlank()) {
            throw new IllegalArgumentException("Color is required");
        }
        if (IdentifyingMarkings == null || IdentifyingMarkings.isBlank()) {
            throw new IllegalArgumentException("Identifying Markings is required");
        }
        if (OwnerId < 0) {
            throw new IllegalArgumentException("OwnerId is required");
        }

        String sql = "INSERT INTO cats(OwnerId,Name, Gender, Breed, DateOfBirth, Color, IdentifyingMarkings) VALUES (? , ? , ? , ? , ? , ? , ?)";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, OwnerId);
            ps.setString(2, Name.trim());
            ps.setString(3, Gender.trim());
            ps.setString(4, Breed.trim());
            ps.setDate(5, DateOfBirth);
            ps.setString(6, Color.trim());
            ps.setString(7, IdentifyingMarkings);

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

    private static Cat mapRow(ResultSet rs) throws SQLException {
        int Id = rs.getInt("id");
        int OwnerId = rs.getInt("OwnerId");
        String Name = rs.getString("Name");
        String Gender = rs.getString("Gender");
        String Breed = rs.getString("Breed");
        Date DateOfBirth = rs.getDate("DateOfBirth");
        String Color = rs.getString("Color");
        String IdentifyingMarkings = rs.getString("IdentifyingMarkings");

        return new Cat(Id, OwnerId, Name, Gender, Breed, DateOfBirth, Color, IdentifyingMarkings);
    }
}


