package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dao.NutritionDao;
import domain.Nutrition;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class NutritionService implements Service {
    private final NutritionDao dao;

    public NutritionService(NutritionDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");
        this.dao = dao;
    }

    public Nutrition createNutrition(Nutrition nutrition) throws Exception {
        return dao.insert(nutrition);
    }

    public Optional<Nutrition> getNutrition(int catId) throws Exception {
        return dao.findByCatId(catId);
    }

    public List<Nutrition> listNutrition() throws Exception {
        return dao.findAll();
    }

    public void deleteNutrition(int catId) throws Exception {
        if (dao.deleteByCatId(catId)) {
            IO.println("Nutrition plan deleted successfully");
        } else {
            IO.println("Nutrition plan was not deleted");
        }
    }

    public Nutrition updateNutrition(int catId, Nutrition nutrition) throws Exception {
        return dao.update(catId, nutrition);
    }

    public List<Nutrition> filterNutrition(Predicate<Nutrition> filter) throws Exception {
        return dao.filter(dao.findAll(), filter);
    }

    public String nutritionToJson(Nutrition nutrition) throws JsonProcessingException {
        return dao.serialise(nutrition);
    }

    public Nutrition nutritionFromJson(String json) throws JsonProcessingException {
        return dao.deSerialise(json);
    }

    public String nutritionListToJson(List<Nutrition> nutritionList) throws JsonProcessingException {
        return dao.serialiseList(nutritionList);
    }

    public List<Nutrition> jsonToNutritionList(String json) throws JsonProcessingException {
        return dao.deSerialiseList(json);
    }
}
