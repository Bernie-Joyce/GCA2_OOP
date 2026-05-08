package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import domain.Cat;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Data Access Object for {@link Cat} entities.
 * Defines CRUD operations and JSON serialisation for cats.
 * @author Bernard Joyce
 */
public interface CatDao {
    /**
     * Inserts a new cat into the data store.
     * @author Bernard Joyce
     * @param cat the cat to insert
     * @return the generated ID of the inserted cat
     * @throws Exception if the insert fails
     */
    int insert(Cat cat) throws Exception;

    /**
     * Updates an existing cat by ID.
     * @author Bernard Joyce
     * @param id the ID of the cat to update
     * @param cat the cat object containing updated values
     * @return the updated {@link Cat}
     * @throws SQLException if the update fails
     */
    Cat update(int id, Cat cat) throws SQLException;

    /**
     * Finds a cat by its ID.
     * @author Bernard Joyce
     * @param id the cat's ID
     * @return an {@link Optional} containing the cat if found, or empty if not
     * @throws Exception if the query fails
     */
    Optional<Cat> findById(int id) throws Exception;

    /**
     * Retrieves all cats from the data store.
     * @author Bernard Joyce
     * @return a list of all {@link Cat} objects
     * @throws Exception if the query fails
     */
    List<Cat> findAll() throws Exception;

    /**
     * Deletes a cat by its ID.
     * @author Bernard Joyce
     * @param id the ID of the cat to delete
     * @return true if deleted successfully, false if not found
     * @throws Exception if the deletion fails
     */
    boolean deleteById(int id) throws Exception;

    /**
     * Filters a list of cats using a given predicate.
     * @author Bernard Joyce
     * @param cats the list to filter
     * @param keep the condition a cat must satisfy to be kept
     * @return a filtered list of {@link Cat} objects
     */
    List<Cat> filter(List<Cat> cats, Predicate<Cat> keep);

    /**
     * Serialises a cat to a JSON string.
     * @author Bernard Joyce
     * @param cat the cat to serialise
     * @return JSON representation of the cat
     * @throws JsonProcessingException if serialisation fails
     */
    String serialise(Cat cat) throws JsonProcessingException;

    /**
     * Deserialises a JSON string into a cat object.
     * @author Bernard Joyce
     * @param json the JSON string to parse
     * @return the deserialised {@link Cat}
     * @throws JsonProcessingException if deserialisation fails
     */
    Cat deSerialise(String json) throws JsonProcessingException;

    /**
     * Serialises a list of cats to a JSON string.
     * @author Bernard Joyce
     * @param catList the list to serialise
     * @return JSON representation of the list
     * @throws JsonProcessingException if serialisation fails
     */
    String serialiseList(List<Cat> catList) throws JsonProcessingException;

    /**
     * Deserialises a JSON string into a list of cats.
     * @author Bernard Joyce
     * @param jsonList the JSON string to parse
     * @return a list of deserialised {@link Cat} objects
     * @throws JsonProcessingException if deserialisation fails
     */
    List<Cat> deSerialiseList(String jsonList) throws JsonProcessingException;
}
