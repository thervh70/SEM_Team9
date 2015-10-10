package nl.tudelft.ti2206.group9.gui.renderer;

import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.entity.Log;

/**
 * Renders a Coin in the Log.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class LogRenderer extends AbstractBoxRenderer<Log> {

    /**
     * Default constructor.
     * @param entity the entity to Trace.
     */
    public LogRenderer(final Log entity) {
        super(entity);
    }

    @Override
    protected void setMaterial() {
        setMaterial(Style.WOOD);
    }

}
