package nl.tudelft.ti2206.group9.renderer;

import javafx.scene.Group;

/**
 * Renders the entire Track. The Track is a collection of tiles.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public abstract class AbstractGroupRenderer extends Group implements Renderer {

	/**
	 * When this method is called, the Renderer should update its appearance
	 * according to the environment.
	 */
	public abstract void update();

}
