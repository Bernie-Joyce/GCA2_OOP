package server;

import domain.Owner;

/**
 * Data Transfer Object (DTO) representing an owner-related request.
 *
 * <p>This class encapsulates the data required to perform operations
 * related to {@link Owner}, such as retrieving, updating, or deleting
 * owner records.</p>
 *
 * <p>It is used for communication between client and server layers.</p>
 * @author Bernard Joyce
 */
public class Owner_Request {
    private int id;
    private Owner owner;

    /**
     * Returns the identifier associated with the request.
     * @author Bernard Joyce
     * @return the owner ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the {@link Owner} payload of the request.
     * @author Bernard Joyce
     * @return the owner data, or {@code null} if not provided
     */
    public Owner getOwner() {
        return owner;
    }

}
