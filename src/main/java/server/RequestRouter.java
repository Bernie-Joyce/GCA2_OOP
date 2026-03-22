package server;
import java.util.HashMap;
import java.util.Map;
import service.*;
import protocol.*;
import domain.*;
import java.util.List;

public class RequestRouter {
    private Map<String, RequestHandler> fHandlers = new HashMap<>();

    public RequestRouter(OwnerService ownerService) {
        fHandlers.put(RequestType.GET_ALL_OWNERS.name(), req -> {
            List<Owner> owners = ownerService.listOwners();
            return Response.success("retrieved " + owners.size() + " owners", owners);
        });
    }
}
