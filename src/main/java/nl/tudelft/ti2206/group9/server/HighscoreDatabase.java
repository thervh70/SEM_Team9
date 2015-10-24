package nl.tudelft.ti2206.group9.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Database that the ServerThreads will communicate with before responding
 * to the clients.
 * @author Maarten
 */
public final class HighscoreDatabase {

    /** List containing all highscores ever. */
    private static List<Highscore> database = new ArrayList<>();

    /** This list stores all supported queries. */
    private static List<String[]> supported = new ArrayList<>();
    /** The map that stores the Queries. */
    private static Map<String, Query> queries = new ConcurrentHashMap<>();
    /** The map that stores the arguments per query. */
    private static Map<String, String[]> args = new ConcurrentHashMap<>();
    /** The map that stores the supported argument types. */
    private static Map<Class<?>, String> types = new ConcurrentHashMap<>();

    static {
        types.put(String.class, "string");
        types.put(Integer.class, "int");

        args.put("add", new String[]{"<name:string>", "<score:int>"});
        queries.put("add", arg -> {
            final String name = (String) arg[0];
            final int score = (int) arg[1];
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
        args.put("get global", new String[]{"<amount:int>"});
        queries.put("get global",
                arg -> {
                    final int amount = (int) arg[0];
                    if (amount < 0) {
                        return "";
                    }
                    final StringBuffer theOutput = new StringBuffer();
                    createList(amount, theOutput, h -> !theOutput.toString()
                            .contains("[" + h.getUser() + ","));
                    return theOutput.toString();
                });
        args.put("get user", new String[]{"<name:string>", "<amount:int>"});
        queries.put("get user",
                arg -> {
                    final int amount = (int) arg[1];
                    if (amount < 0) {
                        return "";
                    }
                    final String name = (String) arg[0];
                    final StringBuffer theOutput = new StringBuffer();
                    createList(amount, theOutput,
                            h -> h.getUser().equals(name));
                    return theOutput.toString();
                });

        for (final String q : queries.keySet()) {
            supported.add(q.split(" "));
        }
    }

    /** Hiding public constructor. */
    private HighscoreDatabase() { }

    /**
     * Parses the query and returns the correct result.
     * @param query The query to parse.
     * @return The result of the query on the database.
     */
    public static String query(final String query) {
        final String[] parts = query.split(" ");
        final Set<String> usage = new HashSet<>();
        boolean gotOne = false;
        int i;
        for (i = 0; i < parts.length; i++) {
            gotOne = false;
            for (final String[] q : supported) {
                if (i < q.length && q[i].equals(parts[i])) {
                    gotOne = true;
                    usage.add(q[i]);
                    if (q.length == usage.size()) {
                        return parseArguments(String.join(" ", usage),
                                Arrays.copyOfRange(parts, q.length,
                                        parts.length));
                    }
                }
            }
        }
        if (!gotOne || query.equals("")) {
            i--;
        }
        final Set<String> localUsage = new HashSet<>();
        for (final String[] q : supported) {
            if (i < q.length) {
                localUsage.add(q[i]);
            }
        }
        final StringBuffer usageString = new StringBuffer();
        usageString.append("USAGE");
        if (!usage.isEmpty()) {
            usageString.append(' ');
        }
        usageString.append(String.join(" ", usage)).append(' ')
        .append(String.join("|", localUsage)).append(" <args>");
        return usageString.toString();
    }

    /**
     * @param query The verified query.
     * @param arg The arguments of the verified query.
     * @return The result of the query, or a USAGE String.
     */
    private static String parseArguments(final String query,
            final String... arg) {
        final String[] q = args.get(query);
        if (arg.length > q.length) {
            return "USAGE " + query + " " + String.join(" ", q);
        }
        final StringBuffer usage = new StringBuffer();
        final List<Object> parsedArgs = new ArrayList<>();
        for (int i = 0; i < arg.length; i++) {
            if (arg[i].equals("")) {
                return "USAGE " + query + " " + String.join(" ", q);
            }
            usage.append(' ').append(arg[i]);

            switch (q[i].split(":")[1].split(">")[0]) {
            case "int": parsedArgs.add(Integer.parseInt(arg[i])); break;
            case "string": parsedArgs.add((String) arg[i]); break;
            default: break;
            }
        }
        if (arg.length < q.length) {
            for (int i = arg.length; i < q.length; i++) {
                usage.append(' ').append(q[i]);
            }
            return "USAGE " + query + usage.toString();
        }
        return queries.get(query).query(parsedArgs.toArray());
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
