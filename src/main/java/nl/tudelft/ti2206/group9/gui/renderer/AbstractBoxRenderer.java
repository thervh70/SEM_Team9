package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.level.entity.AbstractEntity;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * This class renders an entity, meaning that it will create the looks of an
 * Entity that lives in the {@link Track}.
 * @param <T> the type of the entity traced.
 * @author Maarten
 */
public abstract class AbstractBoxRenderer<T extends AbstractEntity>
            extends Box implements Renderer {

    /** Center of the traced entity. */
    private final Point3D center;
    /** Size of the traced entity. */
    private final Point3D size;

    /**
     * Default constructor.
     * @param entity The Entity that should be traced.
     */
    public AbstractBoxRenderer(final T entity) {
        super();
        center = entity.getCenter();
        size   = entity.getSize();
        updatePosition();
        setMaterial();
    }

    /** Sets the material according to the traced entity. */
    protected abstract void setMaterial();

    /** Updates the Renderer's position according to the entity's position. */
    private void updatePosition() {
        setTranslateX(center.getX());
        setTranslateY(-center.getY());
        setTranslateZ(center.getZ());
        setWidth(size.getX());
        setHeight(size.getY());
        setDepth(size.getZ());
    }

    @Override
    public void update() {
        updatePosition();
    }

}
