import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface OwnerDao {
    Owner insert(Owner owner) throws Exception;
    Optional<Owner> findOwnerById(int id) throws Exception;
    List<Owner> findAllOwners() throws Exception;
    boolean deleteById(int id) throws Exception;
    List<Owner> findOwnersByFilter(Predicate<Owner> filter) throws Exception;
}