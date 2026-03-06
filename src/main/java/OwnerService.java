import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

public class OwnerService implements Service{
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private final OwnerDao dao;

    public OwnerService(OwnerDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");
        this.dao = dao;
    }

    public Owner createOwner(Owner owner) throws Exception {
        return dao.insert(owner);
    }

    public Optional<Owner> getOwner(int id) throws Exception {
        return dao.findOwnerById(id);
    }

    public List<Owner> listOwners() throws Exception {
        return dao.findAllOwners();
    }

    public void deleteOwner(int id) throws Exception {
        if (dao.deleteById(id)) {
            IO.println("Owner deleted successfully");
        } else {
            IO.println("Owner was not deleted");
        }
    }
    public String ownerToJson(Owner owner) throws JsonProcessingException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(owner);
    }

    public Owner ownerFromJson(String json) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, Owner.class);
    }

    public String ownerListToJson(List<Owner> ownerList) throws JsonProcessingException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(ownerList);
    }

    public List<Owner> ownerListFromJson(String json) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, new TypeReference<List<Owner>>() {
        });
    }
    
}
