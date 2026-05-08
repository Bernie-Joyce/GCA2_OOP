package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an owner in the system.
 * @author Michal Salabura
 */

public class Owner {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String address;
    private final String phone;
    private final String email;
    private final String fileName;
    private final String contentType;
    private final int fileSize;
    private final byte[] ownerImage;

    /**
     * Creates an Owner with a default ID of 0.
     * Convenience constructor for new owners before they are persisted.
     * @author Michal Salabura
     * @param firstName the owner's first name
     * @param lastName  the owner's last name
     * @param age       the owner's age
     * @param address   the owner's address
     * @param phone     the owner's phone number
     * @param email     the owner's email address
     */
    public Owner(String firstName, String lastName, int age, String address, String phone, String email) {
        this(0, firstName, lastName, age, address, phone, email, null, null, 0, null);
    }

    /** Returns the owner's unique ID. */
    public int getId() {
        return id;
    }

    /** Returns the owner's first name. */
    public String getFirstName() {
        return firstName;
    }

    /** Returns the owner's last name. */
    public String getLastName() {
        return lastName;
    }

    /** Returns the owner's age. */
    public int getAge() {
        return age;
    }

    /** Returns the owner's address. */
    public String getAddress() {
        return address;
    }

    /** Returns the owner's phone number. */
    public String getPhone() {
        return phone;
    }

    /** Returns the owner's email address. */
    public String getEmail() {
        return email;
    }

    /** Returns the image file name. */
    public String getFileName() {
        return fileName;
    }

    /** Returns the image type. */
    public String getContentType() {
        return contentType;
    }

    /** Returns the image size. */
    public int getFileSize() {
        return fileSize;
    }

    /** Returns the image byte array. */
    public byte[] getOwnerImage() {
        return ownerImage;
    }

    /**
     * Creates a new Owner with the given attributes.
     * @author Michal Salabura
     * @param id            the owner's unique ID (must be >= 0)
     * @param firstName     the owner's first name (required)
     * @param lastName      the owner's last name (required)
     * @param age           the owner's age (required)
     * @param address       the owner's address (required)
     * @param phone         the owner's phone number (required)
     * @param email         the owner's email address (required)
     * @param fileName      image file name
     * @param contentType   image type
     * @param fileSize      image size
     * @param ownerImage    image byte array
     * @throws IllegalArgumentException if any required field is null, blank, or invalid
     */
    @JsonCreator
    public Owner(@JsonProperty("id") int id,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("age") int age,
                @JsonProperty("address") String address,
                @JsonProperty("phone") String phone,
                @JsonProperty("email") String email,
                @JsonProperty("fileName") String fileName,
                @JsonProperty("contentType") String contentType,
                @JsonProperty("fileSize") int fileSize,
                @JsonProperty("ownerImage") byte[] ownerImage)
    {
        if (id < 0) {
            throw new IllegalArgumentException("ID cant be below 0");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name can't be empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name can't be empty");
        }
        if (age == 0) {
            throw new IllegalArgumentException("Age is required");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address is required");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.ownerImage = ownerImage;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
