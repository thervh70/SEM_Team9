package nl.tudelft.ti2206.group9.entities;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.renderer.AbstractBoxRenderer;
import nl.tudelft.ti2206.group9.renderer.PickupRenderer;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.Point3D;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;

/**
 * An abstract class defining a Pickup. This can be anything: a Coin, Powerup,
 * anything that can be picked up.
 * @author Maarten
 */
public abstract class Pickup extends AbstractEntity {

	/** The Pickup that is being decorated by this Pickup. Can be null. */
	private final Pickup decoratedPickup;

	/**
	 * Default constructor.
	 * @param cent cender of the pickup
	 * @param siz size of the pickup
	 * @param decorating the Pickup that this Pickup will decorate (can be null)
	 */
	public Pickup(final Point3D cent, final Point3D siz,
			final Pickup decorating) {
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
		Pickup decorated = decoratedPickup;
		while (decorated != null) {
			res += decorated.thisValue();
			decorated = decorated.decoratedPickup;
		}
		return res;
	}

	/**
	 * The value for this particular type of pickup, not taking into account
	 * any decorated pickups.
	 * @return The value of this Pickup only.
	 */
	protected abstract double thisValue();

	/**
	 * Executes the actions for all pickups, when picked up. The value of this
	 * Pickup is added to the total score.
	 */
	public final void doAction() {
		State.addScore(getValue());
		thisAction().doAction();
		Pickup decorated = decoratedPickup;
		while (decorated != null) {
			decorated.thisAction().doAction();
			decorated = decorated.decoratedPickup;
		}
	}

	/**
	 * @return The action that this pickup should perform when picked up.
	 */
	protected abstract Action thisAction();

	/**
	 * When colliding with Player, Pickup should be removed from the field.
	 * Also, the action defined in {@link Pickup#doAction()} is performed.
	 * @param collidee Entity that this Pickup collides with.
	 */
	@Override
	public final void collision(final AbstractEntity collidee) {
		if (collidee instanceof Player) {
			OBSERVABLE.notify(Category.PLAYER, GameObserver.Player.COLLISION,
					this.getClass().getSimpleName());
			doAction();
			selfDestruct();
		}
	}

	@Override
	public AbstractBoxRenderer<? extends AbstractEntity> createRenderer() {
		return new PickupRenderer(this);
	}

}
