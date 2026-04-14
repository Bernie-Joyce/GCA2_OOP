package protocol;

/**
 * Defines all supported request types for inter-service communication.
 *
 * <p>Each constant corresponds to a specific operation exposed by one of the
 * available services. The appropriate constant should be set as the type field
 * of a {@link Request} before it is dispatched.</p>
 */
public enum RequestType {

    /**
     * Owner service operations.
     */
    GET_ALL_OWNERS,
    GET_OWNER_BY_ID,
    CREATE_OWNER,
    UPDATE_OWNER,
    DELETE_OWNER,
    FILTER_OWNERS,

    /**
     * Cat service operations.
     */
    GET_ALL_CATS,
    GET_CAT_BY_ID,
    CREATE_CAT,
    DELETE_CAT,
    UPDATE_CAT,
    FILTER_GENDER_CAT,

    /**
     * Nutrition service operations.
     */
    GET_ALL_NUTRITION,
    GET_NUTRITION_BY_CAT_ID,
    CREATE_NUTRITION,
    UPDATE_NUTRITION,
    DELETE_NUTRITION,
    FILTER_NUTRITION,

    /**
     * Connection operations.
     */
    DISCONNECT
}