package client;

import java.util.Scanner;

public class ClientMain {
    private final Scanner scanner = new Scanner(System.in);

    void main() throws Exception {
        try (Client client = new Client("localhost", 9000)) {
            boolean loopCheck = true;
            OwnerMenu oMenu = new OwnerMenu(client);
            while(loopCheck) {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1. Cats");
                System.out.println("2. Owners");
                System.out.println("3. Nutrients");
                System.out.println("0. Exit");
                System.out.print("Choice: ");

                switch(scanner.nextLine().trim()) {
                    case "1" -> System.out.println("Cats");
                    case "2" -> oMenu.run();
                    case "3" -> System.out.println("Nutrients");
                    case "0" -> loopCheck = false;
                    default -> System.out.println("Invalid option");
                }
            }

        }
    }
}