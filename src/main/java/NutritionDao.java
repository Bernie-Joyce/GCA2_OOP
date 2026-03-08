import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface NutritionDao {
    Nutrition insert(Nutrition nutrition) throws Exception;
    Optional<Nutrition> findByCatId(int catId) throws Exception;
    List<Nutrition> findAll() throws Exception;
    boolean deleteByCatId(int catId) throws Exception;
    Nutrition update(int catId, Nutrition nutrition) throws Exception;
    List<Nutrition> filter(List<Nutrition> nutritionList, Predicate<Nutrition> keep);
    String serialise(Nutrition nutrition) throws JsonProcessingException;
    Nutrition deSerialise(String json) throws JsonProcessingException;
    String serialiseList(List<Nutrition> nutritionList) throws JsonProcessingException;
    List<Nutrition> deSerialiseList(String json) throws JsonProcessingException;
}
