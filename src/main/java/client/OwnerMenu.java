package client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import domain.Cat;
import domain.FileUploadPayload;
import domain.Owner;
import protocol.RequestType;
import protocol.Response;

import java.util.Scanner;
import java.io.IOException;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Console menu for managing owners.
 * Handles user input and delegates requests to the server via {@link Client}.
 * @author Michal Salabura
 */
public class OwnerMenu {
    private final Scanner scanner = new Scanner(System.in);
    private Client client;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Creates a OwnerMenu with the given client.
     * @author Michal Salabura
     * @param client the connected client used to send requests
     */
    OwnerMenu(Client client) {
        this.client = client;
    }

    /** Displays the owner menu and handles user input in a loop until exit. */
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
            System.out.println("7. Download image");
            System.out.println("8. Get image metadata");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            switch(scanner.nextLine().trim()) {
                case "1" -> handleGetAll();
                case "2" -> handleGetById();
                case "3" -> handleAdd();
                case "4" -> handleUpdate();
                case "5" -> handleDelete();
                case "6" -> handleImageUpload();
                case "7" -> handleDownloadImage();
                case "8" -> handleGetMetadata();
                case "0" -> check = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /** Retrieves and prints all owners from the server. */
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

    /**
     * Retrieves an owner by ID.
     */
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

    /** Prompts for owner details and sends a create request to the server. */
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

    /** Prompts for an ID and updated owner details, then sends an update request. */
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

    /** Prompts for an ID and sends a delete request to the server. */
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

    /**
     * Prompts the user for all owner fields, excluding image file,
     * and builds an {@link Owner}.
     * @author Michal Salabura
     * @param id the owner's ID (0 for new owner)
     * @return a constructed {@link Owner}
     */
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
        return new Owner(id, firstName, lastName, age, address, phone, email, null, null, 0, null);
    }

    /**
     * Prompts the user for an owner ID and file path, then uploads the specified
     * image file to the server associated with that owner.
     * Reads the file at the provided path, encodes it, and sends it via
     * {@link RequestType#UPLOAD_OWNER_IMAGE}. Prints the server's response
     * message on success, or an error message if the upload fails.
     */
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

    /**
     * Reads the file at the given path and constructs a {@link FileUploadPayload}
     * containing the file's Base64-encoded content and metadata.
     * @author Michal Salabura
     * @param filePath the {@link Path} to the image file to be uploaded
     * @param id       the ID of the owner to associate the image with
     * @return a {@link FileUploadPayload} containing the encoded file data and its metadata
     * @throws IOException if the file cannot be read or its content type cannot be probed
     */
    private FileUploadPayload buildUploadPayload(Path filePath, int id) throws IOException {
        byte[] bytes = Files.readAllBytes(filePath);
        String b64 = Base64.getEncoder().encodeToString(bytes);
        String name = filePath.getFileName().toString();
        String detectedMime = Files.probeContentType(filePath);
        String mime = (detectedMime != null) ? detectedMime : "application/octet-stream";
        return new FileUploadPayload(id, name, mime, bytes.length, b64);
    }

    /**
     * Prompts the user for an owner ID and a local destination folder, then
     * downloads the owner's image from the server and saves it to disk.
     * Sends a {@link RequestType#GET_OWNER_IMAGE} request and decodes the
     * Base64 image data from the response. The file is saved using the
     * original filename returned by the server. Prints the saved file's
     * path on success, or an error message on failure.
     * @author Michal Salabura
     */
    public void handleDownloadImage() {
        try {
            System.out.print("Owner ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Save to folder (e.g. C:/downloads/): ");
            String savePath = scanner.nextLine().trim();

            Response<JsonNode> res = client.send(RequestType.GET_OWNER_IMAGE, id);

            if (res.getStatus().matches("ERROR")) {
                throw new Exception(res.getMessage());
            }

            JsonNode data = res.getData();
            String fileName = data.get("fileName").asText();
            String base64 = data.get("imageData").asText();
            byte[] imageBytes = Base64.getDecoder().decode(base64);

            Path outPath = Path.of(savePath, fileName);
            Files.write(outPath, imageBytes);

            System.out.println("File saved at: " + outPath);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for an owner ID and prints the image metadata
     * associated with that owner, as returned by the server.
     * Sends a {@link RequestType#GET_OWNER_METADATA} request and prints
     * the response payload as a formatted JSON string. Prints an error
     * message if the request fails.
     * @author Michal Salabura
     */
    public void handleGetMetadata() {
        try {
            System.out.print("Owner ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Response<JsonNode> res = client.send(RequestType.GET_OWNER_METADATA, id);

            if(res.getStatus().matches("ERROR")) {
                throw new Exception(res.getMessage());
            }

            System.out.println(res.getData().toPrettyString());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
