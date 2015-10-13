package nl.tudelft.ti2206.group9.server;

/**
 * The Database that the ServerThreads will communicate with before responding
 * to the clients.
 * @author Maarten
 */
public final class HighscoreDatabase {

    /** Hiding public constructor. */
    private HighscoreDatabase() { }

    /**
     * Query the database.
     * @param query the query to resolve.
     * @return the result from the database.
     */
    public static String query(final String query) {
        String theOutput = null;
        if (query.equals("get user")) {
            theOutput = "Kees";
        }
        return theOutput;
    }
}
