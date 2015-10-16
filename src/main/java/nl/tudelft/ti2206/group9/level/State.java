package nl.tudelft.ti2206.group9.level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    /** Skin to be used. */
    private static Skin skin;

    /** Current track, contains all entities. */
    private static Track track = new Track();

    /** Name of the player. */
    private static String playerName;

    /**
     * Default savegame directory.
     * Attention! Only use 1 subfolder!
     */
    private static String defaultSaveDir = "sav/";

    /** Boolean to determine whether soundtracks are enabled. */
    private static boolean soundtrackEnabled;
    /** Boolean to determine whether sound effects are enabled. */
    private static boolean soundEffectsEnabled;

    /** List of the names of all the saved games. */
    private static ObservableList<String> saveGames =
            FXCollections.observableArrayList();
    /** Standard modulus number for both modulo calculation. */
    public static final int MOD = 50;

    /** Cannot be constructed. */
    private State() { }

    /** Reset all player data. */
    public static void resetAll() {
        reset();
        setCoins(0);
        highscore = 0;
        skin = Skin.getNoob();
        soundtrackEnabled = true;
        soundEffectsEnabled = true;
    }

    /** Reset data that should be reset every run. */
    public static void reset() {
        setTrack(new Track());
        setScore(0);
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
     * @return the distance of the track
     */
    public static double getDistance() {
        return Track.getDistance();
    }

    /**
     * Update the current distance every {@link #MOD} moves or points increase.
     * @param amount number of (distance or points)
     * @return updated amount
     */
    public static int modulo(final double amount) {
        return (int) (Math.floor(amount / MOD) * MOD);
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

    /**
     * Getter for the current skin.
     * @return The skin.
     */
    public static Skin getSkin() {
        return skin;
    }

    /**
     * If new skins are bought and applied it can be done via this setter.
     * @param newSkin The new skin.
     */
    public static void setSkin(final Skin newSkin) {
        skin = newSkin;
    }

    /**
     * Get the default savegame directory.
     * @return defaultSaveDir
     */
    public static String getDefaultSaveDir() {
        return defaultSaveDir;
    }

    /**
     * Set a new default savegame directory.
     * @param newSaveDir the new savegame directory
     */
    public static void setDefaultSaveDir(final String newSaveDir) {
        State.defaultSaveDir = newSaveDir;
    }

    /**
     * Get the list of the names of all the saved games.
     * @return ObservableList which contains all names of the saved games
     */
    public static ObservableList<String> getSaveGames() {
        return saveGames;
    }
}
