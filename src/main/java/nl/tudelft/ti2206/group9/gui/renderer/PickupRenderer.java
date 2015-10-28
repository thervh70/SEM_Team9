package nl.tudelft.ti2206.group9.gui.renderer;

import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.entity.AbstractPickup;

/**
 * Renders a AbstractPickup in the World.
 * @author Maarten
 */
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
