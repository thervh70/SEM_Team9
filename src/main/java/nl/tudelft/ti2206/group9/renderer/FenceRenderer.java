package nl.tudelft.ti2206.group9.renderer;

import nl.tudelft.ti2206.group9.entities.Fence;
import nl.tudelft.ti2206.group9.gui.Style;

/**
 * Renders a Fence in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class FenceRenderer extends AbstractBoxRenderer<Fence> {

	/**
	 * Default constructor.
	 * @param entity the entity to Trace.
	 */
	public FenceRenderer(final Fence entity) {
		super(entity);
	}

	@Override
	protected void setMaterial() {
		setMaterial(Style.FENCE);
	}

}
