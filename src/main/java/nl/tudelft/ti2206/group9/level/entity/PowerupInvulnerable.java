package nl.tudelft.ti2206.group9.level.entity;

import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * When this Powerup is picked up, the player becomes invincible for a little
 * while.
 * @author Maarten
 */
public class PowerupInvulnerable extends AbstractPickup {

	/** The default size for this Powerup. */
	public static final Point3D SIZE = new Point3D(0.5, 0.5, 0.5);
	/** The default time this Powerup should last. */
	public static final int SECONDS = 10;

	/** The amount of ticks that this countdown still lasts. */
	private static int countdown;

	/**
	 * Default constructor.
	 * @param cent the center of this Powerup.
	 * @param decorating the Pickup that this Powerup is decorating.
	 */
	public PowerupInvulnerable(final Point3D cent,
			final AbstractPickup decorating) {
		super(cent, SIZE, decorating);
	}

	/**
	 * Default constructor, decorating nothing.
	 * @param cent the center of this Powerup.
	 */
	public PowerupInvulnerable(final Point3D cent) {
		this(cent, null);
	}

	/** Is called every step in {@link Track}. */
	public static void step() {
		if (countdown != 0) {
			countdown--;
		}
	}

	/** @return Whether this Powerup is active. */
	public static boolean isActive() {
		return countdown != 0;
	}

	/** Activates this Powerup foreveerrrrr.
	 *  @param enable whether the cheat should be enabled */
	public static void cheat(final boolean enable) {
		if (enable) {
			countdown = -1;
		} else {
			countdown = 0;
		}
	}

	@Override
	protected double thisValue() {
		return 0;
	}

	@Override
	protected Action thisAction() {
		return () -> countdown = SECONDS * InternalTicker.FPS;
	}

	/** @return the amount of seconds until this Powerup's effect stops. */
	public static double getSecondsLeft() {
		return (double) countdown / (double) InternalTicker.FPS;
	}

}
