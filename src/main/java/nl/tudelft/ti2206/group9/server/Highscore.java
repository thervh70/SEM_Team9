package nl.tudelft.ti2206.group9.server;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Data class, containing a highscore entry.
 * @author Maarten
 */
public class Highscore {

    /** User name of this Highscore entry. */
    private final String user;
    /** Score value of this Highscore entry. */
    private final int score;

    /**
     * Default constructor.
     * @param userName the user name of this entry.
     * @param scoreValue the score value of this entry.
     */
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
