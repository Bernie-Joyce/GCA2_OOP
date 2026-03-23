package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dao.CatDao;
import domain.Cat;
import domain.Gender;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CatService implements Service {
    private final CatDao dao;
    public CatService(CatDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");
        this.dao = dao;
    }
    public int createCat(Cat cat) throws Exception {
        return dao.insert(cat);
    }
    public Optional<Cat> getCat(int id) throws Exception {
        return dao.findById(id);
    }
    public List<Cat> listCats() throws Exception {
        return dao.findAll();
    }
    public Cat updateCat(int id , Cat cat) throws SQLException {
        return dao.update(id,cat);
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