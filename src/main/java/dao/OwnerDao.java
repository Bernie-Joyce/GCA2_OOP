package dao;

import domain.Owner;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

}