import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.*;

public class CatService {
    private final CatDao dao;
    public CatService(CatDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");
        this.dao = dao;
    }
    public int createCat(int ownerId, String name, Gender gender, String breed, Date dateOfBirth, String color, String identifyingMarkings) throws Exception {
        return dao.insert(ownerId, name, gender, breed, dateOfBirth, color, identifyingMarkings);
    }
    public Optional<Cat> getCat(int id) throws Exception {
        return dao.findById(id);
    }
    public List<Cat> listCats() throws Exception {
        return dao.findAll();
    }
    public void deleteCat(int id) throws Exception {
        if (dao.deleteById(id)) {
            IO.println("Cat deleted successfully");
        } else {
            IO.println("Cat was not deleted");
        }
    }
    public List<Cat> filterGender(Gender gender) throws Exception {
        return dao.filter(dao.findAll(), cat -> cat.getGender().equals(gender));
    }
    public String catToJSON(Cat cat) throws JsonProcessingException {
        return dao.serialise(cat);
    }
    public Cat catFromJSON(String json) throws JsonProcessingException {
        return dao.deSerialise(json);
    }
    public String catListToJSON(List<Cat> catList) throws JsonProcessingException {
        return dao.serialiseList(catList);
    }
    public List<Cat> JSONToCatList(String json) throws JsonProcessingException {
        return dao.deSerialiseList(json);
    }
}