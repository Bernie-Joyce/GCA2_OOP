package client;

import java.io.*;
import java.net.Socket;

public class Client implements AutoCloseable {
    private final BufferedReader in;
    private final PrintWriter out;
    private final Socket socket;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}