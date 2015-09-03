package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Parent for all Obstacles.
 * @author Maarten
 *
 */
public class Obstacle extends AbstractEntity {

	/** Default constructor */
	public Obstacle(final Point3D center, final Point3D size) {
		super(center, size);
	}

	/**
	 * Collisions handled in {@link Player}
	 * @see nl.tudelft.ti2206.group9.entities.AbstractEntity#collision
	 * (nl.tudelft.ti2206.group9.entities.AbstractEntity)
	 */
	@Override
	public void collision(final AbstractEntity collidee) { }

}
