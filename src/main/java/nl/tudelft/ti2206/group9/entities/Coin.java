package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.renderer.AbstractBoxRenderer;
import nl.tudelft.ti2206.group9.renderer.CoinRenderer;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Coins can be picked up by the {@link Player}.
 * @author Maarten
 *
 */
public class Coin extends Pickup {

	/** Value added to the score when picked up. */
	public static final int VALUE = 100;

	/** Standard bounding box size of a Coin. */
	public static final Point3D SIZE = new Point3D(0.5, 0.5, 0.1);

	/**
	 * Default constructor.
	 * @param center center of the bounding box of the Coin
	 * @param decorating the Pickup that this Coin is decorating.
	 */
	public Coin(final Point3D center, final Pickup decorating) {
		super(center, SIZE, decorating);
	}

	/**
	 * Default constructor, no decorated pickup.
	 * @param center center of the bounding box of the Coin
	 */
	public Coin(final Point3D center) {
		this(center, null);
	}

	@Override
	protected double thisValue() {
		return VALUE;
	}

	@Override
	protected Action thisAction() {
		return () -> State.addCoins(1);
	}

	@Override
	public AbstractBoxRenderer<? extends AbstractEntity> createRenderer() {
		return new CoinRenderer(this);
	}

}
