package nl.tudelft.ti2206.group9.renderer;

import nl.tudelft.ti2206.group9.entities.Pickup;
import nl.tudelft.ti2206.group9.gui.Style;

/**
 * Renders a Pickup in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class PickupRenderer extends AbstractBoxRenderer<Pickup> {

	/**
	 * Default constructor.
	 * @param entity the entity to Trace.
	 */
	public PickupRenderer(final Pickup entity) {
		super(entity);
	}

	@Override
	protected void setMaterial() {
		setMaterial(Style.PICKUP);
	}

}
