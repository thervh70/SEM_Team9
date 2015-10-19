package nl.tudelft.ti2206.group9.server;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
    private static void disconnect() {
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
            if (response.equals("SUCCESS")) {
                callback.callback(true);
            } else {
                callback.callback(false);
            }
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
     * Data class, containing a highscore entry.
     * @author Maarten
     */
    public static class Highscore {

        /** User name of this Highscore entry. */
        private final String user;
        /** Score value of this Highscore entry. */
        private final int score;
        /** Default constructor.
         *  @param userName the user name of this entry.
         *  @param scoreValue the score value of this entry. */
        public Highscore(final String userName, final int scoreValue) {
            user = userName;
            score = scoreValue;
        }

        /** @return the user name. */
        public String getUser() {
            return user;
        }

        /** @return the score (integer). */
        public int getScore() {
            return score;
        }

        /**
         * Parses a toString() String to a Highscore.
         * @param toString the String to parse.
         * @return a new Highscore, equal to the String.
         */
        public static Highscore parse(final String toString) {
            try {
                final Scanner sc = new Scanner(toString);
                sc.useDelimiter(", ");
                sc.skip("Highscore\\[");
                if (!sc.hasNext()) {
                    sc.close();
                    return null;
                }
                final String name = sc.next(); // NOPMD - name could be unused
                sc.skip(", ");                 // if !hasNextInt(), that's logic
                sc.useDelimiter("\\]");
                if (!sc.hasNextInt()) {
                    sc.close();
                    return null;
                }
                final int score = sc.nextInt();
                sc.close();
                return new Highscore(name, score);
            } catch (NoSuchElementException e) {
                return null;
            }
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + score;
            if (user == null) {
                result = prime * result;
            } else {
                result = prime * result + user.hashCode();
            }
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Highscore other = (Highscore) obj;
            if (score != other.score) {
                return false;
            }
            if (user == null) {
                if (other.user != null) {
                    return false;
                }
            } else if (!user.equals(other.user)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Highscore[" + user + ", " + score + "]";
        }
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
