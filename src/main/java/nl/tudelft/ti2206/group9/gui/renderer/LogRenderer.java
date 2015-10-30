package nl.tudelft.ti2206.group9.gui.renderer;

import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.entity.AbstractPowerup;
import nl.tudelft.ti2206.group9.level.entity.Log;
import nl.tudelft.ti2206.group9.level.entity.PowerupDestroy;

/**
 * Renders a Coin in the Log.
 * @author Maarten
 */
public class LogRenderer extends AbstractBoxRenderer<Log> {

    /** Amount of milliSeconds per Second. */
    private static final int BLINK_MILLIS = 500;

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

    @Override
    public void update() {
        super.update();
        if (AbstractPowerup.isActive(PowerupDestroy.class)) {
            if ((System.currentTimeMillis() / BLINK_MILLIS) % 2 == 0) {
                setMaterial(Style.WOOD);
            } else {
                setMaterial(Style.WOOD_D);
            }
        } else if (getMaterial().equals(Style.WOOD_D)) {
            setMaterial(Style.WOOD);
        }
    }

}
