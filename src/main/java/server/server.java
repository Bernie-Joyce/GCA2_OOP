package server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.*;

import javax.sound.sampled.Port;

public class server {
    private static final int port = 9000;

    private static ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        System.out.println("PORT LISTENING ON: " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());


            try (PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Received: " + line);

                    // Parse the incoming request
                    Request request = MAPPER.readValue(line, Request.class);
                    Response<String> response = Response.success(
                            "Echo: " + request.getType(), "ok"
                    );

                    // Send the response back on one line
                    out.println(MAPPER.writeValueAsString(response));
                }
            }

        }


    }
}
