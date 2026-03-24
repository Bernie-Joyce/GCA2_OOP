package client;

import com.fasterxml.jackson.databind.JsonNode;
import domain.Owner;
import protocol.RequestType;
import protocol.Response;

import java.util.Scanner;

public class OwnerMenu {
    private final Scanner scanner = new Scanner(System.in);
    private Client client;

    OwnerMenu(Client client) {
        this.client = client;
    }

    public void run() {
        boolean check = true;
        while(check) {
            System.out.println("\n=== Owner Menu ===");
            System.out.println("1. Get all owners");
            System.out.println("2. Get owner by ID");
            System.out.println("3. Add owner");
            System.out.println("4. Update owner");
            System.out.println("5. Delete owner");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            switch(scanner.nextLine().trim()) {
                case "1" -> handleGetAll();
                case "2" -> handleGetById();
                case "3" -> handleAdd();
                case "4" -> handleUpdate();
                case "5" -> System.out.println("Delete");
                case "0" -> check = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void handleGetAll() {
        try {
            Response<JsonNode> res = client.send(RequestType.GET_ALL_OWNERS, null);
            System.out.println(res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleGetById() {
        try {
            System.out.println("Owner ID: ");
            int id = scanner.nextInt();
            Response<JsonNode> res = client.send(RequestType.GET_OWNER_BY_ID, id);
            System.out.println(res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAdd() {
        try {
            Owner newOwner = getOwnerDetails();
            Response<JsonNode> res = client.send(RequestType.CREATE_OWNER, newOwner);
            System.out.println(res.getStatus() + res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdate() {
        try {
            System.out.println("Id to update: ");
            int id = scanner.nextInt();
            Owner upOwner = getOwnerDetails(id);
            Response<JsonNode> res = client.send(RequestType.UPDATE_OWNER, upOwner);
            System.out.println(res.getStatus() + res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

//    getOwnerDetails
    private Owner getOwnerDetails() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        return new Owner(0, firstName, lastName, age, address, phone, email);
    }
//    overloaded getOwnerDetails for update
    private Owner getOwnerDetails(int id) {
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        return new Owner(id, firstName, lastName, age, address, phone, email);
    }

}
