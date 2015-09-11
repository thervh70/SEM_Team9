package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.util.Point3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class holds all entities present in the game, such as Coins, a Player
 * and Obstacles.
 * @author Maarten, Mitchell
 *
 */
public class Track {

	/** Chance per frame to spawn a coin. */
	public static final double COINCHANCE = 0.02;
	/** Chance per frame to spawn an obstacle. */
	public static final double OBSTACLECHANCE = 0.009;

	/** Amount of units the track should move per tick. */
	public static final double UNITS_PER_TICK = 0.5;

	/** Width of the track (amount of lanes). */
	public static final int WIDTH = 3;
	/** Length of the track. */
	public static final double LENGTH = 100;
	
	/** Standard modulus number for modDistance. */
	public static final int MOD = 50;

	/** List of entities on the track. */
	private final List<AbstractEntity> entities;
	/** Index of the player entity in the entities list. */
	private int player;

	/** Random number generator for generating stuff on the track. */
	private Random random;
	
	/** Current distance moved by the track, reset every run. */
	private static double distance;
	
	/** Default constructor, new Random() is created as generator. */
	public Track() {
		this(new Random());
	}

	/**
	 * Constructor, in which one can specify the Random generator to use.
	 * @param generator the Random generator to use for this Track.
	 */
	public Track(final Random generator) {
		entities = new ArrayList<AbstractEntity>();
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
	public final synchronized List<AbstractEntity> getEntities() {
		return Collections.unmodifiableList(entities);
	}

	/**
	 * @param amount the amount of distance to add
	 */
	void addDistance(final double amount) {
		distance += amount;
	}	
	
	/**
	 * @return the distance
	 */
	double getDistance() {
		return distance;
	}

	/**
	 * @param dist the distance to set
	 */
	void setDistance(final double dist) {
		Track.distance = dist;
	}

	/**
	 * This method should be called each ticks. It generates new coins and
	 * obstacles. Also moves the track forward (thus making the Player run).
	 */
	public final synchronized void step() {
		if (random.nextDouble() < COINCHANCE) {
			addEntity(new Coin(new Point3D(-1, 1.0 / 2, LENGTH)));
			addEntity(new Coin(new Point3D(0, 1.0 / 2, LENGTH)));
    			addEntity(new Coin(new Point3D(1, 1.0 / 2, LENGTH)));
		} else if (random.nextDouble() < OBSTACLECHANCE) {
			addEntity(new Obstacle(
					new Point3D(0, 1.0 / 2, LENGTH),
					new Point3D(WIDTH, 1, 1))
			);
		}
		
		getPlayer().step();

		distance += UNITS_PER_TICK;
		moveTrack(UNITS_PER_TICK);
	}

}
