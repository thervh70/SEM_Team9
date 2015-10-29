package nl.tudelft.ti2206.group9.server;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

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

    /**
     * Default constructor.
     * @param hostName the host name of the server.
     */
    public HighscoreClient(final String hostName) {
        super();

        try {
            socket = new Socket(hostName, HighscoreServer.PORT);
            toServer = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), "UTF-8"), true);
            fromServer = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "UTF-8"));
            connected = true;
        } catch (UnknownHostException e) {
            OBSERVABLE.notify(Category.ERROR, Error.CLIENTCOULDNOTCONNECT,
                    "HighscoreClient.<init> (UnknownHost)", e.getMessage());
        } catch (IOException e) {
            OBSERVABLE.notify(Category.ERROR, Error.CLIENTCOULDNOTCONNECT,
                    "HighscoreClient.<init> (IOException)", e.getMessage());
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
                    connected = false;
                }
            } catch (IOException e) {
                OBSERVABLE.notify(Category.ERROR, Error.CLIENTCOULDNOTCONNECT,
                        "HighscoreClient.disconnect", e.getMessage());
            }
        }).start();
    }

    /**
     * Query the server to get the top <pre>amount</pre> highscores.
     * @param amount amount of highscores to get.
     * @param callback the action to be performed on return.
     */
    public void getGlobal(final int amount, final QueryCallback callback) {
        query("get global " + amount, callback);
    }

    /**
     * Query the server to get the user's top <pre>amount</pre> highscores.
     * @param user the user to get the highscores for.
     * @param amount amount of highscores to get.
     * @param callback the action to be performed on return.
     */
    public void getUser(final String user, final int amount,
            final QueryCallback callback) {
        query("get user " + user + " " + amount, callback);
    }

    /**
     * Send a score to the server.
     * @param name the name of the user.
     * @param score the score of the user.
     * @param callback the action to be performed on return.
     */
    public void add(final String name, final int score,
                    final QueryCallback callback) {
        query("add " + name + " " + score, callback);
    }

    /**
     * Query the server for information.
     * @param query the query to be sent to the server.
     * @param callback the action to be performed on return.
     */
    void query(final String query, final QueryCallback callback) {
        if (!connected) {
            callback.callback("DISCONNECTED");
            return;
        }
        new Thread(() -> {
            try {
                toServer.println(query);
                final StringBuilder response = new StringBuilder();
                String from;
                while (true) {
                    if (response.length() > 0) {
                        response.append('\n');
                    }
                    from = fromServer.readLine();
                    response.append(from);
                    if (from.contains("\4")) {
                        response.deleteCharAt(response.length() - 1);
                        break;
                    }
                    if (from.startsWith("USAGE")) {
                        break;
                    }
                }
                callback.callback(response.toString());
            } catch (IOException e) {
                OBSERVABLE.notify(Category.ERROR, Error.CLIENTCOULDNOTCONNECT,
                        "HighscoreClient.query(" + query + ")", e.getMessage());
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
