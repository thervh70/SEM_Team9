package nl.tudelft.ti2206.group9.level.save;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.ti2206.group9.util.Action;

import com.gamejolt.GameJolt;
import com.gamejolt.UnverifiedUserException;
import com.gamejolt.UserVerificationListener;

/**
 * Uploads the highscores get in the game to GameJolt.
 * @author Maarten
 */
public final class Highscores {

    /** Smoke weed every day! */
    private static final String HASJ = "4121ee7785ba64a86ef5f2f198b06cf4";
    /** GameJolt Game ID. */
    private static final int GAME_ID = 96560;
    /** GameJolt object that communicates with GameJolt. */
    private static GameJolt gj = new GameJolt(GAME_ID, HASJ);
    /** Username of the current user. */
    private static String username;

    /** Hide public constructor. */
    private Highscores() { }

    /**
     * Tries to log in the user.
     * @param name the username.
     * @param userToken the token of the user (which is available online).
     * @param successAction Action to be performed when verification succeeded.
     * @param failedAction Action to be performed when verification failed.
     */
    public static void login(final String name, final String userToken,
            final Action successAction, final Action failedAction) {
        gj.verifyUser(name, userToken, new UserVerificationListener() {
            @Override
            public void verified(final String name) {
                // TODO Log to logger that verification succeeded
                Highscores.username = name;
                successAction.doAction();
            }
            @Override
            public void failedVerification(final String name) {
                // TODO Log to logger that verification failed
                failedAction.doAction();
            }
        });
    }

    /** Logs out the current user. */
    public static void logout() {
        gj = new GameJolt(GAME_ID, HASJ);
    }

    /**
     * Add a highscore to the online highscore table.
     * WARNING: this method is synchronous! Running it will take some time,
     * usually a few seconds!
     * @param score the score to add.
     * @return Whether the adding of the score was successful.
     */
    public static boolean add(final double score) {
        if (username == null) {
            return false;
        }
        try {
            // TODO Log to logger that highscore is being added
            return gj.userAchievedHighscore((int) score);
        } catch (UnverifiedUserException e) {
            // TODO Log to logger that user is not logged in
            return false;
        }
    }

    /**
     * Get the top <pre>amount</pre> highscores from the online list.
     * WARNING: this method is synchronous! Running it will take some time,
     * usually a few seconds!
     * NOTE: Currently still relying on the GameJolt API, will soon change to
     * the new Server-Client system.
     * @param amount amount of scores to fetch.
     * @return A list of {@link Highscore}s.
     */
    public static List<Highscore> get(final int amount) {
        final List<com.gamejolt.highscore.Highscore> list =
                gj.getAllHighscores(amount);
        final List<Highscore> reslist = new ArrayList<>();
        for (final com.gamejolt.highscore.Highscore h : list) {
            reslist.add(new Highscore(h.getUser(), h.getScore()));
        }
        return reslist;
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

        // TODO write a parser for a Highscore string.
        @Override
        public String toString() {
            return "Highscore[" + user + ", " + score + "]";
        }
    }

}
