package protocol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Unit tests for {@link Request}.
 * @author Michal Salabura
 */
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

    @Test
    void setters_updateTypeAndPayload_correctly() {
        Request req = new Request();
        req.setType("CREATE_OWNER");
        req.setPayload(MAPPER.nullNode());
        assertEquals("CREATE_OWNER", req.getType());
        assertNotNull(req.getPayload());
    }

}