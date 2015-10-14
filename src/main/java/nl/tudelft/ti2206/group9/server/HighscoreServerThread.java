package nl.tudelft.ti2206.group9.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Thread spawned by HighscoreServer every time a client connects.
 * @author Maarten
 */
public class HighscoreServerThread extends Thread {

    /** Socket connected to. */
    private final Socket socket;

    /**
     * Default constructor.
     * @param connection Socket to connect to.
     */
    public HighscoreServerThread(final Socket connection) {
        super("HighscoreServerThread");
        socket = connection;
    }

    @Override
    public void run() {
        PrintWriter toClient;       // Text stream to client
        BufferedReader fromClient;  // Text stream from client
        try {
            toClient = new PrintWriter(socket.getOutputStream(), true);
            fromClient = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            final String clientIP = socket.getRemoteSocketAddress().toString();
            HighscoreServer.log(clientIP + " is connected to me ("
                    + socket.getLocalSocketAddress().toString() + ")");
            String from;
            String to;
            while (true) {
                from = fromClient.readLine();
                if (from == null || from.equals("exit")) {
                    toClient.println("exit");
                    break;
                }
                to = HighscoreDatabase.query(from);
                HighscoreServer.log(from + " -> " + to);
                toClient.println(to);
            }
            socket.close();
            HighscoreServer.log(clientIP + " disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
