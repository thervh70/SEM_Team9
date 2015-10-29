package nl.tudelft.ti2206.group9.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Uploads the highscores get in the game to GameJolt.
 * @author Maarten
 */
public final class HighscoreClientAdapter {

    /** HighscoreClient for communicating with the server. */
    private static HighscoreClient client = new HighscoreClient("localhost");

    /** Hide public constructor. */
    private HighscoreClientAdapter() { }

    /**
     * Connects to a highscore server. Disconnects from the previous server.
     * @param hostName host name of the server to connect to.
     * @return whether the connection has been established.
     */
    public static boolean connect(final String hostName) {
        disconnect();
        client = new HighscoreClient(hostName);
        return client.isConnected();
    }

    /**
     * Disconnect from the server.
     */
    public static void disconnect() {
        client.disconnect();
    }

    /**
     * Add a highscore to the online highscore table.
     * @param name name of the user.
     * @param score the score to add.
     * @param callback the callback for when the highscore has been added.
     */
    public static void add(final String name, final double score,
            final ResultCallback callback) {
        client.add(name, (int) score, response -> {
            callback.callback(response.equals("SUCCESS"));
        });
    }

    /**
     * Get the top <pre>amount</pre> highscores from the online list.
     * WARNING: this method is asynchronous! The List will be empty on return,
     * and will be filled once the callback is called!
     * @param amount amount of scores to fetch.
     * @param callback the callback for when the highscores have been fetched.
     * @return A list of {@link Highscore}s.
     */
    public static List<Highscore> getGlobal(final int amount,
            final ResultCallback callback) {
        final List<Highscore> res = new ArrayList<>();
        client.getGlobal(amount, response -> {
            if (response.startsWith("Highscore[")) {
                // Response is padded with space to make the final \n split too
                final String[] list = (response + " ").split("\n");
                for (final String s : list) {
                    res.add(Highscore.parse(s));
                }
                callback.callback(true);
            } else {
                callback.callback(false);
            }
        });
        return res;
    }

    /**
     * Get the user's top <pre>amount</pre> highscores from the online list.
     * WARNING: this method is asynchronous! The List will be empty on return,
     * and will be filled once the callback is called!
     * @param name the username to get the highscores for.
     * @param amount amount of scores to fetch.
     * @param callback the callback for when the highscores have been fetched.
     * @return A list of {@link Highscore}s.
     */
    public static List<Highscore> getUser(final String name, final int amount,
            final ResultCallback callback) {
        final List<Highscore> res = new ArrayList<>();
        client.getUser(name, amount, response -> {
            if (response.startsWith("Highscore[")) {
                // Response is padded with space to make the final \n split too
                final String[] list = (response + " ").split("\n");
                for (final String s : list) {
                    res.add(Highscore.parse(s));
                }
                callback.callback(true);
            } else {
                callback.callback(false);
            }
        });
        return res;
    }

    /**
     * Interface used for query callbacks.
     * @author Maarten
     */
    public interface ResultCallback {
        /**
         * @param success whether the query was successful.
         */
        void callback(boolean success);
    }

}
