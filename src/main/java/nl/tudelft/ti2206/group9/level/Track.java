package nl.tudelft.ti2206.group9.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.group9.Main;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * This class holds all entities present in the game, such as Coins, a Player
 * and Obstacles.
 * @author Maarten
 *
 */
public class Track {

	/** Chance per frame to spawn a coin. */
	public static final double COINCHANCE = 0.02;
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
	 * Moves the track towards the player (thus making the player run over the
	 * track, like a treadmill).
	 * @param distance amount of units to move the track
	 */
	public void moveTrack(final double distance) {
		synchronized (this) {
			for (final AbstractEntity entity : entities) {
				if (!(entity instanceof Player)) {
					entity.getCenter().addX(-distance);
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
	public Track addEntity(final AbstractEntity entity) {
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
	public Track removeEntity(final AbstractEntity entity) {
		synchronized (this) {
			entities.remove(entity);
		}
		return this;
	}
	
	/**
	 * @return the Player
	 */
	public Player getPlayer() {
		return (Player) entities.get(player);
	}

	/**
	 * @return the entities
	 */
	public List<AbstractEntity> getEntities() {
		return Collections.unmodifiableList(entities);
	}

	/** 
	 * This method should be called each ticks. It generates new coins and
	 * obstacles. Also moves the track forward (thus making the Player run).
	 */
	public void step() {
		if (random.nextDouble() < COINCHANCE) {
			addEntity(new Coin(new Point3D(Main.RENDERDIST, -1, 0)));
			addEntity(new Coin(new Point3D(Main.RENDERDIST, 0, 0)));
			addEntity(new Coin(new Point3D(Main.RENDERDIST, 1, 0)));
		} else if (random.nextDouble() < OBSTACLECHANCE) {
			addEntity(new Obstacle(
					new Point3D(Main.RENDERDIST, 0, 0),
					new Point3D(1, Main.TRACKWIDTH, 1))
			);
		}
		
		moveTrack(UNITS_PER_TICK);
	}

}
