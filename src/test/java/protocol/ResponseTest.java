package protocol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import server.ErrorType;

public class ResponseTest {

    @Test
    void success_setsStatusOk_andData() {
        Response<String> res = Response.success("done", "hello", ErrorType.SUCCESS);
        assertEquals("OK", res.getStatus());
        assertEquals("done", res.getMessage());
        assertEquals("hello", res.getData());
    }

    @Test
    void failure_setsStatusError_andNullData() {
        Response<String> res = Response.failure("not found", null, ErrorType.RESOURCE_NOT_FOUND);
        assertEquals("ERROR", res.getStatus());
        assertEquals("not found", res.getMessage());
        assertNull(res.getData());
    }

    @Test
    void defaultConstructor_setsEmptyValue() {
        Response<String> res = new Response<>();
        assertEquals("", res.getStatus());
        assertEquals("", res.getMessage());
        assertNull(res.getData());
    }
}