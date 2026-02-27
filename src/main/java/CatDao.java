import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface CatDao {
    int insert(int OwnerId, String Name, String Gender, String Breed, Date DateOfBirth, String Colour, String IdentifyingMarkings) throws Exception;
    Optional<Cat> findById(int id) throws Exception;
    List<Cat> findAll() throws Exception;
    boolean deleteById(int id) throws Exception;
}
