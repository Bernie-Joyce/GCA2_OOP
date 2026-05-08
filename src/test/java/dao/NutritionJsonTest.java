package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Nutrition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for Nutrition Json.
 * @author Jack Cleary
 */
public class NutritionJsonTest {

    private final ObjectMapper _mapper = new ObjectMapper();

    @Test
    void toJson_validNutrition_roundTripEqualsOriginal() throws Exception {
        Nutrition original = new Nutrition(1, 350, 45.5, 18.3, 12.0, 250, 2, "Royal Canin", "Grain-free");

        String json = _mapper.writeValueAsString(original);
        Nutrition deserialised = _mapper.readValue(json, Nutrition.class);

        assertEquals(original.getCatId(),               deserialised.getCatId());
        assertEquals(original.getDailyCaloriesKcal(),   deserialised.getDailyCaloriesKcal());
        assertEquals(original.getProteinGrams(),        deserialised.getProteinGrams());
        assertEquals(original.getFatGrams(),            deserialised.getFatGrams());
        assertEquals(original.getCarbGrams(),           deserialised.getCarbGrams());
        assertEquals(original.getWaterIntakeMl(),       deserialised.getWaterIntakeMl());
        assertEquals(original.getMealsPerDay(),         deserialised.getMealsPerDay());
        assertEquals(original.getFoodBrand(),           deserialised.getFoodBrand());
        assertEquals(original.getDietaryRestrictions(), deserialised.getDietaryRestrictions());
    }
}