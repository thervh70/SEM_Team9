package nl.tudelft.ti2206.group9.level;

import java.util.*;

import nl.tudelft.ti2206.group9.Main;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * This class holds all entities present in the game, such as Coins, a Player
 * and Obstacles.
 * @author Maarten
 *
 */
public class Track {

	/** Chance per frame to spawn a lane of coins. */
	public static final double COINLANECHANCE = 0.015;
	/** Chance per frame to spawn zigzag of coins */
	public static final double COINZIGZAGCHANCE = 0.01;
	/** Chance per frame to spawn an obstacle. */
	public static final double OBSTACLECHANCE = 0.01;

	/** Amount of units the track should move per tick. */
	public static final double UNITS_PER_TICK = 0.15;

	/** List of entities on the track. */
	private final List<AbstractEntity> entities;
	/** Index of the player entity in the entities list. */
	private int player;

	/** Random number generator for generating stuff on the track. */
	private Random random;
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
	public final void moveTrack(final double distance) {
		synchronized (this) {
			for (final AbstractEntity entity : entities) {
				if (!(entity instanceof Player)) {
					if(entity.getCenter().getZ() < 0)
						entities.remove(entity);
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
	public final Track addEntity(final AbstractEntity entity) {
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
	public final Track removeEntity(final AbstractEntity entity) {
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
	 * This method should be called each ticks. It generates new coins and
	 * obstacles. Also moves the track forward (thus making the Player run).
	 */
	public final void step() {
		synchronized (entities) {
			if (coinrunleft > 0) {
				coinrunleft -= UNITS_PER_TICK;
			}
			else {
				double rand = random.nextDouble();
				if(rand < COINZIGZAGCHANCE)
					createZigZag();
				else if(rand < COINZIGZAGCHANCE + COINLANECHANCE)
					createCoinLane();
			}
		}
			moveTrack(UNITS_PER_TICK);
		}

	/**
	 * Creates a row of coins.
	 */
	private void createCoinLane() {
		int lane = -1 + random.nextInt(3);
		int length = 5 + random.nextInt(10);
		for (int i = 0; i < length; i++) {
			addEntity(new Coin(new Point3D(lane, 0, Main.RENDERDIST + i)));
		}
		coinrunleft = length;
	}

	/**
	 * Creates a zig-zag of coins.
	 */
	private void createZigZag() {
		int lane = random.nextInt(4);
		int length = 7 + random.nextInt(10);
		for (int i = 0; i < length; i++) {
			int x = (lane == 3) ? 0 : lane - 1;
			addEntity(new Coin(new Point3D(x, 0, Main.RENDERDIST + i)));
			lane = (lane + 1) % 4;
		}
		coinrunleft = length;
	}

	/**
	 * Returns the coinRunLeft value
	 *  **For testing purposes only**
	 * @return coinRunLeft
	 */
	public double getCoinrunleft() {
		return coinrunleft;
	}
}
