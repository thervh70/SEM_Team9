package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.level.entity.AbstractPowerup;
import nl.tudelft.ti2206.group9.shop.CurrentItems;

/**
 * This utility class stores the State of the game,
 * for example the score and the distance run.
 *
 * @author Maarten, Mitchell
 */
public final class State {

    /** Current score of the player, reset every run. */
    private static double score;
    /** Current amount of coins. */
    private static int coins;
    /** Highest score ever obtained. */
    private static double highscore;

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
    /**
     * Records the sound effect volume and is initially 0.5.
     */
    private static double soundEffectVolume = 1.0 / 2.0;
    /**
     * Records the soundtrack volume and is initially 0.5.
     */
    private static double soundtrackVolume = 1.0 / 2.0;

    /**
     * Constant for the default volume level of a soundtrack.
     */
    private static final double DEFAULT_VOLUMELEVEL = 0.5;

    /** Cannot be constructed. */
    private State() { }

    /** Reset all player data. */
    public static void resetAll() {
        reset();
        setCoins(0);
        highscore = 0;
        CurrentItems.reset();
        soundtrackEnabled = true;
        soundEffectsEnabled = true;
        soundEffectVolume = DEFAULT_VOLUMELEVEL;
        soundtrackVolume = DEFAULT_VOLUMELEVEL;
    }

    /** Reset data that should be reset every run. */
    public static void reset() {
        Track.reset();
        setScore(0);
        Track.setPreviousDistance(0);
        Track.setDistance(0);
        Track.getInstance().getPlayer().respawn();
        AbstractPowerup.resetCounters();
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

    /**
     * Gets the current soundtrack volume.
     *
     * @return current soundtrackVolume (double).
     */
    public static double getSoundtrackVolume() {
        return soundtrackVolume;
    }

    /**
     * Sets the current soundtrack volume.
     *
     * @param newVolume (double) new soundtrack volume to set.
     */
    public static void setSoundtrackVolume(final double newVolume) {
        soundtrackVolume = newVolume;
    }

    /**
     * Gets the current sound effect volume.
     *
     * @return current soundEffectVolume (double).
     */
    public static double getSoundEffectVolume() {
        return soundEffectVolume;
    }

    /**
     * Sets the current sound effects volume.
     *
     * @param newVolume (double) new sound effects volume to set.
     */
    public static void setSoundEffectVolume(final double newVolume) {
        soundEffectVolume = newVolume;
    }

}
