package nl.tudelft.ti2206.group9.level;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.group9.entities.*;
import nl.tudelft.ti2206.group9.gui.GameScreen;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * This class holds all entities present in the game, such as Coins, a Player
 * and Obstacles.
 * @author Maarten, Mitchell
 *
 */
public class Track {

	/** Chance per frame to spawn a lane of coins. */
	public static final double COIN_LANE_CHANCE = 0.015;
	/** Chance per frame to spawn zigzag of coins. */
	public static final double COIN_ZIGZAG_CHANCE = 0.01;
	/** Chance per frame to spawn an obstacle. */
	public static final double OBSTACLE_CHANCE = 0.01;
	/** The distance between the coins. */
	public static final double COIN_DISTANCE = 10;

	/** Width of the track (amount of lanes). */
	public static final int WIDTH = 3;
	/** Length of the track. */
	public static final double LENGTH = 100;

	/** Minimum number of coins in a coin lane. */
	private static final int MIN_COIN_LANE_LENGTH = 5;
	/** Minimum number of coins in a coin zigzag. */
	private static final int MIN_COIN_ZIG_ZAG_LENGTH = 7;
	/** Maximum number of coins added to a zigzag or coinlane. */
	private static final int ADD_TO_COINS = 10;
	/** Number of obstacle types in the game. */
	private static final int OBSTACLE_TYPES = 3;

	/** Amount of units the track should move per tick, initially. */
	static final double UNITS_PER_TICK_BASE = 0.4;
	/** Acceleration of the units per tick, per tick. */
	static final double UNITS_PER_TICK_ACCEL = 0.0001;
	/** Current distance moved by the track, reset every run. */
	private static double distance;

	/** List of entities on the track. */
	private final List<AbstractEntity> entities;
	/** Index of the player entity in the entities list. */
	private int player;

	/** Random number generator for generating stuff on the track. */
	private Random random;
	/** Amount of coins that still have to spawn. */
	private double coinrunleft = 0;

	/** Default constructor, new Random() is created as generator. */
	public Track() {
		this(new Random());
	}

	/**
	 * Constructor, in which one can specify the Random generator to use.
	 * @param generator the Random generator to use for this Track.
	 */
	public Track(final Random generator) {
		entities = new LinkedList<AbstractEntity>();
		entities.add(new Player());
		player = 0;
		random = generator;
	}

	/**
	 * Moves the track towards the player (thus making the player run over
	 * the track, like a treadmill).
	 * @param dist amount of units to move the track
	 */
	@SuppressWarnings("restriction")
	public final synchronized void moveTrack(final double dist) {
		synchronized (this) {
			for (final AbstractEntity entity : entities) {
				if (!(entity instanceof Player)) {
					if (entity.getCenter().getZ()
							< GameScreen.CAMERA_TRANS.getZ()) {
						entity.selfDestruct();
					}
					entity.getCenter().addZ(-dist);
					entity.checkCollision(entities.get(player));
				}
			}
		}
	}

	/**
	 * Adds entity to the list of entities.
	 * @param entity entity to add
	 * @return this Track, allowing for chaining.
	 */
	public final synchronized Track addEntity(final AbstractEntity entity) {
		synchronized (this) {
			entities.add(entity);
		}
		return this;
	}

	/**
	 * Removes entity from the list of entities.
	 * @param entity entity to remove
	 * @return this Track, allowing for chaining.
	 */
	public final synchronized Track removeEntity(final AbstractEntity entity) {
		synchronized (this) {
			entities.remove(entity);
		}
		return this;
	}

	/**
	 * @return the Player
	 */
	public final Player getPlayer() {
		return (Player) entities.get(player);
	}

	/**
	 * @return the entities
	 */
	public final synchronized List<AbstractEntity> getEntities() {
		return Collections.unmodifiableList(entities);
	}

	/**
	 * Checks whether there already exists an entity with the given center.
	 * @param center center
	 * @return boolean
	 */
	public final boolean containsCenter(final Point3D center) {
		for (AbstractEntity entity : entities) {
			if (entity.getCenter().getX() == center.getX()
					&& entity.getCenter().getZ() == center.getZ()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param amount the amount to be added
	 */
	static final void addDistance(final double amount) {
		distance += amount;
	}

	/**
	 * @return the distance
	 */
	static final double getDistance() {
		return distance;
	}

	/**
	 * @param dist the distance to set
	 */
	static final void setDistance(final double dist) {
		Track.distance = dist;
	}

	/**
	 * Get the number of Units that pass by per tick at this moment.
	 * @return double Units per Tick
	 */
	public static final double getUnitsPerTick() {
		final double div = Math.pow(UNITS_PER_TICK_ACCEL, -1) / 2
				* UNITS_PER_TICK_BASE * UNITS_PER_TICK_BASE;
		return UNITS_PER_TICK_BASE
				* Math.sqrt(State.getDistance() / div + 1);
	}

	/**
	 * This method should be called each ticks. It generates new coins and
	 * obstacles. Also moves the track forward (thus making the Player run).
	 */
	public final synchronized void step() {
		synchronized (this) {
			double coin = random.nextDouble();
			double obstacle = random.nextDouble();
			if (coinrunleft > 0) {
				coinrunleft -= getUnitsPerTick() / COIN_DISTANCE;
			} else {
				if (coin < COIN_ZIGZAG_CHANCE) {
					createZigZag();
				} else if (coin < COIN_ZIGZAG_CHANCE + COIN_LANE_CHANCE) {
					createCoinLane();
				}
			}
			if (obstacle < OBSTACLE_CHANCE) {
				createSingleObstacle();
			}
		}
		getPlayer().step();

		distance += getUnitsPerTick();
		State.addScore(getUnitsPerTick());
		moveTrack(getUnitsPerTick());
	}

	/**
	 * Creates a row of coins.
	 */
	private void createCoinLane() {
		int lane = random.nextInt(WIDTH) - 1;
		int length = MIN_COIN_LANE_LENGTH + random.nextInt(ADD_TO_COINS);
		for (int i = 0; i < length; i++) {
			addEntity(new Coin(new Point3D(lane, 1,
					LENGTH + COIN_DISTANCE * i)));
		}
		coinrunleft = length;
	}

	/**
	 * Creates a zig-zag of coins.
	 */
	private void createZigZag() {
		int x;
		int lane = random.nextInt(WIDTH + 1);
		int length = MIN_COIN_ZIG_ZAG_LENGTH + random.nextInt(ADD_TO_COINS);
		for (int i = 0; i < length; i++) {
			if (lane == WIDTH) {
				x = 0;
			} else {
				x = lane - 1;
			}
			addEntity(new Coin(new Point3D(x, 1,
					LENGTH + COIN_DISTANCE * i)));
			lane = (lane + 1) % (WIDTH + 1);
		}
		coinrunleft = length;
	}

	/**
	 * Creates a single obstacle (1x1x1).
	 */
	private void createSingleObstacle() {
		int lane = random.nextInt(WIDTH);
		if (this.containsCenter(new Point3D(lane - 1, 0, LENGTH))) {
			lane = (lane + 1) % WIDTH;
		}
		int obstacleType = random.nextInt(OBSTACLE_TYPES);
		Point3D center = new Point3D(lane - 1, 1, LENGTH);
		Obstacle obstacle;
		switch (obstacleType) {
			case 0 : obstacle = new Log(center, Point3D.UNITCUBE); break;
			case 1 : obstacle = new Pillar(center, new Point3D(1, 3, 1)); break;
			case 2 : obstacle = new Fence(new Point3D(lane - 1, 2, LENGTH), new Point3D(1, 2, 1)); break;
			default : obstacle = new Log(center, Point3D.UNITCUBE); break;
		}
		addEntity(obstacle);
	}

	/**
	 * Returns the coinRunLeft value.
	 *  **For testing purposes only**
	 * @return coinRunLeft
	 */
	public final double getCoinrunleft() {
		return coinrunleft;
	}

	/**
	 * Set the Random generator.
	 * @param randomGenerator Random object to set.
	 */
	public final void setRandom(final Random randomGenerator) {
		random = randomGenerator;
	}

}
