package dao;

import dao.jdbc.JdbcCatDao;
import dao.jdbc.JdbcNutritionDao;
import dao.jdbc.JdbcOwnerDao;
import domain.Cat;
import domain.Gender;
import domain.Nutrition;
import domain.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for {@link JdbcNutritionDao}.
 * @author Jack Cleary
 */
public class JdbcNutritionDaoTest {

    private static final String URL  = "jdbc:mysql://localhost:8889/catnowner_test?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "root";

    private JdbcNutritionDao _dao;
    private int _catId;

    @BeforeEach
    void setUp() throws Exception {
        _dao = new JdbcNutritionDao(URL, USER, PASS);

        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            c.createStatement().executeUpdate("DELETE FROM nutrition");
            c.createStatement().executeUpdate("DELETE FROM cats");
            c.createStatement().executeUpdate("DELETE FROM owners");
        }

        JdbcOwnerDao ownerDao = new JdbcOwnerDao(URL, USER, PASS);
        Owner owner = ownerDao.insert(new Owner("Test", "Owner", 30, "1 Test St", "0871234567", "test@example.com"));

        JdbcCatDao catDao = new JdbcCatDao(URL, USER, PASS);
        _catId = catDao.insert(new Cat.Builder()
                .ownerId(owner.getId())
                .name("TestCat")
                .gender(Gender.MALE)
                .breed("Tabby")
                .dateOfBirth(Date.valueOf("2022-01-01"))
                .colour("Black")
                .identifyingMarkings("None")
                .fileName("")
                .contentType("")
                .fileSize(0)
                .build());

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(
                "INSERT INTO nutrition(CatId, DailyCaloriesKcal, ProteinGrams, FatGrams, CarbGrams, WaterIntakeMl, MealsPerDay, FoodBrand, DietaryRestrictions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, _catId);
            ps.setInt(2, 350);
            ps.setDouble(3, 45.5);
            ps.setDouble(4, 18.3);
            ps.setDouble(5, 12.0);
            ps.setInt(6, 250);
            ps.setInt(7, 2);
            ps.setString(8, "Royal Canin");
            ps.setString(9, "Grain-free");
            ps.executeUpdate();
        }
    }

    @Test
    void findNutritionByCatId_returnsEmptyOptional_whenCatIdDoesNotExist() throws Exception {
        Optional<Nutrition> result = _dao.findByCatId(99999);
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_returnsAllNutritionRecords_whenRecordsExist() throws Exception {
        List<Nutrition> result = _dao.findAll();
        assertFalse(result.isEmpty());
        assertEquals(350, result.get(0).getDailyCaloriesKcal());
    }
}
