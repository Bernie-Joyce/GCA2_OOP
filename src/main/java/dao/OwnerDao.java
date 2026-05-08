package dao;

import domain.Nutrition;
import domain.Owner;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Data Access Object for {@link Owner} entities.
 * Defines CRUD operations and JSON serialisation for owners.
 * @author Michal Salabura
 */
public interface OwnerDao {
    /**
     * Inserts a new owner into the data store.
     * @param owner the owner to insert
     * @return the updated {@link Owner}
     * @throws Exception if the insert fails
     */
    Owner insert(Owner owner) throws Exception;

    /**
     * Finds an owner by its ID.
     * @param id the owner's ID
     * @return an {@link Optional} containing the owner if found, or empty if not
     * @throws Exception if the query fails
     */
    Optional<Owner> findOwnerById(int id) throws Exception;

    /**
     * Retrieves all owners from the data store.
     * @return a list of all {@link Owner} objects
     * @throws Exception if the query fails
     */
    List<Owner> findAllOwners() throws Exception;

    /**
     * Deletes an owner by its ID.
     * @param id the ID of the owner to delete
     * @return true if deleted successfully, false if not found
     * @throws Exception if the deletion fails
     */
    boolean deleteById(int id) throws Exception;

    /**
     * Updates an existing owner by ID.
     * @param id the ID of the owner to update
     * @param owner the owner object containing updated values
     * @return the updated {@link Owner}
     * @throws Exception if the update fails
     */
    Owner updateOwner(int id, Owner owner) throws Exception;

    /**
     * Filters owners using a given predicate.
     * @param filter the condition an owner must satisfy to be kept
     * @return a filtered list of {@link Owner} objects
     * @throws Exception if the query fails
     */
    List<Owner> findOwnersByFilter(Predicate<Owner> filter) throws Exception;

    /**
     * Uploads an image and associates it with the specified owner.
     * @param id          the ID of the owner to associate the image with
     * @param image       the raw bytes of the image file
     * @param fileName    the original name of the image file
     * @param contentType the MIME type of the image (e.g. {@code image/jpeg})
     * @param fileSize    the size of the image file in bytes
     * @return the updated {@link Owner} after the image has been associated
     * @throws Exception if the upload fails or the owner cannot be found
     */
    Owner uploadImage(int id, byte[] image, String fileName, String contentType, int fileSize) throws Exception;

    /**
     * Retrieves the image associated with the specified owner.
     * @param id the ID of the owner whose image is to be retrieved
     * @return the {@link Owner} containing the associated image data
     * @throws Exception if the retrieval fails or no image is found for the owner
     */
    Owner getOwnerImage(int id) throws Exception;

    /**
     * Retrieves the image metadata associated with the specified owner.
     * @param id the ID of the owner whose image metadata is to be retrieved
     * @return the {@link Owner} containing the associated image metadata
     * @throws Exception if the retrieval fails or no metadata is found for the owner
     */
    Owner getOwnerMetadata(int id) throws Exception;
}