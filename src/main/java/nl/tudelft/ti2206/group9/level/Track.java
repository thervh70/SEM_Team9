package nl.tudelft.ti2206.group9.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Player;

/**
 * This class holds all entities present in the game, such as Coins, a Player
 * and Obstacles.
 * @author Maarten
 *
 */
public class Track {

	/** List of entities on the track. */
	private final List<AbstractEntity> entities;
	/** Index of the player entity in the entities list. */
	private int player;

	/** Default constructor. */
	public Track() {
		entities = new ArrayList<AbstractEntity>();
		entities.add(new Player());
		player = 0;
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

}
