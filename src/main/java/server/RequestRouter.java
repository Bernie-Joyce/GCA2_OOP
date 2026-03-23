package server;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.*;
import protocol.*;
import domain.*;

import java.util.List;
import java.util.Optional;

public class RequestRouter {
    private Map<RequestType, RequestHandler> handlers = new HashMap<>();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private OwnerService ownerService;
    private CatService catService;
    private NutritionService nutritionService;

    public RequestRouter(OwnerService ownerService, CatService catService, NutritionService nutritionService) {
        this.catService = catService;
        this.nutritionService = nutritionService;
        this.ownerService = ownerService;

        handlers.put(RequestType.GET_ALL_OWNERS, req -> {
            List<Owner> owners = ownerService.listOwners();
            return Response.success("retrieved " + owners.size() + " owners", owners);
        });

        handlers.put(RequestType.GET_OWNER_BY_ID, req -> {
            Optional<Owner> optionalOwner = ownerService.getOwner(req.getPayload().asInt());
            if (optionalOwner.isPresent()) {
                Owner owner = optionalOwner.get();
                return Response.success("Retrieved: " + owner.getFirstName(), owner);
            } else {
                return Response.failure("Owner not found");
            }
        });

        handlers.put(RequestType.CREATE_OWNER, req -> {
            Owner owner = MAPPER.treeToValue(req.getPayload(), Owner.class);
            ownerService.createOwner(owner);
            return Response.success("Successfully created owner", owner);
        });

        handlers.put(RequestType.UPDATE_OWNER, req -> {
            Owner_Request owner = MAPPER.treeToValue(req.getPayload(), Owner_Request.class);
            ownerService.updateOwner(owner.getId(), owner.getOwner());
            return Response.success("Successfully updated owner", owner);
        });

        handlers.put(RequestType.DELETE_OWNER, req -> {
            int ownerId = req.getPayload().asInt();
            if (ownerService.getOwner(ownerId).isEmpty()) {
                return Response.failure("Owner not found");
            }
            ownerService.deleteOwner(ownerId);
            return Response.success("Owner deleted successfully", null);
        });

        handlers.put(RequestType.GET_CAT_BY_ID, (req) -> {
            Optional<Cat> optionalCat = catService.getCat(req.getPayload().asInt());
            if (optionalCat.isPresent()) {
                Cat cat = optionalCat.get();
                return Response.success("Retrieved: " + cat.getName(), cat);
            } else {
                return Response.failure("Cat not found");
            }
        });

        handlers.put(RequestType.GET_NUTRITION_BY_CAT_ID, (req) -> {
            Optional<Nutrition> optionalNutrition = nutritionService.getNutrition(req.getPayload().asInt());
            if (optionalNutrition.isPresent()) {
                Nutrition nutrition = optionalNutrition.get();
                return Response.success("Retrieved Nutrition ", nutrition);
            } else {
                return Response.failure("Nutrition not found");
            }
        });
    }

    public Response<?> handleRequest(Request request) {
        RequestType requestType = RequestType.valueOf(request.getType());
        RequestHandler handler = handlers.get(requestType);

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
