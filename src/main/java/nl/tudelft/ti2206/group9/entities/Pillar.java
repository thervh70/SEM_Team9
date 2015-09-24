package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Mathias
 */
public class Pillar extends Obstacle {

    /** Standard bounding box size of a Pillar. */
    public static final Point3D SIZE = new Point3D(1, 4, 1);

    /**
     * Easy to use constructor for standard Pillar.
     * @param center center of Pillar
     */
    public Pillar(final Point3D center) {
        this(center, SIZE);
    }

    /**
     * Default constructor.
     *
     * @param center center of the Obstacle.
     * @param size   size of the Obstacle (i.e. size of bounding box)
     */
    public Pillar(final Point3D center, final Point3D size) {
        super(center, size);
    }
}
