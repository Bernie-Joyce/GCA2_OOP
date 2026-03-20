package protocol;

public class Response <T> {
    private String fStatus;
    private String fMessage;
    private T      fData;

    public Response(){
        fStatus = "";
        fMessage = "";
        fData = null;
    }

    public Response(String status, String message, T data){
        fStatus = status;
        fMessage = message;
        fData = data;
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
    public static <T>Response<T> success(String message, T data){
        return new Response<>("OK",message, data);}

    //Failure Method Response
    public static <T>Response<T> failure(String message){
        return new Response<>("ERROR", message, null);
    }


}
