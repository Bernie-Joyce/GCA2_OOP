package server;

import client.NutritionMenu;
import domain.Cat;
import domain.Nutrition;

/**
 * Data Transfer Object (DTO) representing a nutrition-related request.
 *
 * <p>This class encapsulates the data required to perform operations
 * related to {@link Nutrition}, such as retrieving, updating, or deleting
 * nutrition records associated with a specific cat.</p>
 *
 * <p>It is typically used in communication between client and server layers.</p>
 * @author Jack Cleary
 */
public class Nutrition_Request {
    private int id;
    private Nutrition nutrition;

    /**
     * Returns the identifier associated with the request.
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the {@link Nutrition} payload of the request.
     * @return the nutrition data, or {@code null} if not provided
     */
    public Nutrition getNutrition() {
        return nutrition;
    }
}
