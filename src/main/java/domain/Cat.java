package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class Cat {
    private int id;
    private int ownerId;
    private String name;
    private Gender gender;
    private String breed;
    private Date dateOfBirth;
    private String colour;
    private String identifyingMarkings;
    private String fileName;
    private String content_type;
    private int file_size;
    private byte[] cat_image;


    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getBreed() {
        return breed;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getColour() {
        return colour;
    }

    public String getIdentifyingMarkings() {
        return identifyingMarkings;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent_type() {
        return content_type;
    }

    public int getFile_size() {
        return file_size;
    }

    public byte[] getCat_image() {
        return cat_image;
    }

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