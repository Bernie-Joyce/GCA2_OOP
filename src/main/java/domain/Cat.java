package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

/**
 * Represents a cat in the system.
 */
public class Cat {
    private int id;
    private int ownerId;
    private String name;
    private Gender gender;
    private String breed;
    private Date dateOfBirth;
    private String colour;
    private String identifyingMarkings;

    /** Returns the cat's unique ID. */
    public int getId() {
        return id;
    }

    /** Returns the ID of the cat's owner. */
    public int getOwnerId() {
        return ownerId;
    }

    /** Returns the cat's name. */
    public String getName() {
        return name;
    }

    /** Returns the cat's gender. */
    public Gender getGender() {
        return gender;
    }

    /** Returns the cat's breed. */
    public String getBreed() {
        return breed;
    }

    /** Returns the cat's date of birth. */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /** Returns the cat's colour. */
    public String getColour() {
        return colour;
    }

    /** Returns the cat's identifying markings. */
    public String getIdentifyingMarkings() {
        return identifyingMarkings;
    }

    /**
     * Creates a new Cat with the given attributes.
     * @param id                  the cat's unique ID (must be >= 0)
     * @param ownerId             the ID of the cat's owner (must be >= 0)
     * @param name                the cat's name (required)
     * @param gender              the cat's gender (required)
     * @param breed               the cat's breed (required)
     * @param dateOfBirth         the cat's date of birth (required)
     * @param colour              the cat's colour (required)
     * @param identifyingMarkings any identifying markings (required)
     * @throws IllegalArgumentException if any required field is null, blank, or invalid
     */
    @JsonCreator
    public Cat(@JsonProperty("id") int id,
               @JsonProperty("ownerId") int ownerId,
               @JsonProperty("name") String name,
               @JsonProperty("gender") Gender gender,
               @JsonProperty("breed") String breed,
               @JsonProperty("dateOfBirth") Date dateOfBirth,
               @JsonProperty("colour") String colour,
               @JsonProperty("identifyingMarkings") String identifyingMarkings)
    {
        if (id < 0) {
            throw new IllegalArgumentException("id cant be below 0");
        }
        if (ownerId < 0) {
            throw new IllegalArgumentException("owner id cant be below 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Gender is required");
        }
        if (breed == null || breed.isBlank()) {
            throw new IllegalArgumentException("Breed is required");
        }
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of Birth is required");
        }
        if (colour == null || colour.isBlank()) {
            throw new IllegalArgumentException("Colour is required");
        }
        if (identifyingMarkings == null || identifyingMarkings.isBlank()) {
            throw new IllegalArgumentException("Identifying markings is required");
        }

        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.gender = gender;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.colour = colour;
        this.identifyingMarkings = identifyingMarkings;
    }

    @Override
    public String toString() {
        return "Cat{id=" + id +
                ", ownerId=" + ownerId +
                ", name=" + name +
                ", gender=" + gender +
                ", breed=" + breed +
                ", dateOfBirth=" + dateOfBirth +
                ", colour=" + colour +
                ", identifyingMarkings=" + identifyingMarkings;
    }
}