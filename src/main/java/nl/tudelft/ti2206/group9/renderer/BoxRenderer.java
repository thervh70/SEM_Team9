package nl.tudelft.ti2206.group9.renderer;

import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Log;
import nl.tudelft.ti2206.group9.entities.Pillar;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.State;

/**
 * This class renders an entity, meaning that it will create the looks of an
 * Entity that lives in the {@link Track}.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class BoxRenderer extends Box implements Renderer {

	/** The Entity that is traced by this Renderer. */
	private final AbstractEntity traced;

	/**
	 * Default constructor.
	 * @param entity The Entity that should be traced.
	 */
	public BoxRenderer(final AbstractEntity entity) {
		super();
		traced = entity;
		updatePosition();
		setMaterial();
	}

	/** Sets the material according to the traced entity. */
	private void setMaterial() {
		if (traced instanceof Player) {
			setMaterial(State.getSkin().getSkinMaterial());
		} else if (traced instanceof Coin) {
			setMaterial(Style.COIN);
		} else if (traced instanceof Log) {
			setMaterial(Style.WOOD);
		} else if (traced instanceof Pillar) {
			setMaterial(Style.PILLAR);
		} else /*if (traced instanceof Fence) */ {
			setMaterial(Style.FENCE);
		}
	}

	/** Updates the Renderer's position according to the entity's position. */
	private void updatePosition() {
		setTranslateX(traced.getCenter().getX());
		setTranslateY(-traced.getCenter().getY());
		setTranslateZ(traced.getCenter().getZ());
		setWidth(traced.getSize().getX());
		setHeight(traced.getSize().getY());
		setDepth(traced.getSize().getZ());
	}

	/** Updates the position of this BoxRenderer. */
	public void update() {
		updatePosition();
	}

}
