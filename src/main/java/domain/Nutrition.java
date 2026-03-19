package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
  if (catId <= 0)
    throw new IllegalArgumentException("catId must be greater than 0");
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

  public int getCatId() { return catId; }
public int getDailyCaloriesKcal() { return dailyCaloriesKcal; }
public double getProteinGrams() { return proteinGrams; }
public double getFatGrams() { return fatGrams; }
public double getCarbGrams() { return carbGrams; }
public int getWaterIntakeMl() { return waterIntakeMl; }
public int getMealsPerDay() { return mealsPerDay; }
public String getFoodBrand() { return foodBrand; }
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
