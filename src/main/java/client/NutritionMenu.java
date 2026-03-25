package client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import domain.Nutrition;
import domain.Owner;
import protocol.RequestType;
import protocol.Response;


public class NutritionMenu {

    private final Scanner scanner = new Scanner(System.in);
    private Client client;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    NutritionMenu(Client client) {
        this.client = client;
    }

    public void run() {
        boolean check = true;
        while (check) {
            System.out.println("\n=== Nutrition Menu ===");
            System.out.println("1. Get all owners");
            System.out.println("2. Get owner by ID");
            System.out.println("3. Add owner");
            System.out.println("4. Update owner");
            System.out.println("5. Delete owner");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> handleGetAll();
                case "2" -> handleGetById();
                    case "3" -> handleAdd();
                    case "4" -> handleUpdate();
                    case "5" -> handleDelete();
                case "0" -> check = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    public void handleGetAll() {
        try {
            Response<JsonNode> res = client.send(RequestType.GET_ALL_NUTRITION, null);
            System.out.println(res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleGetById() {
        try {
            Response<JsonNode> res = client.send(RequestType.GET_NUTRITION_BY_CAT_ID, null);
            System.out.println(res.getData().toPrettyString());
        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }

    public void handleAdd(){
        try {

            Nutrition newNutrition = getNutritionDetails(0);

            Response<JsonNode> res = client.send(RequestType.CREATE_NUTRITION, newNutrition);
            System.out.println(res.getData().toPrettyString());
        }catch (Exception e){
            System.out.println("Error: " +e.getMessage());
        }
    }

    public void handleUpdate(){
        try {
            System.out.println("Enter ID to Update: ");
            int ID = scanner.nextInt();
            scanner.nextLine();
            Nutrition newNutrition = getNutritionDetails(ID);

            ObjectNode payload = MAPPER.createObjectNode();
            payload.put("id", ID);
            payload.set("nutrition", MAPPER.valueToTree(newNutrition));

            Response<JsonNode> res = client.send(RequestType.UPDATE_NUTRITION, payload);
            System.out.println(res.getData().toPrettyString());
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());

        }
    }
    public void handleDelete(){
        try{
            System.out.println("Enter ID to delete: ");
            int ID = scanner.nextInt();
            scanner.nextLine();
            Response<JsonNode> res = client.send(RequestType.DELETE_NUTRITION, ID );
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private Nutrition getNutritionDetails(int id){
        scanner.nextLine();
        System.out.println("Daily Calories: ");
        int kcals = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Protein grams: ");
        double protein = scanner.nextDouble();
        scanner.nextLine();
        System.out.println();
        System.out.println("Fat grams: ");
        double fat = scanner.nextDouble();
        scanner.nextLine();
        System.out.println();
        System.out.println("Carbohydrate grams: ");
        double carb = scanner.nextDouble();
        scanner.nextLine();
        System.out.println();
        System.out.println("Water in ml: ");
        int water = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Meals per Day: ");
        int mealCount = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Food Brand: ");
        String brand = scanner.nextLine();
        System.out.println("Dietary Restrictions: ");
        String dietRestrictions = scanner.nextLine();

        return new Nutrition(id, kcals, protein, fat, carb, water, mealCount, brand, dietRestrictions);
    }
}


