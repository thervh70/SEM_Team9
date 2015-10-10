package nl.tudelft.ti2206.group9.gui.renderer;

import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.entity.Fence;

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
