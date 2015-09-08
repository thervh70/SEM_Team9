package nl.tudelft.ti2206.group9.level;

/**
 * This utility class stores the State of the game,
 * for example the score and the distance run.
 *
 * @author Maarten
 */
public final class State {

	/** Current score of the player, reset every run. */
	private static int score;
	/** Current amount of coins. */
	private static int coins;

	/** Current track, contains all entities. */
	private static Track track = new Track();

	/** Cannot be constructed. */
	private State() { }

	/** Reset all player data. */
	public static void resetAll() {
		reset();
		setCoins(0);
	}

	/** Reset data that should be reset every run. */
	public static void reset() {
		setTrack(new Track());
		setScore(0);
		track.setDistance(0);
		track.getPlayer().respawn();
	}

	/**
	 * @param amount the amount of score to add
	 */
	public static void addScore(final int amount) {
		score += amount;
	}

	/**
	 * @param amount the amount of coins to add
	 */
	public static void addCoins(final int amount) {
		coins += amount;
	}

	/**
	 * @return the score
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * @param newScore the score to set
	 */
	public static void setScore(final int newScore) {
		State.score = newScore;
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
		State.coins = newCoins;
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

}
