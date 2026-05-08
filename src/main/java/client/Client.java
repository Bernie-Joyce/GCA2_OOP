package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.RequestType;
import protocol.Response;
import protocol.Request;

import java.io.*;
import java.net.Socket;

/**
 * Client that sends requests to the server and receives responses.
 * Implements {@link AutoCloseable} for use in try-with-resources.
 * @author Michal Salabura
 */
public class Client implements AutoCloseable {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final BufferedReader in;
    private final PrintWriter out;
    private final Socket socket;

    /**
     * Creates a new Client and connects to the server.
     * @author Michal Salabura
     * @param host the server IP address
     * @param port the server port number
     * @throws IOException if the connection cannot be established
     */
    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Sends a request to the server and returns the response.
     * @author Michal Salabura
     * @param type the type of request to send
     * @param payload the request data, serialised to JSON
     * @return the server's response containing a {@link JsonNode}
     * @throws IOException if the request fails or the connection is lost
     */
    public Response<JsonNode> send(RequestType type, Object payload) throws IOException {
        JsonNode payloadNode = MAPPER.valueToTree(payload);
        Request req = new Request(type.name(), payloadNode);
        out.println(MAPPER.writeValueAsString(req));
        String line = in.readLine();
        return MAPPER.readValue(line, new TypeReference<Response<JsonNode>>() {});
    }

    /**
     * Closes the underlying socket connection.
     * @author Michal Salabura
     * @throws IOException if the socket cannot be closed
     */
    @Override
    public void close() throws IOException {
        try {
            send(RequestType.DISCONNECT, null);
        } catch (Exception ignored) {}
        socket.close();
    }
}