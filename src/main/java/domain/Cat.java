package domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.sql.Date;

@JsonDeserialize(builder = Cat.Builder.class)
public class Cat {
    private final int id;
    private final int ownerId;
    private final String name;
    private final Gender gender;
    private final String breed;
    private final Date dateOfBirth;
    private final String colour;
    private final String identifyingMarkings;
    private final String fileName;
    private final String contentType;
    private final int fileSize;
    private final byte[] catImage;

    private Cat(Builder builder) {
        this.id = builder.id;
        this.ownerId = builder.ownerId;
        this.name = builder.name;
        this.gender = builder.gender;
        this.breed = builder.breed;
        this.dateOfBirth = builder.dateOfBirth;
        this.colour = builder.colour;
        this.identifyingMarkings = builder.identifyingMarkings;
        this.fileName = builder.fileName;
        this.contentType = builder.contentType;
        this.fileSize = builder.fileSize;
        this.catImage = builder.catImage;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private int id;
        private int ownerId;
        private String name;
        private Gender gender;
        private String breed;
        private Date dateOfBirth;
        private String colour;
        private String identifyingMarkings;
        private String fileName;
        private String contentType;
        private int fileSize;
        private byte[] catImage;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder ownerId(int ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder breed(String breed) {
            this.breed = breed;
            return this;
        }

        public Builder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder colour(String colour) {
            this.colour = colour;
            return this;
        }

        public Builder identifyingMarkings(String identifyingMarkings) {
            this.identifyingMarkings = identifyingMarkings;
            return this;
        }

        @JsonProperty("fileName")
        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        @JsonProperty("contentType")
        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        @JsonProperty("fileSize")
        public Builder fileSize(int fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        @JsonProperty("catImage")
        public Builder catImage(byte[] catImage) {
            this.catImage = catImage;
            return this;
        }

        public Cat build() {
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
            return new Cat(this);
        }
    }


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

    public String getContentType() {
        return contentType;
    }

    public int getFileSize() {
        return fileSize;
    }

    public byte[] getCatImage() {
        return catImage;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", breed='" + breed + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", colour='" + colour + '\'' +
                ", identifyingMarkings='" + identifyingMarkings + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}