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
    private final int port;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private ExecutorService pool;

    public server(int port){
        if (port < 1_024 || port > 65_535)
            throw new IllegalArgumentException("port must be 1024–65535");
        this.port = port;
        pool = Executors.newCachedThreadPool();
    }

    public void start() throws IOException {
        System.out.println("Server starting on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();    // block until a client arrives
                System.out.println("Accepted: " + clientSocket.getInetAddress());
                pool.submit(new ClientHandler(clientSocket)); // hand off to pool
            }
        }
        finally{
            pool.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {

        private Socket socket;

        // Creates: a handler for the given socket
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        // Runs: the client session — reads lines and echoes them
        @Override
        public void run() {
            System.out.println("Handling client on " + Thread.currentThread().getName());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out  = new PrintWriter(socket.getOutputStream(), true)) {

                String line;
                while ((line = in.readLine()) != null) {
                    out.println("ECHO: " + line);
                }
            }
            catch (IOException e) {
                System.out.println("Client disconnected: " + e.getMessage());
            }
            finally {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    // nothing useful to do here
                }
            }
        }
    }
     static void main() throws IOException {
        new server(9_000).start();

    }
}
