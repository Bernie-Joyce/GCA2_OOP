package protocol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {


    @Test
    void defaultConstructor_setsEmptyTypeAndNullPayload() {
        Request req = new Request();
        assertEquals("", req.getType());
        assertNull(req.getPayload());
    }

}