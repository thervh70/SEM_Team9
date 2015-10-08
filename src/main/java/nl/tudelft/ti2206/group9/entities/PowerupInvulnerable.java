package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * When this Powerup is picked up, the player becomes invincible for a little
 * while.
 * @author Maarten
 */
public class PowerupInvulnerable extends Pickup {

	/** The default size for this Powerup. */
	public static final Point3D SIZE = new Point3D(0.5, 0.5, 0.5);

	/**
	 * Default constructor.
	 * @param cent the center of this Powerup.
	 * @param decorating the Pickup that this Powerup is decorating.
	 */
	public PowerupInvulnerable(final Point3D cent, final Pickup decorating) {
		super(cent, SIZE, decorating);
	}

	@Override
	protected double thisValue() {
		return 0;
	}

	@Override
	protected Action thisAction() {
		return () -> {
			//TODO
		};
	}

}
