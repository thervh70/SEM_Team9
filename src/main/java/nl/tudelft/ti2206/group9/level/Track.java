package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Fence;
import nl.tudelft.ti2206.group9.entities.Log;
import nl.tudelft.ti2206.group9.entities.Pillar;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.gui.GameScene;
import nl.tudelft.ti2206.group9.util.Point3D;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This class holds all entities present in the game, such as Coins, a Player
 * and Obstacles.
 * @author Maarten, Mitchell
 *
 */
public class Track {

	/** Width of the track (amount of lanes). */
	public static final int WIDTH = 3;
	/** Length of the track. */
	public static final double LENGTH = 100;

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
	/** List of all TrackParts the Track can consist of. */
	private List<TrackPart> trackParts;

	/** Length of the track that is already created. */
	private double trackLeft;

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
		trackParts = new TrackParser().parseTrack();
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
							< GameScene.CAMERA_TRANS.getZ()) {
						entity.selfDestruct();
					}
					moveEntity(entity, -dist);
				}
			}
		}
	}

	/**
	 * Make sure the collisions are checked over the interval that the entities
	 * are moved.
	 * @param entity Entity that the collision is checked with.
	 * @param dist The distance that the second entity has moved.
	 */
	private void moveEntity(final AbstractEntity entity,
			final double dist) {
		double oldZ = entity.getCenter().getZ();
		double diffZ =
				(getPlayer().getSize().getZ() + entity.getSize().getZ())
				* Math.signum(dist);
		for (double i = 0; Math.abs(i) < Math.abs(dist); i += diffZ) {
			entity.getCenter().addZ(diffZ);
			getPlayer().checkCollision(entity);
		}
		entity.getCenter().setZ(oldZ + dist);
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
			if (trackLeft > 0) {
				 trackLeft -= getUnitsPerTick();
			} else {
				final int rand = random.nextInt(trackParts.size());
				final TrackPart part = trackParts.get(rand);
				addTrackPartToTrack(part);
			}
		}
		getPlayer().step();

		distance += getUnitsPerTick();
		State.addScore(getUnitsPerTick());
		moveTrack(getUnitsPerTick());
	}

	/**
	 * Adds al entities from the TrackPart to the Track.
	 * @param part the TrackPart to be added
	 */
	private void addTrackPartToTrack(final TrackPart part) {
		AbstractEntity add = null;
		for (final AbstractEntity entity : part.getEntities()) {
			final Point3D center = new Point3D(entity.getCenter());
			center.addZ(LENGTH);
			if (entity instanceof Coin) {
				add = new Coin(center);
			} else if (entity instanceof Fence) {
				add = new Fence(center);
			} else if (entity instanceof Log) {
				add = new Log(center);
			} else if (entity instanceof Pillar) {
				add = new Pillar(center);
			}
			addEntity(add);
		}
		trackLeft = part.getLength();
	}

}
