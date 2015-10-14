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
    private boolean connected;
    /** Socket used for communication. */
    private Socket socket;

    /** Default constructor. */
    public HighscoreClient() {
        super();

        final String hostName = "localhost";

        try {
            socket = new Socket(hostName, HighscoreServer.PORT);
            toServer = new PrintWriter(socket.getOutputStream(), true);
            fromServer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            connected = true;
        } catch (UnknownHostException e) {
            System.err.println("Client does not know about host " + hostName);
        } catch (IOException e) {
            System.err.println("Client could not connect to " + hostName);
        }
    }

    /** @return whether the client is connected to the server. */
    public boolean isConnected() {
        return connected;
    }

    /** Disconnects from the server. */
    public void disconnect() {
        if (!connected) {
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
     * Query the server to get the top <pre>amount</pre> highscores.
     * @param amount amount of highscores to get.
     * @param callback the action to be performed on return.
     */
    public void get(final int amount, final QueryCallback callback) {
        query("get " + amount, amount, callback);
    }

    /**
     * Send a score to the server.
     * @param name the name of the user.
     * @param score the score of the user.
     * @param callback the action to be performed on return.
     */
    public void add(final String name, final int score,
                     final QueryCallback callback) {
        query("add " + name + " " + score, 1, callback);
    }

    /**
     * Query the server for information.
     * @param query the query to be sent to the server.
     * @param responseLines the amount of lines expected from server.
     * @param callback the action to be performed on return.
     */
    private void query(final String query, final int responseLines,
                       final QueryCallback callback) {
        if (!connected) {
            callback.callback("FAILED");
        }
        new Thread(() -> {
            try {
                toServer.println(query);
                final StringBuilder response = new StringBuilder();
                for (int i = 0; i < responseLines; i++) {
                    if (i > 0) {
                        response.append('\n');
                    }
                    response.append(fromServer.readLine());
                }
                callback.callback(response.toString());
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

}
