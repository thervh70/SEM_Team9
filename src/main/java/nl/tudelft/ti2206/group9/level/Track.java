package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.gui.GameWindow;
import nl.tudelft.ti2206.group9.util.Point3D;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This class holds all entities present in the game, such as Coins, a Player
 * and Obstacles.
 * @author Maarten
 *
 */
@SuppressWarnings("magicnumber")
public class Track {

	/** Chance per frame to spawn a lane of coins. */
	public static final double COINLANECHANCE = 0.015;
	/** Chance per frame to spawn zigzag of coins. */
	public static final double COINZIGZAGCHANCE = 0.01;
	/** Chance per frame to spawn an obstacle. */
	public static final double OBSTACLECHANCE = 0.01;
	/** The distance between the coins. */
	public static final double COINDISTANCE = 10;

	/** Amount of units the track should move per tick. */
	public static final double UNITS_PER_TICK = 0.4;

	/** Width of the track (amount of lanes). */
	public static final int WIDTH = 3;
	/** Length of the track. */
	public static final double LENGTH = 100;

	/** List of entities on the track. */
	private final List<AbstractEntity> entities;
	/** Index of the player entity in the entities list. */
	private int player;
	/** Minimum number of coins in a coin lane. */
	private int minCoinLaneLength = 5;
	/** Minimum number of coins in a coin zigzag. */
	private int minCoinZigZagLength = 7;

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
	 * @param distance amount of units to move the track
	 */
	public final synchronized void moveTrack(final double distance) {
		synchronized (this) {
			for (final AbstractEntity entity : entities) {
				if (!(entity instanceof Player)) {
					if (entity.getCenter().getZ()
							< GameWindow.CAMERA_TRANS.getZ()) {
						entity.selfDestruct();
					}
					entity.getCenter().addZ(-distance);
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
	public final List<AbstractEntity> getEntities() {
		return Collections.unmodifiableList(entities);
	}

	/**
	 * Checks whether there already exists an entity with the given center.
	 * @param center center
	 * @return boolean
	 */
	public final boolean containsCenter(final Point3D center) {
		for (AbstractEntity entity : entities) {
			if (entity.getCenter().equals(center)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method should be called each ticks. It generates new coins and
	 * obstacles. Also moves the track forward (thus making the Player run).
	 */
	public final void step() {
		synchronized (this) {
			if (coinrunleft > 0) {
				coinrunleft -= UNITS_PER_TICK / COINDISTANCE;
			}
			else {
				double rand = random.nextDouble();
				if (rand < COINZIGZAGCHANCE) {
					createZigZag();
				}
				else if (rand < COINZIGZAGCHANCE + COINLANECHANCE) {
					createCoinLane();
				}
			}
			if (random.nextDouble() < OBSTACLECHANCE) {
				createSingleObstacle();
			}
		}
		getPlayer().step();
		moveTrack(UNITS_PER_TICK);
	}

	/**
	 * Creates a row of coins.
	 */
	private void createCoinLane() {
		int lane = random.nextInt(3) - 1;
		int length = minCoinLaneLength + random.nextInt(10);
		for (int i = 0; i < length; i++) {
			addEntity(new Coin(new Point3D(lane, 1, LENGTH + COINDISTANCE*i)));
		}
		coinrunleft = length;
	}

	/**
	 * Creates a zig-zag of coins.
	 */
	private void createZigZag() {
		int x;
		int lane = random.nextInt(4);
		int length = minCoinZigZagLength + random.nextInt(10);
		for (int i = 0; i < length; i++) {
			if (lane == 3) { x = 0; }
			else { x = lane - 1; }
			addEntity(new Coin(new Point3D(x, 1, LENGTH + COINDISTANCE*i)));
			lane = (lane + 1) % 4;
		}
		coinrunleft = length;
	}

	/**
	 * Creates a single obstacle (1x1x1).
	 */
	private void createSingleObstacle() {
		int x;
		int lane = random.nextInt(4);
		if (this.containsCenter(new Point3D(lane - 1, 0, LENGTH))) {
			lane = (lane + 1) % 3;
		}
		if (lane == 3) { x = 0; }
		else { x = lane - 1; }
		addEntity(new Obstacle(new Point3D(x, 1, LENGTH), new Point3D(1, 1, 1)));
	}

	/**
	 * Returns the coinRunLeft value.
	 *  **For testing purposes only**
	 * @return coinRunLeft
	 */
	public double getCoinrunleft() {
		return coinrunleft;
	}
}
