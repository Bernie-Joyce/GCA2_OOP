package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dao.CatDao;
import domain.Cat;
import domain.Gender;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Service layer responsible for managing {@link Cat} entities.
 *
 * <p>This class provides business operations such as creation, retrieval,
 * update, deletion, filtering, and JSON serialization of cat data.
 * It acts as an intermediary between higher-level components and the
 * {@link CatDao}.</p>
 */
public class CatService implements Service {
    private final CatDao dao;

    /**
     * Constructs a {@code CatService} with the given DAO.
     *
     * @param dao the {@link CatDao} used for data access
     * @throws IllegalArgumentException if {@code dao} is {@code null}
     */
    public CatService(CatDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");
        this.dao = dao;
    }

    /**
     * Creates a new {@link Cat}.
     *
     * @param cat the cat to create
     * @return the generated identifier of the created cat
     * @throws Exception if the operation fails
     */
    public int createCat(Cat cat) throws Exception {
        return dao.insert(cat);
    }

    /**
     * Retrieves a {@link Cat} by its unique identifier.
     *
     * @param id the cat ID
     * @return an {@link Optional} containing the cat if found
     * @throws Exception if the retrieval fails
     */
    public Optional<Cat> getCat(int id) throws Exception {
        return dao.findById(id);
    }

    /**
     * Retrieves all cats.
     *
     * @return a list of all cats
     * @throws Exception if the retrieval fails
     */
    public List<Cat> listCats() throws Exception {
        return dao.findAll();
    }

    /**
     * Updates an existing cat.
     *
     * @param id the ID of the cat to update
     * @param cat the updated cat data
     * @return the updated {@link Cat}
     * @throws SQLException if the update fails
     */
    public Cat updateCat(int id , Cat cat) throws SQLException {
        return dao.update(id,cat);
    }

    /**
     * Deletes a cat by its unique identifier.
     *
     * @param id the cat ID
     * @throws Exception if the deletion fails
     */
    public void deleteCat(int id) throws Exception {
        if (dao.deleteById(id)) {
            IO.println("Cat deleted successfully");
        } else {
            IO.println("Cat was not deleted");
        }
    }

    /**
     * Filters cats by gender.
     *
     * @param gender the {@link Gender} to filter by
     * @return a list of cats matching the given gender
     * @throws Exception if the operation fails
     */
    public List<Cat> filterGender(Gender gender) throws Exception {
        return dao.filter(dao.findAll(), cat -> cat.getGender().equals(gender)
        );
    }

    /**
     * Converts a {@link Cat} object to its JSON representation.
     *
     * @param cat the cat to serialize
     * @return a JSON string representation
     * @throws JsonProcessingException if serialization fails
     */
    public String catToJSON(Cat cat) throws JsonProcessingException {
        return dao.serialise(cat);
    }

    /**
     * Converts a JSON string into a {@link Cat} object.
     *
     * @param json the JSON string
     * @return the deserialized cat
     * @throws JsonProcessingException if deserialization fails
     */
    public Cat catFromJSON(String json) throws JsonProcessingException {
        return dao.deSerialise(json);
    }

    /**
     * Converts a list of {@link Cat} objects to JSON.
     *
     * @param catList the list of cats
     * @return a JSON string representation
     * @throws JsonProcessingException if serialization fails
     */
    public String catListToJSON(List<Cat> catList) throws JsonProcessingException {
        return dao.serialiseList(catList);
    }

    /**
     * Converts a JSON string into a list of {@link Cat} objects.
     *
     * @param json the JSON string
     * @return a list of deserialized cats
     * @throws JsonProcessingException if deserialization fails
     */
    public List<Cat> JSONToCatList(String json) throws JsonProcessingException {
        return dao.deSerialiseList(json);
    }
}