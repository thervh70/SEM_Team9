package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Mathias
 */
public class Log extends Obstacle {

    /** Standard bounding box size of a Log. */
    public static final Point3D SIZE = new Point3D(1, 1, 1);

    /**
     * Easy to use constructor for standard Log.
     * @param center center of Log
     */
    public Log(final Point3D center) {
        super(center, SIZE);
    }

    /**
     * Default constructor.
     *
     * @param center center of the Obstacle.
     * @param size   size of the Obstacle (i.e. size of bounding box)
     */
    public Log(final Point3D center, final Point3D size) {
        super(center, size);
    }
}
