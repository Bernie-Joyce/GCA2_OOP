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
    private final Map<RequestType, RequestHandler> handlers = new HashMap<>();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public RequestRouter(OwnerService ownerService, CatService catService, NutritionService nutritionService) {

        handlers.put(RequestType.GET_ALL_OWNERS, req -> {
            List<Owner> owners = ownerService.listOwners();
            return Response.success("retrieved " + owners.size() + " owners", owners,ErrorType.SUCCESS);
        });

        handlers.put(RequestType.GET_OWNER_BY_ID, req -> {
            Optional<Owner> optionalOwner = ownerService.getOwner(req.getPayload().asInt());
            if (optionalOwner.isPresent()) {
                Owner owner = optionalOwner.get();
                return Response.success("Retrieved: " + owner.getFirstName(), owner,ErrorType.SUCCESS);
            } else {
                return Response.failure(
                        "Owner not found",
                        ErrorType.RESOURCE_NOT_FOUND.getCode() + "\nStatus Code: " + ErrorType.RESOURCE_NOT_FOUND.getHttpStatus(),
                        ErrorType.RESOURCE_NOT_FOUND
                );
            }
        });

        handlers.put(RequestType.CREATE_OWNER, req -> {
            Owner owner = MAPPER.treeToValue(req.getPayload(), Owner.class);
            ownerService.createOwner(owner);
            return Response.success("Successfully created owner", owner,ErrorType.SUCCESS);
        });

        handlers.put(RequestType.UPDATE_OWNER, req -> {
            Owner_Request owner = MAPPER.treeToValue(req.getPayload(), Owner_Request.class);
            ownerService.updateOwner(owner.getId(), owner.getOwner());
            return Response.success("Successfully updated owner", owner,ErrorType.SUCCESS);
        });

        handlers.put(RequestType.DELETE_OWNER, req -> {
            int ownerId = req.getPayload().asInt();
            if (ownerService.getOwner(ownerId).isEmpty()) {
                return Response.failure(
                        "Owner with id: " + ownerId + "not found",
                         ErrorType.RESOURCE_NOT_FOUND.getCode()+ "\nStatus Code: " + ErrorType.RESOURCE_NOT_FOUND.getHttpStatus(),
                        ErrorType.RESOURCE_NOT_FOUND
                );
            }
            ownerService.deleteOwner(ownerId);
            return Response.success("Owner deleted successfully", ErrorType.RESOURCE_NOT_FOUND.getHttpStatus(),ErrorType.SUCCESS);
        });

        handlers.put(RequestType.GET_NUTRITION_BY_CAT_ID, (req) -> {
            Optional<Nutrition> optionalNutrition = nutritionService.getNutrition(req.getPayload().asInt());
            if (optionalNutrition.isPresent()) {
                Nutrition nutrition = optionalNutrition.get();
                return Response.success("Retrieved Nutrition ", nutrition, ErrorType.SUCCESS);
            } else {
                return Response.failure("Nutrition not found",
                        null,
                        ErrorType.RESOURCE_NOT_FOUND
                );
            }
        });

        handlers.put(RequestType.GET_CAT_BY_ID, (req) -> {
            int catId = req.getPayload().asInt();
            Optional<Cat> optionalCat = catService.getCat(catId);
            if (optionalCat.isPresent()) {
                Cat cat = optionalCat.get();
                return Response.success("Retrieved: " + cat.getName(), cat, ErrorType.SUCCESS);
            } else {
                return Response.failure(
                        "Cat with id " + catId + "not found",
                        ErrorType.RESOURCE_NOT_FOUND.getCode() + ", Status Code: "+ ErrorType.RESOURCE_NOT_FOUND.getHttpStatus(),
                        ErrorType.RESOURCE_NOT_FOUND
                );
            }
        });

        handlers.put(RequestType.GET_ALL_CATS, req -> {
            List<Cat> cats = catService.listCats();
            return Response.success("retrieved " + cats.size() + " owners", cats, ErrorType.SUCCESS);
        });

        handlers.put(RequestType.UPDATE_CAT, req -> {
            Cat_Request catRequest = MAPPER.treeToValue(req.getPayload(), Cat_Request.class);

            Cat cat = catRequest.getCat();

            String error = getValidationErrorCat(cat);
            if (error != null) {
                return Response.failure(error,
                        ErrorType.VALIDATION_ERROR.getCode() + "\nStatus Code: "+ ErrorType.VALIDATION_ERROR.getHttpStatus(),
                        ErrorType.VALIDATION_ERROR);
            }
            catService.updateCat(catRequest.getId(), cat);
            return Response.success("Updated successfully", cat, ErrorType.SUCCESS);
        });

        handlers.put(RequestType.DELETE_CAT, req -> {
            int catId = req.getPayload().asInt();
            if (catService.getCat(catId).isEmpty()) {
                return Response.failure("Cat not found",
                        ErrorType.RESOURCE_NOT_FOUND.getCode() + "\nStatus Code: "+ ErrorType.RESOURCE_NOT_FOUND.getHttpStatus(),
                        ErrorType.RESOURCE_NOT_FOUND
                );
            }
            catService.deleteCat(catId);
            return Response.success("Cat deleted successfully", null, ErrorType.SUCCESS);
        });

        handlers.put(RequestType.CREATE_CAT, req -> {
            Cat cat = MAPPER.treeToValue(req.getPayload(), Cat.class);

            String error = getValidationErrorCat(cat);
            if (error != null) {
                return Response.failure(error, ErrorType.VALIDATION_ERROR.getCode() + "\nStatus Code: "+ ErrorType.VALIDATION_ERROR.getHttpStatus(), ErrorType.VALIDATION_ERROR);
            }

            catService.createCat(cat);
            return Response.success("Successfully created cat", cat, ErrorType.SUCCESS);
        });

        handlers.put(RequestType.FILTER_GENDER_CAT, req -> {
            Gender gender = MAPPER.treeToValue(req.getPayload(), Gender.class);
            List<Cat> cat = catService.filterGender(gender);
            return Response.success("Filtered by " + gender.name(), cat, ErrorType.SUCCESS);
        });
    }

    public Response<?> handleRequest(Request request) {
        RequestType requestType = RequestType.valueOf(request.getType());
        RequestHandler handler = handlers.get(requestType);

        if (handler == null) {
            return Response.failure(
                    "Unknown request type: " + request.getType(),
                    ErrorType.INVALID_REQUEST.getCode() + "\nStatus Code: "+ ErrorType.INVALID_REQUEST.getHttpStatus(),
                    ErrorType.INVALID_REQUEST
            );
        }

        try {
            // This calls the lambda function mapped in the constructor
            return handler.handle(request);
        } catch (Exception e) {
            return Response.failure(
                    "Server error: " + e.getMessage(),
                    ErrorType.INTERNAL_ERROR.getHttpStatus() + ":\nStatus Code: "+ ErrorType.INTERNAL_ERROR.getHttpStatus(),
                    ErrorType.INTERNAL_ERROR
            );
        }
    }

    private String getValidationErrorCat(Cat cat) {
        if (cat.getName() == null || cat.getName().isBlank()) return "Cat name is required";
        if (cat.getGender() == null) return "Cat gender is required";
        if (cat.getIdentifyingMarkings() == null || cat.getIdentifyingMarkings().isBlank()) return "Identifying markings are required";
        if (cat.getBreed() == null || cat.getBreed().isBlank()) return "Cat breed is required";
        if (cat.getDateOfBirth() == null) return "Cat date of birth is required";
        return null;
    }
}
