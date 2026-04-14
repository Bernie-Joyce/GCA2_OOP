package client;

import java.util.Scanner;

/**
 * Entry point for the client application.
 * Displays the main menu and delegates to sub-menus for cats, owners, and nutrition.
 */
public class ClientMain {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Starts the client application and connects to the server on localhost:9000.
     * @throws Exception if the server connection fails or a request errors
     */
    void main() throws Exception {
        try (Client client = new Client("localhost", 9000)) {
            boolean loopCheck = true;
            OwnerMenu oMenu = new OwnerMenu(client);
            NutritionMenu nMenu = new NutritionMenu(client);
            CatMenu catMenu = new CatMenu(client);
            while(loopCheck) {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1. Cats");
                System.out.println("2. Owners");
                System.out.println("3. Nutrients");
                System.out.println("0. Exit");
                System.out.print("Choice: ");

                switch(scanner.nextLine().trim()) {
                    case "1" -> catMenu.run();
                    case "2" -> oMenu.run();
                    case "3" -> nMenu.run();
                    case "0" -> loopCheck = false;
                    default -> System.out.println("Invalid option");
                }
            }

        }
    }
}