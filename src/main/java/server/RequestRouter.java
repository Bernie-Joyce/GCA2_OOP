package server;
import java.util.HashMap;
import java.util.Map;
import service.*;
import protocol.*;
import domain.*;
import java.util.List;

public class RequestRouter {
    private Map<String, RequestHandler> fHandlers = new HashMap<>();

    public RequestRouter(OwnerService ownerService, CatService catService, NutritionService nutritionService) {
        fHandlers.put(RequestType.GET_ALL_OWNERS.name(), req -> {
            List<Owner> owners = ownerService.listOwners();
            return Response.success("retrieved " + owners.size() + " owners", owners);
        });
    }

    public Response<?> handleRequest(Request request) {
        RequestHandler handler = fHandlers.get(request.getType());

        if (handler == null) {
            return Response.failure("Unknown request type: " + request.getType());
        }

        try {
            // This calls the lambda function mapped in the constructor
            return handler.handle(request);
        } catch (Exception e) {
            return Response.failure("Server error: " + e.getMessage());
        }
    }
}
