package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.renderer.AbstractBoxRenderer;
import nl.tudelft.ti2206.group9.renderer.FenceRenderer;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Mathias
 */
public class Fence extends AbstractObstacle {

    /** Standard bounding box size of a Fence. */
    public static final Point3D SIZE = new Point3D(1, 2, 1);

    /**
     * Easy to use constructor for standard Fence.
     * @param center center of the Fence
     */
    public Fence(final Point3D center) {
        this(center, SIZE);
    }
    /**
     * Default constructor.
     *
     * @param center center of the AbstractObstacle.
     * @param size   size of the AbstractObstacle (i.e. size of bounding box)
     */
    public Fence(final Point3D center, final Point3D size) {
        super(center, size);
    }

    @Override
    public AbstractBoxRenderer<? extends AbstractEntity> createRenderer() {
        return new FenceRenderer(this);
    }

}
