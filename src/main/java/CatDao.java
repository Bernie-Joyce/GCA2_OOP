import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface CatDao {
    int insert(int OwnerId, String Name, Gender Gender, String Breed, Date DateOfBirth, String Colour, String IdentifyingMarkings) throws Exception;
    Optional<Cat> findById(int id) throws Exception;
    List<Cat> findAll() throws Exception;
    boolean deleteById(int id) throws Exception;
    List<Cat> filter(List<Cat> cats, Predicate<Cat> keep);
    String serialise(Cat cat) throws JsonProcessingException;
    Cat deSerialise(String json) throws JsonProcessingException;
    String serialiseList(List<Cat> catList) throws JsonProcessingException;
}
