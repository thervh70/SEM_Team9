package nl.tudelft.ti2206.group9.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Database that the ServerThreads will communicate with before responding
 * to the clients.
 * @author Maarten
 */
public final class HighscoreDatabase {

    /** List containing all highscores ever. */
    private static List<Highscore> database = new ArrayList<>();

    /** Hiding public constructor. */
    private HighscoreDatabase() { }

    /**
     * Query the database.
     * @param query the query to resolve.
     * @return the result from the database.
     */
    public static String query(final String query) {
        final Scanner sc = new Scanner(query);
        if (!sc.hasNext()) {
            sc.close();
            return "USAGE get|add <args>";
        }
        switch (sc.next()) {
        case "get": return queryGet(sc);
        case "add": return queryAdd(sc);
        default:
            sc.close();
            return "USAGE get|add <args>";
        }
    }

    /**
     * @param sc Scanner that contains the arguments for the query.
     * @return the result of the query.
     */
    private static String queryGet(final Scanner sc) {
        if (!sc.hasNext()) {
            sc.close();
            return "USAGE get user|global <args>";
        }
        switch (sc.next()) {
        case "user":   return queryGetUser(sc);
        case "global": return queryGetGlobal(sc);
        default:       return "USAGE get user|global <args>";
        }
    }

    /**
     * @param sc Scanner that contains the arguments for the query.
     * @return the result of the query.
     */
    private static String queryGetUser(final Scanner sc) {
        if (!sc.hasNext()) {
            sc.close();
            return "USAGE get user <name:string> <amount:int>";
        }
        final String user = sc.next();
        if (!sc.hasNextInt()) {
            sc.close();
            return "USAGE get user " + user + " <amount:int>";
        }
        final int amount = sc.nextInt();
        sc.close();
        return QueryParser.supported.get("get user <name:string> <amount:int>")
                .query(user, amount);
    }

    /**
     * @param sc Scanner that contains the arguments for the query.
     * @return the result of the query.
     */
    private static String queryGetGlobal(final Scanner sc) {
        if (!sc.hasNextInt()) {
            sc.close();
            return "USAGE get global <amount:int>";
        }
        final int amount = sc.nextInt();
        sc.close();
        return QueryParser.supported.get("get global <amount:int>")
                .query(amount);
    }

    /**
     * Creates the list of highscores, querying according to a condition.
     * @param amount the size of the query result list.
     * @param theOutput the StringBuffer containing the result.
     * @param condition condition whether to add the Highscore to the result.
     */
    private static void createList(final int amount,
            final StringBuffer theOutput, final QueryCondition condition) {
        int entries = 0;
        for (final Highscore h : database) {
            if (entries == amount) {
                break;
            }
            if (condition.condition(h)) {
                if (entries > 0) {
                    theOutput.append('\n');
                }
                theOutput.append(h.toString());
                entries++;
            }
        }
        theOutput.append('\4');
    }

    /**
     * @param sc Scanner that contains the arguments for the query.
     * @return the result of the query.
     */
    private static String queryAdd(final Scanner sc) {
        if (!sc.hasNext()) {
            sc.close();
            return "USAGE add <name:string> <score:int>";
        }
        final String name = sc.next();
        if (!sc.hasNextInt()) {
            sc.close();
            return "USAGE add " + name + " <score:int>";
        }
        final int score = sc.nextInt();
        return QueryParser.supported.get("add <name:string> <score:int>")
                .query(name, score);
    }

    /** Has the responsibility to parse Queries and return correct results. */
    private static class QueryParser {
        /** The map that stores the supported Queries. */
        private static Map<String, Query> supported = new ConcurrentHashMap<>();
        static {
            supported.put("add <name:string> <score:int>",
                    args -> {
                        final String name = (String) args[0];
                        final int score = (int) args[1];
                        final Highscore h = new Highscore(name, score);
                        for (int i = 0; i < database.size(); i++) {
                            if (database.get(i).getScore() < score) {
                                database.add(i, h);
                                return "SUCCESS";
                            }
                        }
                        database.add(h);
                        return "SUCCESS";
                    });
            supported.put("get user <name:string> <amount:int>",
                    args -> {
                        final int amount = (int) args[1];
                        if (amount < 0) {
                            return "";
                        }
                        final String name = (String) args[0];
                        final StringBuffer theOutput = new StringBuffer();
                        createList(amount, theOutput,
                                h -> h.getUser().equals(name));
                        return theOutput.toString();
                    });
            supported.put("get global <amount:int>",
                    args -> {
                        final int amount = (int) args[0];
                        if (amount < 0) {
                            return "";
                        }
                        final StringBuffer theOutput = new StringBuffer();
                        createList(amount, theOutput, h -> !theOutput.toString()
                                .contains("[" + h.getUser() + ","));
                        return theOutput.toString();
                    });
        }
    }

    /**
     * A Query interface (used mostly anonymously).
     * Used for: "global", "get user", "get global".
     */
    private interface Query {
        /**
         * @param args The arguments for the query.
         * @return The result of the query.
         */
        String query(Object... args);
    }

    /**
     * Interface to define a condition in a query.
     * @author Maarten
     */
    private interface QueryCondition {
        /**
         * @param h the Highscore the condition should check for.
         * @return true or false according to the condition.
         */
        boolean condition(Highscore h);
    }

}
