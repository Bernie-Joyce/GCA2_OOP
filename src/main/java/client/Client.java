package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.RequestType;
import protocol.Response;
import protocol.Request;

import java.io.*;
import java.net.Socket;

public class Client implements AutoCloseable {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final BufferedReader in;
    private final PrintWriter out;
    private final Socket socket;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public Response<JsonNode> send(RequestType type, Object payload) throws IOException {
        JsonNode payloadNode = MAPPER.valueToTree(payload);
        Request req = new Request(type.name(), payloadNode);
        out.println(MAPPER.writeValueAsString(req));
        String line = in.readLine();
        return MAPPER.readValue(line, new TypeReference<Response<JsonNode>>() {});
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}