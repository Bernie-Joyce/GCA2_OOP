package server;

import client.NutritionMenu;
import domain.Cat;
import domain.Nutrition;

public class Nutrition_Request {
    private int id;
    private Nutrition nutrition;

    public int getId() {
        return id;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }
}
