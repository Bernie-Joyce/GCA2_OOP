package server;
import protocol.*;

/**
 * Functional interface responsible for handling incoming {@link Request} objects
 * and producing a corresponding {@link Response}.
 * @author Jack Cleary
 */
@FunctionalInterface
public interface RequestHandler {

    /**
     * Processes the given {@link Request} and returns a {@link Response}.
     * @param request the incoming request to handle
     * @return the response generated after processing the request
     * @throws Exception if any error occurs during request processing
     */
    Response<?> handle(Request request) throws Exception;
}
