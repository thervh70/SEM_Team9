package nl.tudelft.ti2206.group9.level;

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

	/** Current track, contains all entities. */
	private static Track track = new Track();

	/** Name of the player. */
	private static String playerName;

	/** Boolean to determine whether sound is enabled. */
	private static boolean soundEnabled;

	/** Standard modulus number for both modulo calculation. */
	public static final int MOD = 50;

	/** Cannot be constructed. */
	private State() { }

	/** Reset all player data. */
	public static void resetAll() {
		reset();
		setCoins(0);
		highscore = 0;
	}

	/** Reset data that should be reset every run. */
	public static void reset() {
		setTrack(new Track());
		setScore(0);
		Track.setDistance(0);
		track.getPlayer().respawn();
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

	public static String getPlayerName() {
		return playerName;
	}

	public static void setPlayerName(String newPlayerName) {
		State.playerName = newPlayerName;
	}

	public static boolean isSoundEnabled() {
		return soundEnabled;
	}

	public static void setSoundEnabled(boolean newSoundEnabled) {
		State.soundEnabled = newSoundEnabled;
	}
}
