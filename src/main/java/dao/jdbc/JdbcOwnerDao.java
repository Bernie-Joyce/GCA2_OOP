package dao.jdbc;

import dao.OwnerDao;
import domain.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class JdbcOwnerDao implements OwnerDao {
    private final String _url;
    private final String _user;
    private final String _pass;

    public JdbcOwnerDao(String url, String user, String pass) {
        if (url == null || url.isBlank())
            throw new IllegalArgumentException("url is required");
        _url = url.trim();
        _user = user;
        _pass = pass;
    }

    private Connection open() throws SQLException {
        return DriverManager.getConnection(_url, _user, _pass);
    }

    private static Owner mapRow(ResultSet rs) throws SQLException {
        return new Owner(rs.getInt("id"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getInt("Age"),
                rs.getString("Address"),
                rs.getString("Phone"),
                rs.getString("Email"),
                null, null, 0, null);
    }

    @Override
    public Owner insert(Owner owner) throws Exception {
        String sql = "INSERT INTO owners(FirstName, LastName, Age, Address, Phone, Email) VALUES (? , ? , ? , ? , ? , ?)";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, owner.getFirstName());
            ps.setString(2, owner.getLastName());
            ps.setInt(3, owner.getAge());
            ps.setString(4, owner.getAddress());
            ps.setString(5, owner.getPhone());
            ps.setString(6, owner.getEmail());

            int rows = ps.executeUpdate();
            if (rows != 1)
                throw new IllegalArgumentException("insert failed, rows = " + rows);

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next())
                    throw new IllegalArgumentException("no generated keys returned");

                return new Owner(keys.getInt(1),
                        owner.getFirstName(),
                        owner.getLastName(),
                        owner.getAge(),
                        owner.getAddress(),
                        owner.getPhone(),
                        owner.getEmail(),
                        null, null, 0, null);
            }
        }
    }

    @Override
    public Optional<Owner> findOwnerById(int id) throws Exception {
        if (id <= 0)
            return Optional.empty();

        String sql = "SELECT * FROM owners WHERE id = ?";

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
    public List<Owner> findAllOwners() throws Exception {

        String sql = "SELECT * FROM owners ORDER BY Id";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<Owner> out = new ArrayList<>();
            while (rs.next())
                out.add(mapRow(rs));
            return out;
        }
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        if (id <= 0)
            return false;

        String sql = "DELETE FROM owners WHERE Id = ?";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public Owner updateOwner(int id, Owner owner) throws Exception{
        String sql = "UPDATE owners SET FirstName = ?, LastName = ?, Age = ?, Address = ?, Phone = ?, Email = ? WHERE ID = ?;";
        try (Connection c = open();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, owner.getFirstName());
            ps.setString(2, owner.getLastName());
            ps.setInt(3, owner.getAge());
            ps.setString(4, owner.getAddress());
            ps.setString(5, owner.getPhone());
            ps.setString(6, owner.getEmail());
            ps.setInt(7, id);

            int rows = ps.executeUpdate();
            if (rows != 1)
                throw new IllegalArgumentException("Update failed for id: " + id);

            return new Owner(id, owner.getFirstName(), owner.getLastName(), owner.getAge(), owner.getAddress(),
                            owner.getPhone(), owner.getEmail(), null, null, 0, null);
        }
    }

    @Override
    public List<Owner> findOwnersByFilter(Predicate<Owner> filter) throws Exception {
        List<Owner> all = findAllOwners();
        List<Owner> result = new ArrayList<>();
        for (Owner owner : all)
            if (filter.test(owner))
                result.add(owner);
        return result;
    }

    @Override
    public Owner uploadImage(int id, byte[] image, String fileName, String contentType, int fileSize) throws Exception {
        String sql = "UPDATE owners SET OwnerImage = ?, FileName = ?, ContentType = ?, FileSize = ? WHERE ID = ?";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBytes(1, image);
            ps.setString(2, fileName);
            ps.setString(3, contentType);
            ps.setInt(4, fileSize);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new IllegalArgumentException("Upload failed for owner id: " + id);
            }

            return findOwnerById(id).orElseThrow(() -> new Exception("Owner not found after upload"));
        }
    }

    @Override
    public Owner getOwnerImage(int id) throws Exception {
        String sql = "SELECT OwnerImage, FileName, ContentType, FileSize FROM owners WHERE ID = ?";

        try (Connection c = open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(!rs.next()) {
                    throw new IllegalArgumentException("Owner not found with ID: " + id);
                }
                return mapRowImage(rs);
            }
        }
    }

    private static Owner mapRowImage(ResultSet rs) throws SQLException {
        return new Owner(
                rs.getInt("ID"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getInt("Age"),
                rs.getString("Address"),
                rs.getString("Phone"),
                rs.getString("Email"),
                rs.getString("FileName"),
                rs.getString("ContentType"),
                rs.getInt("FileSize"),
                rs.getBytes("OwnerImage")
        );
    }
}
