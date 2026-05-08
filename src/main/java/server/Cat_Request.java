package server;
import domain.Cat;

/**
 * Encapsulates a cat update request for server processing.
 *
 * <p>This class represents the request data required to update an existing cat entity
 * in the system. It is used by {@link RequestRouter} when handling the UPDATE_CAT
 * function in the request handlers map.</p>
 *
 * <p>The request contains:
 *   <ul>
 *     <li>The unique identifier of the cat to be updated</li>
 *     <li>The updated cat data with new attribute values</li>
 *   </ul>
 *
 *  @author Bernard Joyce
 *  @see RequestRouter
 *  @see domain.Cat
 *
 */
public class Cat_Request {

    /**
     * The unique identifier of the cat to be updated.
     */
    private int id;

    /**
     * The cat object containing the updated cat data.
     */
    private Cat cat;

    /**
     * Returns the cat's unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the {@link domain.Cat} object with updated attributes,
     */
    public Cat getCat() {
        return cat;
    }
}