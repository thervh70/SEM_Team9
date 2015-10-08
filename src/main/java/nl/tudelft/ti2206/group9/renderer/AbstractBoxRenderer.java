package nl.tudelft.ti2206.group9.renderer;

import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;

/**
 * This class renders an entity, meaning that it will create the looks of an
 * Entity that lives in the {@link Track}.
 * @param <T> the type of the entity traced.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public abstract class AbstractBoxRenderer<T extends AbstractEntity>
			extends Box implements Renderer {

	/** The Entity that is traced by this Renderer. */
	private final T traced;

	/**
	 * Default constructor.
	 * @param entity The Entity that should be traced.
	 */
	public AbstractBoxRenderer(final T entity) {
		super();
		traced = entity;
		updatePosition();
		setMaterial();
	}

	/** Sets the material according to the traced entity. */
	protected abstract void setMaterial();

	/** Updates the Renderer's position according to the entity's position. */
	private void updatePosition() {
		setTranslateX(traced.getCenter().getX());
		setTranslateY(-traced.getCenter().getY());
		setTranslateZ(traced.getCenter().getZ());
		setWidth(traced.getSize().getX());
		setHeight(traced.getSize().getY());
		setDepth(traced.getSize().getZ());
	}

	/** Updates the position of this AbstractBoxRenderer. */
	public void update() {
		updatePosition();
	}

}
