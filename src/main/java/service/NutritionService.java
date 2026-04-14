package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dao.NutritionDao;
import domain.Nutrition;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Service layer responsible for managing {@link Nutrition} entities.
 *
 * <p>This class provides business operations such as creation, retrieval,
 * update, deletion, filtering, and JSON serialization of nutrition data.
 * It acts as an intermediary between higher-level components and the
 * {@link NutritionDao}.</p>
 */
public class NutritionService implements Service {
    private final NutritionDao dao;

    /**
     * Constructs a {@code NutritionService} with the given DAO.
     *
     * @param dao the {@link NutritionDao} used for data access
     * @throws IllegalArgumentException if {@code dao} is {@code null}
     */
    public NutritionService(NutritionDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");
        this.dao = dao;
    }

    /**
     * Creates a new {@link Nutrition} record.
     *
     * @param nutrition the nutrition data to create
     * @return the created nutrition record
     * @throws Exception if the operation fails
     */
    public Nutrition createNutrition(Nutrition nutrition) throws Exception {
        return dao.insert(nutrition);
    }

    /**
     * Retrieves nutrition data for a specific cat.
     *
     * @param catId the unique identifier of the cat
     * @return an {@link Optional} containing the nutrition data if found
     * @throws Exception if the retrieval fails
     */
    public Optional<Nutrition> getNutrition(int catId) throws Exception {
        return dao.findByCatId(catId);
    }

    /**
     * Retrieves all nutrition records.
     *
     * @return a list of all nutrition records
     * @throws Exception if the retrieval fails
     */
    public List<Nutrition> listNutrition() throws Exception {
        return dao.findAll();
    }

    /**
     * Deletes nutrition data associated with a specific cat.
     *
     * @param catId the unique identifier of the cat
     * @throws Exception if the deletion fails
     */
    public void deleteNutrition(int catId) throws Exception {
        if (dao.deleteByCatId(catId)) {
            IO.println("Nutrition plan deleted successfully");
        } else {
            IO.println("Nutrition plan was not deleted");
        }
    }

    /**
     * Updates nutrition data for a specific cat.
     *
     * @param catId the unique identifier of the cat
     * @param nutrition the updated nutrition data
     * @return the updated nutrition record
     * @throws Exception if the update fails
     */
    public Nutrition updateNutrition(int catId, Nutrition nutrition) throws Exception {
        return dao.update(catId, nutrition);
    }

    /**
     * Filters nutrition records based on a minimum meals-per-day quota.
     *
     * @param quota the minimum number of meals per day
     * @return a list of nutrition records exceeding the given quota
     * @throws Exception if the operation fails
     */
    public List<Nutrition> filterNutrition(int quota) throws Exception {
        return dao.filter(dao.findAll(), nutrition -> nutrition.getMealsPerDay() > quota);
    }

    /**
     * Converts a {@link Nutrition} object to its JSON representation.
     *
     * @param nutrition the nutrition object to serialize
     * @return a JSON string representation
     * @throws JsonProcessingException if serialization fails
     */
    public String nutritionToJson(Nutrition nutrition) throws JsonProcessingException {
        return dao.serialise(nutrition);
    }

    /**
     * Converts a JSON string into a {@link Nutrition} object.
     *
     * @param json the JSON string
     * @return the deserialized nutrition object
     * @throws JsonProcessingException if deserialization fails
     */
    public Nutrition nutritionFromJson(String json) throws JsonProcessingException {
        return dao.deSerialise(json);
    }

    /**
     * Converts a list of {@link Nutrition} objects to JSON.
     *
     * @param nutritionList the list of nutrition records
     * @return a JSON string representation
     * @throws JsonProcessingException if serialization fails
     */
    public String nutritionListToJson(List<Nutrition> nutritionList) throws JsonProcessingException {
        return dao.serialiseList(nutritionList);
    }

    /**
     * Converts a JSON string into a list of {@link Nutrition} objects.
     *
     * @param json the JSON string
     * @return a list of deserialized nutrition records
     * @throws JsonProcessingException if deserialization fails
     */
    public List<Nutrition> jsonToNutritionList(String json) throws JsonProcessingException {
        return dao.deSerialiseList(json);
    }
}
