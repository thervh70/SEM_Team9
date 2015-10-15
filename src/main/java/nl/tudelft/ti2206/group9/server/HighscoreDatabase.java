package nl.tudelft.ti2206.group9.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nl.tudelft.ti2206.group9.level.save.Highscores.Highscore;

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
        final StringBuffer theOutput = new StringBuffer();
        switch (sc.next()) {
        case "get": return queryGet(sc, theOutput);
        case "add": return queryAdd(sc);
        default:
            sc.close();
            return "USAGE get|add <args>";
        }
    }

    /**
     * @param sc Scanner that contains the arguments for the query.
     * @param theOutput Pointer to the StringBuffer that will be filled.
     * @return the result of the query.
     */
    private static String queryGet(final Scanner sc,
            final StringBuffer theOutput) {
        if (!sc.hasNext()) {
            sc.close();
            return "USAGE get user|global <args>";
        }
        switch (sc.next()) {
        case "user":   return queryGetUser(sc, theOutput);
        case "global": return queryGetGlobal(sc, theOutput);
        default:       return "USAGE get user|global <args>";
        }
    }

    /**
     * @param sc Scanner that contains the arguments for the query.
     * @param theOutput Pointer to the StringBuffer that will be filled.
     * @return the result of the query.
     */
    private static String queryGetUser(final Scanner sc,
            final StringBuffer theOutput) {
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
        int entries = 0;
        for (final Highscore h : database) {
            if (entries == amount) {
                break;
            }
            if (h.getUser().equals(user)) {
                if (entries > 0) {
                    theOutput.append('\n');
                }
                theOutput.append(h.toString());
                entries++;
            }
        }
        for (; entries < amount; entries++) {
            theOutput.append('\n');
        }
        sc.close();
        return theOutput.toString();
    }

    /**
     * @param sc Scanner that contains the arguments for the query.
     * @param theOutput Pointer to the StringBuffer that will be filled.
     * @return the result of the query.
     */
    private static String queryGetGlobal(final Scanner sc,
            final StringBuffer theOutput) {
        if (!sc.hasNextInt()) {
            sc.close();
            return "USAGE get global <amount:int>";
        }
        final int amount = sc.nextInt();
        int i = 0;
        int entries = 0;
        for (final Highscore h : database) {
            if (i == amount) {
                break;
            }
            if (!theOutput.toString().contains("[" + h.getUser() + ",")) {
                if (entries > 0) {
                    theOutput.append('\n');
                }
                theOutput.append(h.toString());
                entries++;
            }
            i++;
        }
        for (; entries < amount; entries++) {
            theOutput.append('\n');
        }
        sc.close();
        return theOutput.toString();
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
        final Highscore h = new Highscore(name, score);
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i).getScore() < score) {
                database.add(i, h);
                return "SUCCESS";
            }
        }
        database.add(h);
        return "SUCCESS";
    }

}
