package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents nutrition in the system.
 */
public class Nutrition{
 private int catId;
private int dailyCaloriesKcal;
private double proteinGrams;
private double fatGrams;
private double carbGrams;
private int waterIntakeMl;
private int mealsPerDay;
private String foodBrand;
private String dietaryRestrictions;


    /**
     * Creates a new Nutrition with the given attributes.
     * @param id                        the nutrition's unique ID (must be >= 0)
     * @param dailyCaloriesKcal         daily amount of calories (required
     * @param proteinGrams              grams of protein (required)
     * @param fatGrams                  grams of fat (required)
     * @param carbGrams                 grams of carbs (required)
     * @param waterIntakeMl             ml of water (required)
     * @param mealsPerDay               amount of meals per day (required)
     * @param foodBrand                 brand of food (required)
     * @param dietaryRestrictions       any restrictions (required)
     * @throws IllegalArgumentException if any required field is null, blank, or invalid
     */
  @JsonCreator
public Nutrition(
    @JsonProperty("catId") int catId,
    @JsonProperty("dailyCaloriesKcal") int dailyCaloriesKcal,
    @JsonProperty("proteinGrams") double proteinGrams,
    @JsonProperty("fatGrams") double fatGrams,
    @JsonProperty("carbGrams") double carbGrams,
    @JsonProperty("waterIntakeMl") int waterIntakeMl,
    @JsonProperty("mealsPerDay") int mealsPerDay,
    @JsonProperty("foodBrand") String foodBrand,
    @JsonProperty("dietaryRestrictions") String dietaryRestrictions)
{
//  if (catId <= 0)
//    throw new IllegalArgumentException("catId must be greater than 0");
if (dailyCaloriesKcal <= 0)
    throw new IllegalArgumentException("Daily calories must be greater than 0");
if (proteinGrams <= 0)
    throw new IllegalArgumentException("Protein grams must be greater than 0");
if (fatGrams <= 0)
    throw new IllegalArgumentException("Fat grams must be greater than 0");
if (carbGrams <= 0)
    throw new IllegalArgumentException("Carb grams must be greater than 0");
if (waterIntakeMl <= 0)
    throw new IllegalArgumentException("Water intake must be greater than 0");
if (mealsPerDay <= 0)
    throw new IllegalArgumentException("Meals per day must be greater than 0");
if (foodBrand == null || foodBrand.isBlank())
    throw new IllegalArgumentException("Food brand is required");
if (dietaryRestrictions == null || dietaryRestrictions.isBlank())
    throw new IllegalArgumentException("Dietary restrictions are required");
this.catId = catId;
this.dailyCaloriesKcal = dailyCaloriesKcal;
this.proteinGrams = proteinGrams;
this.fatGrams = fatGrams;
this.carbGrams = carbGrams;
this.waterIntakeMl = waterIntakeMl;
this.mealsPerDay = mealsPerDay;
this.foodBrand = foodBrand;
this.dietaryRestrictions = dietaryRestrictions;

}

    /** Returns the nutrition's unique ID. */
    public int getCatId() { return catId; }

    /** Returns daily calories. */
    public int getDailyCaloriesKcal() { return dailyCaloriesKcal; }

    /** Returns grams of protein. */
    public double getProteinGrams() { return proteinGrams; }

    /** Returns grams of fat. */
    public double getFatGrams() { return fatGrams; }

    /** Returns grams of carbs. */
    public double getCarbGrams() { return carbGrams; }

    /** Returns ml of water. */
    public int getWaterIntakeMl() { return waterIntakeMl; }

    /** Returns amount of meals per day. */
    public int getMealsPerDay() { return mealsPerDay; }

    /** Returns food brand. */
    public String getFoodBrand() { return foodBrand; }

    /** Returns dietary restrictions. */
    public String getDietaryRestrictions() { return dietaryRestrictions; }

@Override
public String toString() {
    return "Nutrition{" +
        "catId=" + catId +
        ", dailyCaloriesKcal=" + dailyCaloriesKcal +
        ", proteinGrams=" + proteinGrams +
        ", fatGrams=" + fatGrams +
        ", carbGrams=" + carbGrams +
        ", waterIntakeMl=" + waterIntakeMl +
        ", mealsPerDay=" + mealsPerDay +
        ", foodBrand=" + foodBrand +
        ", dietaryRestrictions=" + dietaryRestrictions +
        "}";
}
}
