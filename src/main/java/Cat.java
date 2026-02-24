import java.sql.Date;

public class Cat {
    private int Id;
    private int OwnerId;
    private String Name;
    private String Gender;
    private String Breed;
    private Date DateOfBirth;
    private String Colour;
    private String IdentifyingMarkings;


    public int getId() {
        return Id;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public String getName() {
        return Name;
    }

    public String getGender() {
        return Gender;
    }

    public String getBreed() {
        return Breed;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public String getColour() {
        return Colour;
    }

    public String getIdentifyingMarkings() {
        return IdentifyingMarkings;
    }


    public Cat(int Id, int OwnerId, String Name, String Gender, String Breed, Date DateOfBirth, String Colour, String IdentifyingMarkings) {
        if (Id < 0) {
            throw new IllegalArgumentException("id cant be below 0");
        }
        if (OwnerId < 0) {
            throw new IllegalArgumentException("owner id cant be below 0");
        }
        if (Name == null || Name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (Gender == null || Gender.isBlank()) {
            throw new IllegalArgumentException("Gender is required");
        }
        if (Breed == null || Breed.isBlank()) {
            throw new IllegalArgumentException("Breed is required");
        }
        if (DateOfBirth == null) {
            throw new IllegalArgumentException("Date of Birth is required");
        }
        if (Colour == null || Colour.isBlank()) {
            throw new IllegalArgumentException("Colour is required");
        }
        if (IdentifyingMarkings == null || IdentifyingMarkings.isBlank()) {
            throw new IllegalArgumentException("Colour is required");
        }

        this.Id = Id;
        this.OwnerId = OwnerId;
        this.Name = Name;
        this.Gender = Gender;
        this.Breed = Breed;
        this.DateOfBirth = DateOfBirth;
        this.Colour = Colour;
        this.IdentifyingMarkings = IdentifyingMarkings;
    }

    @Override
    public String toString() {
        return "Cat{id=" + Id +
                ", OwnerId=" + OwnerId +
                ", Name=" + Name +
                ", Gender=" + Gender +
                ", Breed=" + Breed +
                ", DateOfBirth=" + DateOfBirth +
                ", Colour="+ Colour +
                ", IdentifyingMarkings=" + IdentifyingMarkings;
    }
}
