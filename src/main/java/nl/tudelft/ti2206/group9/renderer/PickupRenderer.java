package nl.tudelft.ti2206.group9.renderer;

import nl.tudelft.ti2206.group9.entities.AbstractPickup;
import nl.tudelft.ti2206.group9.gui.Style;

/**
 * Renders a AbstractPickup in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class PickupRenderer extends AbstractBoxRenderer<AbstractPickup> {

	/**
	 * Default constructor.
	 * @param entity the entity to Trace.
	 */
	public PickupRenderer(final AbstractPickup entity) {
		super(entity);
	}

	@Override
	protected void setMaterial() {
		setMaterial(Style.PICKUP);
	}

}
