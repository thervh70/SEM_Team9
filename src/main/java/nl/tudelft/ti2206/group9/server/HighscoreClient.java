package nl.tudelft.ti2206.group9.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client used for retrieving/sending data to the Server.
 * @author Maarten
 */
public class HighscoreClient {

    /** Text stream to server. */
    private PrintWriter toServer;
    /** Text stream from server. */
    private BufferedReader fromServer;
    /** Whether the Client is connected to the server. */
    private boolean isConnected;
    /** Socket used for communication. */
    private Socket socket;

    /** Default constructor. */
    public HighscoreClient() {
        super();

        final String hostName = "127.0.0.1";

        try {
            socket = new Socket(hostName, HighscoreServer.PORT);
            toServer = new PrintWriter(socket.getOutputStream(), true);
            fromServer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            isConnected = true;
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(1);
        }
    }

    /** Disconnects from the server. */
    public void disconnect() {
        if (!isConnected) {
            return;
        }
        new Thread(() -> {
            try {
                toServer.println("exit");
                final String response = fromServer.readLine();
                if (response.equals("exit")) {
                    socket.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Query the server for information.
     * @param query the query to be sent to the server.
     * @param callback the action to be performed on return.
     */
    public void query(final String query, final QueryCallback callback) {
        if (!isConnected) {
            callback.callback(null);
        }
        new Thread(() -> {
            try {
                toServer.println(query);
                final String response = fromServer.readLine();
                callback.callback(response);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Interface used for callbacks from queries.
     * @author Maarten
     */
    public interface QueryCallback {
        /**
         * Action performed when the query returns.
         * @param response the response from the server.
         *        if is null, the server could not respond.
         */
        void callback(String response);
    }

    /**
     * Used for testing.
     * @param args none
     */
    public static void main(final String... args) {
        final HighscoreClient client = new HighscoreClient();
        client.query("get user", response -> System.out.println(response));
        client.disconnect();
    }

}
