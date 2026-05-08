package protocol;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents a request in the protocol layer, encapsulating a type identifier
 * and a JSON payload.
 * @author Jack Cleary
 */
public class Request {
    private String   fType;
    private JsonNode fPayload;


    /**
     * Constructs a default {@link Request} with an empty type and no payload.
     * @author Jack Cleary
     */
    public Request(){
        fType = "";
        fPayload = null;
    }

    /**
     * Constructs a {@link Request} with the specified type and payload.
     * @author Jack Cleary
     * @param type    the request type identifier
     * @param payload the JSON payload associated with this request
     */
    public Request(String type, JsonNode payload){
        fType = type;
        fPayload = payload;
    }

    // getters

    /**
     * Returns the type identifier of this request.
     * @author Jack Cleary
     * @return the request type as a String
     */
    public String getType(){return fType;}

    /**
     * Returns the JSON payload of this request.
     * @author Jack Cleary
     * @return the payload as a {@link JsonNode}
     */
    public JsonNode getPayload(){return fPayload;}

    // setters

    /**
     * Sets the type identifier of this request.
     * @author Jack Cleary
     * @param type the new request type
     */
    public void setType(String type){fType = type;}

    /**
     * Sets the JSON payload of this request.
     * @author Jack Cleary
     * @param payload the new payload as a {@link JsonNode}
     */
    public void setPayload(JsonNode payload){fPayload = payload;}


}
