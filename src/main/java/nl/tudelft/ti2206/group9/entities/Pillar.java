package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Mathias
 */
public class Pillar extends Obstacle {

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
