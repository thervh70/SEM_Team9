package nl.tudelft.ti2206.group9.renderer;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.entities.PowerupInvulnerable;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.InternalTicker;

/**
 * Renders a Coin in the World.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class PlayerRenderer extends AbstractBoxRenderer<Player> {

	/** Thanks to this constant, the hue rotates once every second. */
	private static final double HUE_PER_TICK = 360 / InternalTicker.FPS;
	/** Size of the invulnerability overlay, relative to 1. */
	private static final double INVUL_SCALE = 1.1;

	/** Material for the invulnerability overlay. */
	private final PhongMaterial invulMat = new PhongMaterial();
	/** Box for the invulnerability overlay. */
	private final Box invulBox = new Box();
	/** Current hue value. */
	private double hue;
	/** Whether the player has overlay box. */
	private boolean hasBox;

	/** The overlays group used in {@link GroupEntitiesRenderer}. */
	private Group overlays;

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
		setMaterial(Style.PLAYER);
	}

	@Override
	public void update() {
		super.update();
		updatePosition();
		hue += HUE_PER_TICK;
		if (PowerupInvulnerable.isActive()) {
			if (!hasBox) {
				Platform.runLater(() -> {
					overlays.getChildren().add(invulBox);
					hasBox = true;
				});
			}
			invulMat.setDiffuseColor(Color.hsb(hue, 1, 1, 1.0 / 2.0));
		} else {
			if (hasBox) {
				Platform.runLater(() -> {
					overlays.getChildren().remove(invulBox);
					hasBox = false;
				});
			}
			invulMat.setDiffuseColor(Color.hsb(hue, 1, 1, 0.0));
		}
	}

	/** Updates the invulBox's position according to the entity's position. */
	private void updatePosition() {
		invulBox.setTranslateX(getTranslateX());
		invulBox.setTranslateY(getTranslateY());
		invulBox.setTranslateZ(getTranslateZ());
		invulBox.setWidth(getWidth() * INVUL_SCALE);
		invulBox.setHeight(getHeight() * INVUL_SCALE);
		invulBox.setDepth(getDepth() * INVUL_SCALE);
	}

	/**
	 * Package visibility because this only has to be done in
	 * {@link GroupEntitiesRenderer}.
	 * @param overlayGroup the overlays group to set
	 */
	void setOverlays(final Group overlayGroup) {
		overlays = overlayGroup;
	}

}
