package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public Owner(String firstName, String lastName, int age, String address, String phone, String email) {
        this(0, firstName, lastName, age, address, phone, email, null, null, 0, null);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

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
