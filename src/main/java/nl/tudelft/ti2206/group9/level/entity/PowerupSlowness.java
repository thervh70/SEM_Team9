package nl.tudelft.ti2206.group9.level.entity;

import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * When this Powerup is picked up, the Track slows down for a little while.
 * @author Maarten
 */
public class PowerupSlowness extends AbstractPowerup {

    /** The value of this Powerup when picked up. */
    private static final int VALUE = 150;

    /**
     * Default constructor.
     * @param cent the center of this Powerup.
     * @param decorating the Pickup that this Powerup is decorating.
     */
    public PowerupSlowness(final Point3D cent,
            final AbstractPickup decorating) {
        super(cent, decorating);
    }

    /**
     * Default constructor, decorating nothing.
     * @param cent the center of this Powerup.
     */
    public PowerupSlowness(final Point3D cent) {
        this(cent, null);
    }

    @Override
    protected double thisValue() {
        return VALUE;
    }

}
