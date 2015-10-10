package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.transform.Rotate;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.entity.Coin;

/**
 * Renders a Coin in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class CoinRenderer extends AbstractBoxRenderer<Coin> {

    /** The amount of degrees the coin should rotate per tick. */
    public static final double ROTATE_PER_TICK = 6;
    /** The current rotation of the coin. */
    private double rotate;

    /**
     * Default constructor.
     * @param entity the entity to Trace.
     */
    public CoinRenderer(final Coin entity) {
        super(entity);
        getTransforms().add(new Rotate(0.0, Rotate.Y_AXIS));
    }

    @Override
    protected void setMaterial() {
        setMaterial(Style.COIN);
    }

    @Override
    public void update() {
        super.update();
        rotate += ROTATE_PER_TICK;
        final Rotate rotationTransform = new Rotate(rotate, Rotate.Y_AXIS);
        getTransforms().remove(getTransforms().size() - 1);
        getTransforms().add(rotationTransform);
    }

}
