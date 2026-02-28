import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.*;

public class CatService {

    private final CatDao _dao;

    public CatService(CatDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");
        _dao = dao;
    }

    public int createCat(int OwnerId, String Name, String Gender, String Breed, Date DateOfBirth, String Color, String IdentifyingMarkings) throws Exception {
        return _dao.insert(OwnerId, Name, Gender, Breed, DateOfBirth, Color, IdentifyingMarkings);
    }

    public Optional<Cat> get(int id) throws Exception {
        return _dao.findById(id);
    }

    public List<Cat> list() throws Exception {
        return _dao.findAll();
    }

    public void deleteCat(int id) throws Exception {
        if (_dao.deleteById(id)) {
            IO.println("Cat deleted successfully");
        } else {
            IO.println("Cat was not deleted");
        }
    }

    public List<Cat> filterGender(Gender gender) throws Exception {
        Predicate<Cat> keep = cat -> cat.getGender().equals(gender);
        return _dao.filter(_dao.findAll(), keep);
    }
}

