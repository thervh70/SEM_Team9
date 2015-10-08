package nl.tudelft.ti2206.group9.renderer;

import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.gui.Style;

/**
 * Renders a Coin in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class CoinRenderer extends AbstractBoxRenderer<Coin> {

	/**
	 * Default constructor.
	 * @param entity the entity to Trace.
	 */
	public CoinRenderer(final Coin entity) {
		super(entity);
	}

	@Override
	protected void setMaterial() {
		setMaterial(Style.COIN);
	}

}
