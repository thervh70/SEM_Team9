package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Mathias
 */
public class Fence extends Obstacle {

    /** Standard bounding box size of a Fence. */
    public static final Point3D SIZE = new Point3D(1, 2, 1);
    /** Standard center heigth. */
    public static final double HEIGHT = 2.3;

    /**
     * Easy to use constructor for standard Fence.
     * @param center center of the Fence
     */
    public Fence(final Point3D center) {
        super(center, SIZE);
    }
    /**
     * Default constructor.
     *
     * @param center center of the Obstacle.
     * @param size   size of the Obstacle (i.e. size of bounding box)
     */
    public Fence(final Point3D center, final Point3D size) {
        super(center, size);
    }
}
