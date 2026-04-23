package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import domain.Cat;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface CatDao {
    int insert(Cat cat) throws Exception;
    Cat update(int id, Cat cat) throws SQLException;
    Optional<Cat> findById(int id) throws Exception;
    List<Cat> findAll() throws Exception;
    boolean deleteById(int id) throws Exception;
    List<Cat> filter(List<Cat> cats, Predicate<Cat> keep);
    String serialise(Cat cat) throws JsonProcessingException;
    Cat deSerialise(String json) throws JsonProcessingException;
    String serialiseList(List<Cat> catList) throws JsonProcessingException;
    List<Cat> deSerialiseList(String jsonList) throws JsonProcessingException;
    Optional<Cat> findCatWithoutBinaryData(int id) throws Exception;
}
