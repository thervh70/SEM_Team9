package nl.tudelft.ti2206.group9.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;

@SuppressWarnings("restriction")
public abstract class AbstractScene extends Scene {
	
	/** Default root, is an empty StackPane. */
	public static final StackPane DEFAULT_ROOT = new StackPane();

	/**
	 * Scene of default {@link GUIConstant#WIDTH} and 
	 * {@link GUIConstant#HEIGHT} is created, with an optional depthBuffer
	 * and/or sceneAntialiasing.
	 * @param depthBuffer whether the scene should have a depthBuffer.
	 * @param sceneAntialiasing see {@link SceneAntialiasing}
	 */
	public AbstractScene(boolean depthBuffer,
			SceneAntialiasing sceneAntialiasing) {
		super(DEFAULT_ROOT, GUIConstant.WIDTH, GUIConstant.HEIGHT, depthBuffer,
				sceneAntialiasing);
		setRoot(createRoot());
	}

	/**
	 * Scene of default {@link GUIConstant#WIDTH} and 
	 * {@link GUIConstant#HEIGHT} is created, with an optional depthBuffer.
	 * @param depthBuffer whether the scene should have a depthBuffer.
	 */
	public AbstractScene(boolean depthBuffer) {
		super(DEFAULT_ROOT, GUIConstant.WIDTH, GUIConstant.HEIGHT, depthBuffer);
		setRoot(createRoot());
	}

	/**
	 * Scene of default {@link GUIConstant#WIDTH} and 
	 * {@link GUIConstant#HEIGHT} is created with pre-set fill.
	 * @param fill pre-set fill for the Scene
	 */
	public AbstractScene(Paint fill) {
		super(DEFAULT_ROOT, GUIConstant.WIDTH, GUIConstant.HEIGHT, fill);
		setRoot(createRoot());
	}

	/**
	 * Default constructor, Scene of default {@link GUIConstant#WIDTH} and 
	 * {@link GUIConstant#HEIGHT} is created.
	 */
	public AbstractScene() {
		super(DEFAULT_ROOT, GUIConstant.WIDTH, GUIConstant.HEIGHT);
		setRoot(createRoot());
	}
	
	/**
	 * This method creates the {@link Parent} root that is used in the scene.
	 * It is called in the constructor.
	 * @return a {@link Parent} root that is used in the scene.
	 */
	abstract Parent createRoot();

}
