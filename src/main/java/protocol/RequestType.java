package protocol;

public enum RequestType {

    // Owner service
    GET_ALL_OWNERS,
    GET_OWNER_BY_ID,
    CREATE_OWNER,
    UPDATE_OWNER,
    DELETE_OWNER,
    FILTER_OWNERS,
    UPLOAD_OWNER_IMAGE,

    // Cat service
    GET_ALL_CATS,
    GET_CAT_BY_ID,
    CREATE_CAT,
    DELETE_CAT,
    UPDATE_CAT,
    FILTER_GENDER_CAT,

    // Nutrition service
    GET_ALL_NUTRITION,
    GET_NUTRITION_BY_CAT_ID,
    CREATE_NUTRITION,
    UPDATE_NUTRITION,
    DELETE_NUTRITION,
    FILTER_NUTRITION,

    // Connection
    DISCONNECT
}