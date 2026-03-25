package protocol;

import server.ErrorType;

public class Response <T> {
    private String fStatus;
    private String fMessage;
    private int statusCode;
    private T      fData;

    public Response(){
        fStatus = "";
        fMessage = "";
        statusCode = 400;
        fData = null;
    }

    public Response(String status, String message,ErrorType errorType, T data){
        fStatus = status;
        fMessage = message;
        fData = data;
        this.statusCode = errorType.getHttpStatus();
    }

    public int getStatusCode() {
        return statusCode;
    }

    // getters
    public String getStatus(){ return fStatus;}

    public String getMessage(){return fMessage;}

    public T getData() {return fData;}

    // setters
    public void setStatus(String status){fStatus = status;}

    public void setMessage(String message){fMessage = message;}

    public void setData (T data){fData = data;}

    //Success Method Response
    public static <T>Response<T> success(String message, T data,ErrorType errorType){
        return new Response<>("OK",message,errorType, data);}

    //Failure Method Response
    public static <T>Response<T> failure(String message, T data, ErrorType errorType){
        return new Response<>("ERROR", message,errorType, data);
    }


}
