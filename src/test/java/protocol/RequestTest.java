package protocol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void defaultConstructor_setsEmptyTypeAndNullPayload() {
        Request req = new Request();
        assertEquals("", req.getType());
        assertNull(req.getPayload());
    }

    @Test
    void constructor_setsTypeAndPayload_correctly() {
        Request req = new Request("GET_ALL_OWNERS", MAPPER.nullNode());
        assertEquals("GET_ALL_OWNERS", req.getType());
        assertNotNull(req.getPayload());
    }

}