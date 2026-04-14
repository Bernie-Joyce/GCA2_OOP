package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an owner in the system.
 */
public class Owner {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String address;
    private final String phone;
    private final String email;

    /**
     * Creates an Owner with a default ID of 0.
     * Convenience constructor for new owners before they are persisted.
     * @param firstName the owner's first name
     * @param lastName  the owner's last name
     * @param age       the owner's age
     * @param address   the owner's address
     * @param phone     the owner's phone number
     * @param email     the owner's email address
     */
    public Owner(String firstName, String lastName, int age, String address, String phone, String email) {
        this(0, firstName, lastName, age, address, phone, email);
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

    /**
     * Creates a new Owner with the given attributes.
     * @param id        the owner's unique ID (must be >= 0)
     * @param firstName the owner's first name (required)
     * @param lastName  the owner's last name (required)
     * @param age       the owner's age (required)
     * @param address   the owner's address (required)
     * @param phone     the owner's phone number (required)
     * @param email     the owner's email address (required)
     * @throws IllegalArgumentException if any required field is null, blank, or invalid
     */
    @JsonCreator
    public Owner(@JsonProperty("id") int id,
               @JsonProperty("firstName") String firstName,
               @JsonProperty("lastName") String lastName,
               @JsonProperty("age") int age,
               @JsonProperty("address") String address,
               @JsonProperty("phone") String phone,
               @JsonProperty("email") String email)
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
