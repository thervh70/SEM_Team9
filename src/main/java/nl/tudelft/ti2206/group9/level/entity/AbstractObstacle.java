package nl.tudelft.ti2206.group9.level.entity;

import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Parent for all Obstacles.
 * @author Maarten
 *
 */
public abstract class AbstractObstacle extends AbstractEntity {

    /**
     * Default constructor.
     * @param center center of the AbstractObstacle.
     * @param size size of the AbstractObstacle (i.e. size of bounding box)
     */
    public AbstractObstacle(final Point3D center, final Point3D size) {
        super(center, size);
    }

}
