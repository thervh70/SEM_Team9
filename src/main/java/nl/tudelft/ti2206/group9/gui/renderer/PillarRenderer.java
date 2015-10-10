package nl.tudelft.ti2206.group9.gui.renderer;

import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.entity.Pillar;

/**
 * Renders a Coin in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class PillarRenderer extends AbstractBoxRenderer<Pillar> {

	/**
	 * Default constructor.
	 * @param entity the entity to Trace.
	 */
	public PillarRenderer(final Pillar entity) {
		super(entity);
	}

	@Override
	protected void setMaterial() {
		setMaterial(Style.PILLAR);
	}

}
