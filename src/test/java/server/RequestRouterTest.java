package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.jdbc.JdbcCatDao;
import dao.jdbc.JdbcNutritionDao;
import dao.jdbc.JdbcOwnerDao;
import protocol.Request;
import protocol.Response;
import service.CatService;
import service.NutritionService;
import service.OwnerService;
import java.sql.Connection;
import java.sql.DriverManager;
import domain.Owner;

/** Unit tests for {@link RequestRouter}.
 * @author Michal Salabura
 */
public class RequestRouterTest {

    private static final String URL  = "jdbc:mysql://localhost:3306/catnowner_test?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private RequestRouter _router;
    private JdbcOwnerDao _ownerDao;

    @BeforeEach
    void setUp() throws Exception {
        _ownerDao = new JdbcOwnerDao(URL, USER, PASS);

        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            c.createStatement().executeUpdate("DELETE FROM cats");
            c.createStatement().executeUpdate("DELETE FROM owners");
        }

        OwnerService ownerService = new OwnerService(_ownerDao);
        CatService catService = new CatService(new JdbcCatDao(URL, USER, PASS));
        NutritionService nutritionService = new NutritionService(new JdbcNutritionDao(URL, USER, PASS));
        _router = new RequestRouter(ownerService, catService, nutritionService);
    }

    @Test
    void handleRequest_getAllOwners_returnsSuccessResponse() throws Exception {
        Request req = new Request("GET_ALL_OWNERS", MAPPER.nullNode());
        Response<?> res = _router.handleRequest(req);
        assertEquals("OK", res.getStatus());
    }

    @Test
    void handleRequest_getOwnerById_returnsFailure_whenOwnerDoesNotExist() throws Exception {
        Request req = new Request("GET_OWNER_BY_ID", MAPPER.valueToTree(99999));
        Response<?> res = _router.handleRequest(req);
        assertEquals("ERROR", res.getStatus());
    }

    @Test
    void handleRequest_createOwner_returnsSuccess() throws Exception {
        Owner owner = new Owner("Jane", "Doe", 28, "1 Test St", "0871234567", "jane@example.com");
        Request req = new Request("CREATE_OWNER", MAPPER.valueToTree(owner));
        Response<?> res = _router.handleRequest(req);
        assertEquals("OK", res.getStatus());
    }

    @Test
    void handleRequest_deleteOwner_returnsFailure_whenOwnerDoesNotExist() throws Exception {
        Request req = new Request("DELETE_OWNER", MAPPER.valueToTree(99999));
        Response<?> res = _router.handleRequest(req);
        assertEquals("ERROR", res.getStatus());
    }

    @Test
    void handleRequest_disconnect_returnsSuccess() throws Exception {
        Request req = new Request("DISCONNECT", MAPPER.nullNode());
        Response<?> res = _router.handleRequest(req);
        assertEquals("OK", res.getStatus());
    }
}