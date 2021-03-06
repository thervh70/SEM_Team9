package nl.tudelft.ti2206.group9.level.entity;

import nl.tudelft.ti2206.group9.gui.renderer.AbstractBoxRenderer;
import nl.tudelft.ti2206.group9.gui.renderer.LogRenderer;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Mathias
 */
public class Log extends AbstractObstacle {

    /** Standard bounding box size of a Log. */
    public static final Point3D SIZE = new Point3D(1, 1, 1);
    /** The value of a block of a Log when it's broken by the Player. */
    public static final int VALUE = 50;

    /**
     * Easy to use constructor for standard Log.
     * @param center center of Log
     */
    public Log(final Point3D center) {
        this(center, SIZE);
    }

    /**
     * Default constructor.
     *
     * @param center center of the AbstractObstacle.
     * @param size   size of the AbstractObstacle (i.e. size of bounding box)
     */
    public Log(final Point3D center, final Point3D size) {
        super(center, size);
    }

    @Override
    public AbstractBoxRenderer<? extends AbstractEntity> createRenderer() {
        return new LogRenderer(this);
    }

}
