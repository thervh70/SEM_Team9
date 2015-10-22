package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.gui.skin.Skin;
import nl.tudelft.ti2206.group9.level.entity.PowerupInvulnerable;

/**
 * This utility class stores the State of the game,
 * for example the score and the distance run.
 *
 * @author Maarten, Mitchell
 */
@SuppressWarnings("restriction")
public final class State {

    /** Current score of the player, reset every run. */
    private static double score;
    /** Current amount of coins. */
    private static int coins;
    /** Highest score ever obtained. */
    private static double highscore;

    /** Current track, contains all entities. */
    private static Track track;

    /** Name of the player. */
    private static String playerName;

    /**
     * Boolean to determine whether soundtracks are enabled.
     */
    private static boolean soundtrackEnabled;
    /**
     * Boolean to determine whether sound effects are enabled.
     */
    private static boolean soundEffectsEnabled;

    /** Cannot be constructed. */
    private State() { }

    /** Reset all player data. */
    public static void resetAll() {
        reset();
        setCoins(0);
        highscore = 0;
        Skin.setCurrentSkin(Skin.loadSkinsToList().get(0));
        soundtrackEnabled = true;
        soundEffectsEnabled = true;
    }

    /** Reset data that should be reset every run. */
    public static void reset() {
        Track.reset();
        setTrack(Track.getInstance());
        setScore(0);
        Track.setPreviousDistance(0);
        Track.setDistance(0);
        track.getPlayer().respawn();
        PowerupInvulnerable.resetCounter();
    }

    /**
     * @param amount the amount of score to add
     */
    public static void addScore(final double amount) {
        score += amount;
    }

    /**
     * @return the score
     */
    public static double getScore() {
        return score;
    }

    /**
     * @param newScore the score to set
     */
    public static void setScore(final double newScore) {
        score = newScore;
    }

    /**
     * @param amount the amount of coins to add
     */
    public static void addCoins(final int amount) {
        coins += amount;
    }

    /**
     * @return the coins
     */
    public static int getCoins() {
        return coins;
    }

    /**
     * @param newCoins the coins to set
     */
    public static void setCoins(final int newCoins) {
        coins = newCoins;
    }

    /**
     * @return the track
     */
    public static Track getTrack() {
        return track;
    }

    /**
     * @param trck the track to set
     */
    public static void setTrack(final Track trck) {
        State.track = trck;
    }

    /**
     * Call this method to check whether the highscore should be updated.
     */
    public static void checkHighscore() {
        if (score > highscore) {
            highscore = score;
        }
    }

    /**
     * @return the current highest score
     */
    public static int getHighscore() {
        return (int) highscore;
    }

    /**
     * Set the highscore.
      * @param newScore new highscore
     */
    public static void setHighscore(final double newScore) {
        highscore = newScore;
    }

    /**
     * @return the name of the player
     */
    public static String getPlayerName() {
        return playerName;
    }

    /**
     * Set Player name.
     * @param newName Player name.
     */
    public static void setPlayerName(final String newName) {
        State.playerName = newName;
    }

    /**
     * @return whether soundtracks are enabled.
     */
    public static boolean isSoundtrackEnabled() {
        return soundtrackEnabled;
    }

    /**
     * @return whether sound effects are enabled.
     */
    public static boolean isSoundEffectsEnabled() {
        return soundEffectsEnabled;
    }

    /**
     * Change whether the soundtrack is enabled.
     * @param newSoundEnabled true/false soundtrack.
     */
    public static void setSoundtrackEnabled(final boolean newSoundEnabled) {
        State.soundtrackEnabled = newSoundEnabled;
    }

    /**
     * Change whether the soundtrack is enabled.
     * @param newSoundEnabled true/false sound effects.
     */
    public static void setSoundEffectsEnabled(final boolean newSoundEnabled) {
        State.soundEffectsEnabled = newSoundEnabled;
    }

}
