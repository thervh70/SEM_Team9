package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.entity.Player;
import nl.tudelft.ti2206.group9.level.entity.PowerupInvulnerable;
import nl.tudelft.ti2206.group9.shop.CurrentItems;

/**
 * Renders a Coin in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class PlayerRenderer extends AbstractBoxRenderer<Player> {

    /** Thanks to this constant, the hue rotates once every second. */
    private static final double HUE_PER_TICK = 360 / InternalTicker.FPS;
    /** Size of the invulnerability overlay, relative to 1. */
    private static final double OVERLAY_SCALE = 1.001;
    /** The alpha value (opacity) of the invulnerability overlay. */
    private static final double OVERLAY_ALPHA = 0.05;
    /** The brightness of the invulnerability overlay. */
    private static final double OVERLAY_BRIGHT = 0.4;

    /** Material for the invulnerability overlay. */
    private final PhongMaterial invulMat = new PhongMaterial();
    /** Box for the invulnerability overlay. */
    private Box invulBox = new Box();
    /** Current hue value. */
    private double hue;

    /**
     * Default constructor.
     * @param entity the entity to Trace.
     */
    public PlayerRenderer(final Player entity) {
        super(entity);
        invulBox.setMaterial(invulMat);
    }

    @Override
    protected void setMaterial() {
        setMaterial(CurrentItems.getSkin().getSkinMaterial());
    }

    @Override
    public void update() {
        super.update();
        updatePosition();
        hue += HUE_PER_TICK;
        if (PowerupInvulnerable.isActive()) {
            invulMat.setDiffuseColor(
                    Color.hsb(hue, 1, OVERLAY_BRIGHT, OVERLAY_ALPHA));
            invulBox.setVisible(true);
            invulBox.setManaged(true);
        } else {
            invulMat.setDiffuseColor(Color.hsb(hue, 0, 0, 0));
            invulBox.setVisible(false);
            invulBox.setManaged(false);
        }
    }

    /** Updates the invulBox's position according to the entity's position. */
    private void updatePosition() {
        invulBox.setTranslateX(getTranslateX());
        invulBox.setTranslateY(getTranslateY());
        invulBox.setTranslateZ(getTranslateZ());
        invulBox.setWidth(getWidth() * OVERLAY_SCALE);
        invulBox.setHeight(getHeight() * OVERLAY_SCALE);
        invulBox.setDepth(getDepth() * OVERLAY_SCALE);
    }

    /**
     * Package visibility because this only has to be done in
     * {@link GroupEntitiesRenderer}.
     * @param overlayBox the overlay group to set
     */
    void setOverlay(final Box overlayBox) {
        invulBox = overlayBox;
        invulBox.setMaterial(invulMat);
    }

}
