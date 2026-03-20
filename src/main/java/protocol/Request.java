package protocol;

import com.fasterxml.jackson.databind.JsonNode;

public class Request {
    private String   fType;
    private JsonNode fPayload;

    public Request(){
        fType = "";
        fPayload = null;
    }

    public Request(String type, JsonNode payload){
        fType = type;
        fPayload = payload;
    }

    // getters
    public String getType(){return fType;}

    public JsonNode getPayload(){return fPayload;}

    // setters
    public void setType(String type){fType = type;}

    public void setPayload(JsonNode payload){fPayload = payload;}


}
