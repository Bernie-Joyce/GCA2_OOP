package client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import domain.Cat;
import domain.Gender;
import protocol.RequestType;
import protocol.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Scanner;

/**
 * Console menu for managing cats.
 * Handles user input and delegates requests to the server via {@link Client}.
 */
public class CatMenu {
    private final Scanner scanner = new Scanner(System.in);
    private Client client;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Creates a CatMenu with the given client.
     * @param client the connected client used to send requests
     */
    CatMenu(Client client){this.client = client;}

    /** Displays the cat menu and handles user input in a loop until exit. */
    public void run(){
        boolean check = true;
        while(check) {
            String message = """
                    === Cat Menu ===\s
                    1. Get all cats
                    2. Get cat by ID
                    3. Add cat
                    4. Update cat
                    5. Delete cat
                    0. Exit
                    Choice:\s
                   \s""";
            IO.println(message);
            switch(scanner.nextLine().trim()){
                case "1" -> handleGetAll();
                case "2" -> handleGetById();
                case "3" -> handleAdd();
                case "4" -> handleUpdate();
                case "5" -> handleDelete();
                case "0" -> check = false;
                default -> IO.println("Invalid option");
            }


        }
    }
    private void handleGetAll() {
        try {
            Response<JsonNode> res = client.send(RequestType.GET_ALL_CATS, null);
            System.out.println(res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void handleGetById() {
        try {
            System.out.println("Cat ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Response<JsonNode> res = client.send(RequestType.GET_CAT_BY_ID, id);
            System.out.println(res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void handleAdd() {
        try {
            Cat cat= getCatDetails(0);
            Response<JsonNode> res = client.send(RequestType.CREATE_CAT, cat);
            System.out.println(res.getStatus() + res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void handleUpdate(){
        try{
            IO.println("ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Cat upCat = getCatDetails(id);

            ObjectNode payload = MAPPER.createObjectNode();
            payload.put("id", id);
            payload.set("cat", MAPPER.valueToTree(upCat));

            Response<JsonNode> res = client.send(RequestType.UPDATE_CAT, payload);
            System.out.println(res.getStatus() + res.getData().toPrettyString());
        } catch (Exception e) {
            IO.println("Error:" + e.getMessage());
        }
    }
    private void handleDelete() {
        try {
            System.out.println("Id to delete:");
            int id = scanner.nextInt();
            scanner.nextLine();
            Response<JsonNode> res = client.send(RequestType.DELETE_CAT, id);
            System.out.println(res.getStatus());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private Cat getCatDetails(int id) {
        scanner.nextLine();
        IO.println("Owner Id:");
        int OwnerId = scanner.nextInt();
        IO.print("Name: ");
        String name = scanner.nextLine().trim();
        Gender gender = findGender();
        IO.print("Breed: ");
        String breed = scanner.nextLine().trim();
        IO.println("Date of birth: ");
        Date date = findDate();
        IO.print("Colour: ");
        String colour = scanner.nextLine().trim();
        IO.print("Identifying Markings: ");
        String Identifying_Markings = scanner.nextLine().trim();
        scanner.nextLine();

        
        return new Cat(id, OwnerId,name, gender, breed, date, colour, Identifying_Markings);
    }
    private Gender findGender(){
        IO.println("Gender: (1 for male, 2 for female)");
        switch(scanner.nextInt()){
            case 1 -> {
                return Gender.MALE;
            }
            case 2 -> {
                return Gender.FEMALE;
            }
            default -> findGender();
        }
        return null;
    }
    private Date findDate(){
        Date date = null;
        boolean validDate = false;

        while (!validDate) {
            try {
                IO.println("Date of birth (dd/MM/yyyy): ");
                String dateInput = scanner.nextLine().trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = (Date) dateFormat.parse(dateInput);
                validDate = true;
            }
            catch(ParseException e){
                findDate();
            }
        }
        return date;
    }
}
