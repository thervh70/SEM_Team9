package nl.tudelft.ti2206.group9.gui.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;
import nl.tudelft.ti2206.group9.ShaftEscape;

/**
 * Ancestor of all Scenes. Implements all constructors to use the default
 * width and height in ShaftEscape.
 * An abstract method {@link AbstractScene#createRoot()} should be implemented
 * by subclasses, which will contain the root Node of the scene.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public abstract class AbstractScene extends Scene {

	/** Default root, is an empty StackPane. */
	public static final StackPane DEFAULT_ROOT = new StackPane();

	/** The Popup a AbstractScene can have. */
	private static Popup popup;

	/**
	 * Scene of default {@link ShaftEscape#WIDTH} and
	 * {@link ShaftEscape#HEIGHT} is created, with an optional depthBuffer
	 * and/or sceneAntialiasing.
	 * @param depthBuffer whether the scene should have a depthBuffer.
	 * @param sceneAntialiasing see {@link SceneAntialiasing}
	 */
	public AbstractScene(final boolean depthBuffer,
			final SceneAntialiasing sceneAntialiasing) {
		super(DEFAULT_ROOT, ShaftEscape.WIDTH, ShaftEscape.HEIGHT, depthBuffer,
				sceneAntialiasing);
		setRoot(createRoot());
	}

	/**
	 * Scene of default {@link ShaftEscape#WIDTH} and
	 * {@link ShaftEscape#HEIGHT} is created, with an optional depthBuffer.
	 * @param depthBuffer whether the scene should have a depthBuffer.
	 */
	public AbstractScene(final boolean depthBuffer) {
		super(DEFAULT_ROOT, ShaftEscape.WIDTH, ShaftEscape.HEIGHT, depthBuffer);
		setRoot(createRoot());
	}

	/**
	 * Scene of default {@link ShaftEscape#WIDTH} and
	 * {@link ShaftEscape#HEIGHT} is created with pre-set fill.
	 * @param fill pre-set fill for the Scene
	 */
	public AbstractScene(final Paint fill) {
		super(DEFAULT_ROOT, ShaftEscape.WIDTH, ShaftEscape.HEIGHT, fill);
		setRoot(createRoot());
	}

	/**
	 * Default constructor, Scene of default {@link ShaftEscape#WIDTH} and
	 * {@link ShaftEscape#HEIGHT} is created.
	 */
	public AbstractScene() {
		super(DEFAULT_ROOT, ShaftEscape.WIDTH, ShaftEscape.HEIGHT);
		setRoot(createRoot());
	}

	/**
	 * Get the Popup.
	 * @return the Popup
	 */
	public static Popup getPopup() {
		return popup;
	}

	/**
	 * Set the Popup.
	 * @param newPopup the Popup to be set
	 */
	protected static void setPopup(final Popup newPopup) {
		popup = newPopup;
	}

	/**
	 * This method creates the {@link Parent} root that is used in the scene.
	 * It is called in the constructor.
	 * @return a {@link Parent} root that is used in the scene.
	 */
	public abstract Parent createRoot();

}
