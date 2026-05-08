package dao.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.NutritionDao;
import domain.Nutrition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * JDBC implementation of {@link NutritionDao}.
 * Connects to a relational database to perform cat CRUD operations.
 * @author Jack Cleary
 */
public class JdbcNutritionDao implements NutritionDao {
    private final String _url;
    private final String _user;
    private final String _pass;
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * Creates a new jdbcNutritionDao with the given database credentials.
     * @author Jack Cleary
     * @param url  the JDBC connection URL
     * @param user the database username
     * @param pass the database password
     * @throws IllegalArgumentException if the URL is null or blank
     */
    public JdbcNutritionDao(String url, String user, String pass) {
        if (url == null || url.isBlank())
            throw new IllegalArgumentException("url is required");
        _url = url.trim();
        _user = user;
        _pass = pass;
    }

    private Connection open() throws SQLException {
        return DriverManager.getConnection(_url, _user, _pass);
    }

    private static Nutrition mapRow(ResultSet rs) throws SQLException {
        return new Nutrition(
            rs.getInt("CatId"),
            rs.getInt("DailyCaloriesKcal"),
            rs.getDouble("ProteinGrams"),
            rs.getDouble("FatGrams"),
            rs.getDouble("CarbGrams"),
            rs.getInt("WaterIntakeMl"),
            rs.getInt("MealsPerDay"),
            rs.getString("FoodBrand"),
            rs.getString("DietaryRestrictions")
        );
    }
    @Override
    public Nutrition insert(Nutrition nutrition) throws Exception {
        if (nutrition == null)
            throw new IllegalArgumentException("Nutrition cannot be null");

        String sql = "INSERT INTO nutrition(DailyCaloriesKcal, ProteinGrams, FatGrams, CarbGrams, WaterIntakeMl, MealsPerDay, FoodBrand, DietaryRestrictions) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, nutrition.getDailyCaloriesKcal());
            ps.setDouble(2, nutrition.getProteinGrams());
            ps.setDouble(3, nutrition.getFatGrams());
            ps.setDouble(4, nutrition.getCarbGrams());
            ps.setInt(5, nutrition.getWaterIntakeMl());
            ps.setInt(6, nutrition.getMealsPerDay());
            ps.setString(7, nutrition.getFoodBrand());
            ps.setString(8, nutrition.getDietaryRestrictions());

            int rows = ps.executeUpdate();
            if (rows != 1)
                throw new IllegalArgumentException("insert failed, rows = " + rows);

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return new Nutrition(generatedId,
                            nutrition.getDailyCaloriesKcal(),
                            nutrition.getProteinGrams(),
                            nutrition.getFatGrams(),
                            nutrition.getCarbGrams(),
                            nutrition.getWaterIntakeMl(),
                            nutrition.getMealsPerDay(),
                            nutrition.getFoodBrand(),
                            nutrition.getDietaryRestrictions());
                } else {
                    throw new SQLException("Insert succeeded but no generated key was returned");
                }
            }
        }
    }

    @Override
    public Optional<Nutrition> findByCatId(int catId) throws Exception {
        if (catId <= 0)
            return Optional.empty();

        String sql = "SELECT * FROM nutrition WHERE CatId = ?";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, catId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();
                return Optional.of(mapRow(rs));
            }
        }
    }

    @Override
    public List<Nutrition> findAll() throws Exception {
        String sql = "SELECT * FROM nutrition ORDER BY CatId";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<Nutrition> out = new ArrayList<>();
            while (rs.next())
                out.add(mapRow(rs));
            return out;
        }
    }

    @Override
    public boolean deleteByCatId(int catId) throws Exception {
        if (catId <= 0)
            return false;

        String sql = "DELETE FROM nutrition WHERE CatId = ?";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, catId);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public Nutrition update(int catId, Nutrition nutrition) throws Exception {
        if (nutrition == null)
            throw new IllegalArgumentException("Nutrition cannot be null");

        String sql = "UPDATE nutrition SET DailyCaloriesKcal = ?, ProteinGrams = ?, FatGrams = ?, CarbGrams = ?, WaterIntakeMl = ?, MealsPerDay = ?, FoodBrand = ?, DietaryRestrictions = ? WHERE CatId = ?";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, nutrition.getDailyCaloriesKcal());
            ps.setDouble(2, nutrition.getProteinGrams());
            ps.setDouble(3, nutrition.getFatGrams());
            ps.setDouble(4, nutrition.getCarbGrams());
            ps.setInt(5, nutrition.getWaterIntakeMl());
            ps.setInt(6, nutrition.getMealsPerDay());
            ps.setString(7, nutrition.getFoodBrand());
            ps.setString(8, nutrition.getDietaryRestrictions());
            ps.setInt(9, catId);

            int rows = ps.executeUpdate();
            if (rows != 1)
                throw new IllegalArgumentException("Update failed for catId: " + catId);

            return new Nutrition(catId,
                nutrition.getDailyCaloriesKcal(),
                nutrition.getProteinGrams(),
                nutrition.getFatGrams(),
                nutrition.getCarbGrams(),
                nutrition.getWaterIntakeMl(),
                nutrition.getMealsPerDay(),
                nutrition.getFoodBrand(),
                nutrition.getDietaryRestrictions());
        }
    }

    @Override
    public List<Nutrition> filter(List<Nutrition> nutritionList, Predicate<Nutrition> keep) {
        ArrayList<Nutrition> result = new ArrayList<>();
        for (Nutrition n : nutritionList)
            if (keep.test(n))
                result.add(n);
        return result;
    }

    @Override
    public String serialise(Nutrition nutrition) throws JsonProcessingException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(nutrition);
    }

    @Override
    public Nutrition deSerialise(String json) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, Nutrition.class);
    }

    @Override
    public String serialiseList(List<Nutrition> nutritionList) throws JsonProcessingException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(nutritionList);
    }

    @Override
    public List<Nutrition> deSerialiseList(String json) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, new TypeReference<List<Nutrition>>() {});
    }
}
