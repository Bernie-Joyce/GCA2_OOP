package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OwnerDao;
import domain.Owner;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Service layer responsible for handling business logic related to {@link Owner} entities.
 *
 * <p>This class acts as an intermediary between the DAO layer ({@link OwnerDao})
 * and higher-level components, providing operations such as creation, retrieval,
 * update, deletion, filtering, and JSON serialization.</p>
 * @author Michal Salabura
 */
public class OwnerService implements Service {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private final OwnerDao dao;

    /**
     * Constructs an {@code OwnerService} with the given data access object.
     * @param dao the {@link OwnerDao} used for persistence operations
     * @throws IllegalArgumentException if {@code dao} is {@code null}
     */
    public OwnerService(OwnerDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");
        this.dao = dao;
    }

    /**
     * Creates a new {@link Owner}.
     * @param owner the owner to create
     * @return the created owner
     * @throws Exception if the creation fails
     */
    public Owner createOwner(Owner owner) throws Exception {
        return dao.insert(owner);
    }

    /**
     * Retrieves an {@link Owner} by its unique identifier.
     * @param id the owner ID
     * @return an {@link Optional} containing the owner if found, otherwise empty
     * @throws Exception if the retrieval fails
     */
    public Optional<Owner> getOwner(int id) throws Exception {
        return dao.findOwnerById(id);
    }

    /**
     * Retrieves all owners.
     * @return a list of all owners
     * @throws Exception if the retrieval fails
     */
    public List<Owner> listOwners() throws Exception {
        return dao.findAllOwners();
    }

    /**
     * Deletes an owner by its unique identifier.
     * @param id the owner ID
     * @throws Exception if the deletion fails
     */
    public void deleteOwner(int id) throws Exception {
        if (dao.deleteById(id)) {
            IO.println("Owner deleted successfully");
        } else {
            IO.println("Owner was not deleted");
        }
    }

    /**
     * Updates an existing owner.
     * @param id the ID of the owner to update
     * @param owner the updated owner data
     * @return the updated owner
     * @throws Exception if the update fails
     */
    public Owner updateOwner(int id, Owner owner) throws Exception {
        return dao.updateOwner(id, owner);
    }

    /**
     * Retrieves owners that match the given filter.
     * @param filter a {@link Predicate} used to filter owners
     * @return a list of owners matching the filter
     * @throws Exception if the operation fails
     */
    public List<Owner> findOwnersByFilter(Predicate<Owner> filter) throws Exception {
        return dao.findOwnersByFilter(filter);
    };

    /**
     * Converts an {@link Owner} object to its JSON representation.
     * @param owner the owner to serialize
     * @return a JSON string representation of the owner
     * @throws JsonProcessingException if serialization fails
     */
    public String ownerToJson(Owner owner) throws JsonProcessingException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(owner);
    }

    /**
     * Converts a JSON string into an {@link Owner} object.
     * @param json the JSON string
     * @return the deserialized owner
     * @throws JsonProcessingException if deserialization fails
     */
    public Owner ownerFromJson(String json) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, Owner.class);
    }

    /**
     * Converts a list of {@link Owner} objects to JSON.
     * @param ownerList the list of owners
     * @return a JSON string representation of the list
     * @throws JsonProcessingException if serialization fails
     */
    public String ownerListToJson(List<Owner> ownerList) throws JsonProcessingException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(ownerList);
    }

    /**
     * Converts a JSON string into a list of {@link Owner} objects.
     * @param json the JSON string
     * @return a list of deserialized owners
     * @throws JsonProcessingException if deserialization fails
     */
    public List<Owner> ownerListFromJson(String json) throws JsonProcessingException {
        return JSON_MAPPER.readValue(json, new TypeReference<List<Owner>>() {
        });
    }

    /**
     * Uploads an image and associates it with the specified owner.
     *
     * @param id          the ID of the owner to associate the image with
     * @param image       the raw bytes of the image file
     * @param fileName    the original name of the image file
     * @param contentType the MIME type of the image (e.g. {@code image/jpeg})
     * @param fileSize    the size of the image file in bytes
     * @return the updated {@link Owner} after the image has been associated
     * @throws Exception if the upload fails or the owner cannot be found
     */
    public Owner uploadImage(int id, byte[] image, String fileName, String contentType, int fileSize) throws Exception {
        return dao.uploadImage(id, image, fileName, contentType, fileSize);
    }

    /**
     * Retrieves the image associated with the specified owner.
     *
     * @param id the ID of the owner whose image is to be retrieved
     * @return the {@link Owner} containing the associated image data
     * @throws Exception if the retrieval fails or no image is found for the owner
     */
    public Owner getOwnerImage(int id) throws Exception {
        return dao.getOwnerImage(id);
    }

    /**
     * Retrieves the image metadata associated with the specified owner.
     *
     * @param id the ID of the owner whose image metadata is to be retrieved
     * @return the {@link Owner} containing the associated image metadata
     * @throws Exception if the retrieval fails or no metadata is found for the owner
     */
    public Owner getOwnerMetadata(int id) throws Exception {
        return dao.getOwnerMetadata(id);
    }

}
