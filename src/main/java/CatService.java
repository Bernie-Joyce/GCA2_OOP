import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class CatService {

    private CatDao _dao;

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
}

