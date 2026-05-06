package protocol;

import org.junit.jupiter.api.Test;
import server.ErrorType;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {

    @Test
    void success_setsStatusOk_andData() {
        Response<String> res = Response.success("done", "hello", ErrorType.SUCCESS);
        assertEquals("OK", res.getStatus());
        assertEquals("done", res.getMessage());
        assertEquals("hello", res.getData());
    }

}