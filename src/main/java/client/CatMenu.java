package client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import domain.Cat;
import domain.Gender;
import protocol.RequestType;
import protocol.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Scanner;

public class CatMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final Client client;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    CatMenu(Client client) {
        this.client = client;
    }

    public void run() {
        boolean check = true;
        while (check) {
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
            switch (scanner.nextLine().trim()) {
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
            System.out.print("Cat ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Response<JsonNode> res = client.send(RequestType.GET_CAT_BY_ID, id);

            if (res.getStatus().equals("OK") && res.getData() != null) {
                Cat cat = MAPPER.treeToValue(res.getData(), Cat.class);
                IO.println("\n--- Cat Details Found ---");
                IO.println("Name: " + cat.getName());
                IO.println("Size: " + cat.getFileSize() + " bytes");

                if (cat.getCatImage() != null && cat.getCatImage().length > 0) {
                    String name = (cat.getFileName() == null || cat.getFileName().isBlank())
                            ? "downloaded_image.jpg" : "retrieved_" + cat.getFileName();

                    Path path = Paths.get(name);
                    Files.write(path, cat.getCatImage());
                    IO.println(">>> Success: Image reconstructed at " + path.toAbsolutePath());
                } else {
                    IO.println(">>> Note: Cat object exists, but catImage field is NULL.");
                }
            } else {
                IO.println(">>> Error: Server returned status " + res.getStatus());
            }
        } catch (IOException ex) {
            IO.println("Error: Could not write file. " + ex.getMessage());
        }
    }

    private void handleAdd() {
        try {
            Cat cat = getCatDetails(0);
            Response<JsonNode> res = client.send(RequestType.CREATE_CAT, cat);
            System.out.println(res.getStatus() + res.getData().toPrettyString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdate() {
        try {
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
        IO.println("Owner Id:");
        int ownerId = scanner.nextInt();
        scanner.nextLine();

        IO.print("Name: ");
        String name = scanner.nextLine().trim();

        Gender gender = findGender();

        IO.print("Breed: ");
        String breed = scanner.nextLine().trim();

        Date dateOfBirth = findDate();

        IO.print("Colour: ");
        String colour = scanner.nextLine().trim();

        IO.print("Identifying Markings: ");
        String markings = scanner.nextLine().trim();

        IO.print("Enter absolute path to cat image (e.g., C:/pics/cat.jpg): ");
        String pathInput = scanner.nextLine().trim();

        String fileName = "";
        int fileSize = 0;
        String contentType = "";
        byte[] imageBytes = null;
        if (!pathInput.isBlank()) {
            try {
                Path path = Paths.get(pathInput);
                if (Files.exists(path)) {
                    fileName = path.getFileName().toString();
                    fileSize = (int) Files.size(path);
                    contentType = Files.probeContentType(path);
                    imageBytes = Files.readAllBytes(path);
                    IO.println("Successfully loaded: " + fileName + " (" + fileSize + " bytes)");
                } else {
                    IO.println("Warning: File not found. Proceeding without image.");
                }
            } catch (Exception e) {
                IO.println("Error reading file");
            }
        }

        return new Cat.Builder()
                .id(id)
                .ownerId(ownerId)
                .name(name)
                .gender(gender)
                .breed(breed)
                .dateOfBirth(dateOfBirth)
                .colour(colour)
                .identifyingMarkings(markings)
                .fileName(fileName)
                .contentType(contentType)
                .catImage(imageBytes)
                .fileSize(fileSize)
                .build();
    }

    private Gender findGender() {
        while (true) {
            IO.println("Gender: (1 for male, 2 for female)");
            String input = scanner.nextLine().trim();
            if (input.equals("1")) return Gender.MALE;
            if (input.equals("2")) return Gender.FEMALE;
            IO.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    private Date findDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        while (true) {
            try {
                IO.println("Date of birth (dd/MM/yyyy): ");
                String dateInput = scanner.nextLine().trim();
                java.util.Date utilDate = dateFormat.parse(dateInput);
                return new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                IO.println("Invalid format. Please use dd/MM/yyyy.");
            }
        }
    }
}
