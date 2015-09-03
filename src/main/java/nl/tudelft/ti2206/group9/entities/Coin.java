package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Coins can be picked up by the {@link Player}.
 * @author Maarten
 *
 */
public class Coin extends AbstractEntity {
	
	/** Value added to the score when picked up. */
	public static final int VALUE = 100;
	
	/**
	 * Default constructor.
	 * @param center center of the bounding box
	 */
	public Coin(final Point3D center) {
		super(center, Point3D.UNITCUBE);
	}

	/**
	 * When colliding with Player, Coin should be removed from the field.
	 * @param collidee Entity that this Coin collides with.
	 */
	@Override
	public void collision(final AbstractEntity collidee) {
		if (collidee instanceof Player) {
			selfDestruct();
		}
	}

}
