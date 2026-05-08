package server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.net.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.*;
import service.CatService;
import service.NutritionService;
import service.OwnerService;
import service.ServiceFactory;

/**
 * Multi-threaded TCP server responsible for handling client requests
 * and dispatching them to the appropriate service layer through a
 * {@link RequestRouter}.
 *
 * <p>The server listens on a specified port, accepts incoming socket
 * connections, and processes each client in a separate thread using
 * a cached thread pool.</p>
 *
 * <p>Communication between client and server is performed using JSON
 * messages serialized/deserialized with Jackson's {@link ObjectMapper}.</p>
 * @author Bernard Joyce
 * @author Jack Cleary
 */
public class server {
    private final int port;

    private volatile boolean running = true;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ExecutorService pool;

    /**
     * Creates a new server instance bound to the specified port.
     * @param port the TCP port to listen on (must be between 1024 and 65535)
     * @throws IllegalArgumentException if the port is outside the valid range
     */
    public server(int port){
        if (port < 1_024 || port > 65_535)
            throw new IllegalArgumentException("port must be 1024–65535");
        this.port = port;
        pool = Executors.newCachedThreadPool();
    }

    /**
     * Starts the server and begins listening for incoming client connections.
     *
     * <p>Each accepted client is delegated to a separate thread managed by
     * a cached thread pool.</p>
     * @throws IOException if the server socket fails to open or accept connections
     */
    public void start() throws IOException {
        System.out.println("Server starting on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                Socket clientSocket = serverSocket.accept();    // block until a client arrives
                System.out.println("Accepted: " + clientSocket.getInetAddress());
                pool.submit(new ClientHandler(clientSocket)); // hand off to pool
            }
        }
        finally{
            shutdown();
        }
    }

    /**
     * Shuts down the server's thread pool, waiting for active tasks
     * to complete before forcing termination if necessary.
     */
    private void shutdown() {
        System.out.println("Shutting down thread pool...");
        pool.shutdown();
        try {
            if (!pool.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS)) {
                System.out.println("Pool didn't terminate, forcing shutdown...");
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Thread pool shut down.");
    }

    /**
     * Handles communication with a single connected client.
     *
     * <p>This runnable reads JSON requests from the socket input stream,
     * processes them through the {@link RequestRouter}, and sends back
     * JSON responses to the client.</p>
     */
    private static class ClientHandler implements Runnable {

        private final Socket socket;

        /**
         * Creates a new client handler for the given socket connection.
         *
         * @param socket the client socket
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Processes the client session by reading requests, routing them,
         * and sending back responses until the client disconnects.
         */
        @Override
        public void run() {
            System.out.println("Handling client on " + Thread.currentThread().getName());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out  = new PrintWriter(socket.getOutputStream(), true)) {

                String line;
                while ((line = in.readLine()) != null) {

                    Request req = MAPPER.readValue(line, Request.class);
                    if (req.getType().equals("DISCONNECT")) {
                        System.out.println("Client disconnected cleanly from " + socket.getInetAddress());
                        out.println(MAPPER.writeValueAsString(Response.success("Goodbye", null, ErrorType.SUCCESS)));
                        break;
                    }
//                  System.out.println("ECHO: " + line);
                    ServiceFactory fact = new ServiceFactory();
                    OwnerService ownerService =  fact.createOwnerService();
                    NutritionService nutritionService =  fact.createNutritionService();
                    CatService catService =  fact.createCatService();
                    RequestRouter router = new RequestRouter(ownerService, catService, nutritionService);

                    Response<?> response = router.handleRequest(req);
                    System.out.println("Success");
                    out.println(MAPPER.writeValueAsString(response));
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

    /**
     * Application entry point that starts the server on port 9000.
     * @throws IOException if the server fails to start or bind to the port
     */
     static void main() throws IOException {
        new server(9_000).start();

    }
}
