package server;
import protocol.*;


@FunctionalInterface
public interface RequestHandler {
    Response<?> handle(Request request) throws Exception;
}
