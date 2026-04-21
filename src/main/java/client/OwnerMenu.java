package client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import domain.FileUploadPayload;
import domain.Owner;
import protocol.RequestType;
import protocol.Response;

import java.util.Scanner;
import java.io.IOException;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;

public class OwnerMenu {
    private final Scanner scanner = new Scanner(System.in);
    private Client client;
    private static final ObjectMapper MAPPER = new ObjectMapper();

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
            System.out.println("6. Upload image");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            switch(scanner.nextLine().trim()) {
                case "1" -> handleGetAll();
                case "2" -> handleGetById();
                case "3" -> handleAdd();
                case "4" -> handleUpdate();
                case "5" -> handleDelete();
                case "6" -> handleImageUpload();
                case "0" -> check = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void handleGetAll() {
        try {
            Response<JsonNode> res = client.send(RequestType.GET_ALL_OWNERS, null);

            if(res.getStatus().matches("ERROR")) {
                throw new Exception(res.getMessage());
            } else {
                System.out.println(res.getData().toPrettyString());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleGetById() {
        try {
            System.out.println("Owner ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Response<JsonNode> res = client.send(RequestType.GET_OWNER_BY_ID, id);

            if(res.getStatus().matches("ERROR")) {
                throw new Exception(res.getMessage());
            } else {
                System.out.println(res.getData().toPrettyString());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAdd() {
        try {
            Owner newOwner = getOwnerDetails(0);
            Response<JsonNode> res = client.send(RequestType.CREATE_OWNER, newOwner);

            if(res.getStatus().matches("ERROR")) {
                throw new Exception(res.getMessage());
            } else {
                System.out.println(res.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdate() {
        try {
            System.out.println("Id to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Owner upOwner = getOwnerDetails(id);

            ObjectNode payload = MAPPER.createObjectNode();
            payload.put("id", id);
            payload.set("owner", MAPPER.valueToTree(upOwner));

            Response<JsonNode> res = client.send(RequestType.UPDATE_OWNER, payload);

            if(res.getStatus().matches("ERROR")) {
                throw new Exception(res.getMessage());
            } else {
                System.out.println(res.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private void handleDelete() {
        try {
            System.out.println("Id to delete:");
            int id = scanner.nextInt();
            scanner.nextLine();

            Response<JsonNode> res = client.send(RequestType.DELETE_OWNER, id);

            if(res.getStatus().matches("ERROR")) {
                throw new Exception(res.getMessage());
            } else {
                System.out.println(res.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


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

    private void handleImageUpload() {
        try {
            System.out.print("Owner ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("File path (e.g. C:/images/photo.jpg): ");
            String filePath = scanner.nextLine().trim();

            FileUploadPayload payload = buildUploadPayload(Path.of(filePath), id);
            Response<JsonNode> res = client.send(RequestType.UPLOAD_OWNER_IMAGE, payload);

            if (res.getStatus().matches("ERROR")) {
                throw new Exception(res.getMessage());
            } else {
                System.out.println(res.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private FileUploadPayload buildUploadPayload(Path filePath, int id) throws IOException {
        byte[] bytes = Files.readAllBytes(filePath);
        String b64 = Base64.getEncoder().encodeToString(bytes);
        String name = filePath.getFileName().toString();
        String detectedMime = Files.probeContentType(filePath);
        String mime = (detectedMime != null) ? detectedMime : "application/octet-stream";
        return new FileUploadPayload(id, name, mime, bytes.length, b64);
    }
}
