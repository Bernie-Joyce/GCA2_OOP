package protocol;

import server.ErrorType;

/**
 * Represents a response in the protocol layer, encapsulating a status identifier,
 * a message, status code and Data.
 * @param <T> the type of the response payload
 * @author Jack Cleary
 */
public class Response <T> {
    private String fStatus;
    private String fMessage;
    private int statusCode;
    private T      fData;

    /**
     * Constructs a default {@link Response} with an empty status, message, code and no Data.
     */
    public Response(){
        fStatus = "";
        fMessage = "";
        statusCode = 400;
        fData = null;
    }

    /**
     * Constructs a {@code Response} with the given status, message, error type, and payload.
     *
     * @param status    the status string (e.g. {@code "OK"} or {@code "ERROR"})
     * @param message   a human-readable description of the result
     * @param errorType the error classification, or {@code null} if no error occurred
     * @param data      the response payload
     */
    public Response(String status, String message,ErrorType errorType, T data){
        fStatus = status;
        fMessage = message;
        fData = data;
        this.statusCode = errorType.getHttpStatus();
    }

    // getters

    /** Returns status code. */
    public int getStatusCode() {
        return statusCode;
    }

    /** Returns status. */
    public String getStatus(){ return fStatus;}

    /** Returns message. */
    public String getMessage(){return fMessage;}

    /** Returns data. */
    public T getData() {return fData;}

    // setters

    /** Sets status. */
    public void setStatus(String status){fStatus = status;}

    /** Sets message. */
    public void setMessage(String message){fMessage = message;}

    /** Sets data. */
    public void setData (T data){fData = data;}


    /**
     * Creates a successful {@link Response} instance.
     * @param message a human-readable message describing the result
     * @param data the payload to be returned with the response
     * @param errorType the error type associated with the response (if applicable)
     * @param <T> the type of the response data
     * @return a {@link Response} object representing a successful outcome
     */
    public static <T>Response<T> success(String message, T data,ErrorType errorType){
        return new Response<>("OK",message,errorType, data);}

    /**
     * Creates a failed {@link Response} instance.
     * @param message a human-readable message describing the error
     * @param data the payload to be returned with the response (may be null)
     * @param errorType the error type describing the failure
     * @param <T> the type of the response data
     * @return a {@link Response} object representing a failure
     */
    public static <T>Response<T> failure(String message, T data, ErrorType errorType){
        return new Response<>("ERROR", message,errorType, data);
    }


}
