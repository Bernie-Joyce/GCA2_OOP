package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import domain.Nutrition;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Data Access Object for {@link Nutrition} entities.
 * Defines CRUD operations and JSON serialisation for cats.
 */
public interface NutritionDao {

    /**
     * Inserts a new cat into the data store.
     * @param nutrition the cat to insert
     * @return Nutrition object of the inserted nutrition
     * @throws Exception if the insert fails
     */
    Nutrition insert(Nutrition nutrition) throws Exception;

    /**
     * Finds a nutrition by its ID.
     * @param id the nutrition's ID
     * @return an {@link Optional} containing the nutrition if found, or empty if not
     * @throws Exception if the query fails
     */
    Optional<Nutrition> findByCatId(int catId) throws Exception;

    /**
     * Retrieves all nutrition from the data store.
     * @return a list of all {@link Nutrition} objects
     * @throws Exception if the query fails
     */
    List<Nutrition> findAll() throws Exception;

    /**
     * Deletes a nutrition by its ID.
     * @param id the ID of the nutrition to delete
     * @return true if deleted successfully, false if not found
     * @throws Exception if the deletion fails
     */
    boolean deleteByCatId(int catId) throws Exception;

    /**
     * Updates an existing nutrition by ID.
     * @param id the ID of the cat to update
     * @param nutrition the nutrition object containing updated values
     * @return the updated {@link Nutrition}
     * @throws Exception if the update fails
     */
    Nutrition update(int catId, Nutrition nutrition) throws Exception;

    /**
     * Filters a list of nutrition using a given predicate.
     * @param nutritionList the list to filter
     * @param keep the condition a nutrition must satisfy to be kept
     * @return a filtered list of {@link Nutrition} objects
     */
    List<Nutrition> filter(List<Nutrition> nutritionList, Predicate<Nutrition> keep);

    /**
     * Serialises a nutrition to a JSON string.
     * @param nutrition the nutrition to serialise
     * @return JSON representation of the nutrition
     * @throws JsonProcessingException if serialisation fails
     */
    String serialise(Nutrition nutrition) throws JsonProcessingException;

    /**
     * Deserialises a JSON string into a nutrition object.
     * @param json the JSON string to parse
     * @return the deserialised {@link Nutrition}
     * @throws JsonProcessingException if deserialisation fails
     */
    Nutrition deSerialise(String json) throws JsonProcessingException;

    /**
     * Serialises a list of nutrition to a JSON string.
     * @param nutritionList the list to serialise
     * @return JSON representation of the list
     * @throws JsonProcessingException if serialisation fails
     */
    String serialiseList(List<Nutrition> nutritionList) throws JsonProcessingException;

    /**
     * Deserialises a JSON string into a list of cats.
     * @param json the JSON string to parse
     * @return a list of deserialised {@link Nutrition} objects
     * @throws JsonProcessingException if deserialisation fails
     */
    List<Nutrition> deSerialiseList(String json) throws JsonProcessingException;
}
