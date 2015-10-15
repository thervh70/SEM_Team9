package nl.tudelft.ti2206.group9.level.entity;

import nl.tudelft.ti2206.group9.gui.renderer.AbstractBoxRenderer;
import nl.tudelft.ti2206.group9.gui.renderer.PickupRenderer;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * An abstract class defining a Pickup. This can be anything: a Coin, Powerup,
 * anything that can be picked up.
 * @author Maarten
 */
public abstract class AbstractPickup extends AbstractEntity {

    /** The Pickup that is being decorated by this Pickup. Can be null. */
    private final AbstractPickup decoratedPickup;

    /**
     * Default constructor.
     * @param cent center of the Pickup
     * @param siz size of the Pickup
     * @param decorating the Pickup that this Pickup will decorate (can be null)
     */
    public AbstractPickup(final Point3D cent, final Point3D siz,
            final AbstractPickup decorating) {
        super(cent, siz);
        decoratedPickup = decorating;
    }

    /**
     * Calculates the score Value this Pickup is worth. Takes into account all
     * decorated pickups.
     * @return The total value of this Pickup.
     */
    public final double getValue() {
        double res = thisValue();
        AbstractPickup decorated = decoratedPickup;
        while (decorated != null) {
            res += decorated.thisValue();
            decorated = decorated.decoratedPickup;
        }
        return res;
    }

    /**
     * The value for this particular type of pickup, not taking into account
     * any decorated pickups.
     * @return The value of this AbstractPickup only.
     */
    protected abstract double thisValue();

    /**
     * Executes the actions for all pickups, when picked up. The value of this
     * AbstractPickup is added to the total score.
     */
    public final void doAction() {
        State.addScore(getValue());
        thisAction().doAction();
        AbstractPickup decorated = decoratedPickup;
        while (decorated != null) {
            decorated.thisAction().doAction();
            decorated = decorated.decoratedPickup;
        }
    }

    /**
     * @return The action that this pickup should perform when picked up.
     */
    protected abstract Action thisAction();

    @Override
    public AbstractBoxRenderer<? extends AbstractEntity> createRenderer() {
        return new PickupRenderer(this);
    }

}
