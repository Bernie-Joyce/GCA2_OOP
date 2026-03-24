package client;

import com.fasterxml.jackson.databind.JsonNode;
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
                case "1" -> System.out.println("Get all");
                case "2" -> System.out.println("Get by ID");
                case "3" -> System.out.println("Add");
                case "4" -> System.out.println("Update");
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

}
