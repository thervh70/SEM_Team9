package nl.tudelft.ti2206.group9.gui;

/**
 * @author Robin, Maarten
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.KeyMap;

@SuppressWarnings("restriction")
public class GameWindow extends Application {

	/** Width of the Window. */
	public static final int WIDTH = 640;
	/** Height of the Window. */
	public static final int HEIGHT = 480;

	private static final Translate CAMERA_TRANS = new Translate(0, -3, -12);
	private static final Rotate CAMERA_ROT = new Rotate(-10, Rotate.X_AXIS);
	private static final double CAMERA_NEAR = 0.1;
	private static final double CAMERA_FAR = 1000;

	private static KeyMap keyMap = new KeyMap();
	private static Group root;
	private static Group world;
	private static Group overlay;
	private static Scene scene;
	private static SubScene worldScene;
	private static SubScene overlayScene;
    
	@Override
	public void start(Stage primaryStage) {

		root = new Group();
		root.setDepthTest(DepthTest.ENABLE);
		root.setAutoSizeChildren(true);
		
		scene = new Scene(root, WIDTH, HEIGHT, true);
		scene.setFill(Color.AQUA);
		primaryStage.setScene(scene);

		world = new Group();
		overlay = new Group();
		worldScene = new SubScene(world, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
		overlayScene = new SubScene(overlay, WIDTH, HEIGHT);
		overlayScene.setFill(Color.TRANSPARENT);
		root.getChildren().add(worldScene);
		root.getChildren().add(overlayScene);

		Integer coins = State.getScore();
		Text test = new Text(0, 20, "Coins: " + coins.toString());
		Button pause = new Button("||");
		pause.setLayoutX(WIDTH - 30);
		pause.setLayoutY(10);

		overlay.getChildren().addAll(test, pause);

		// Create and position camera
		final PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.getTransforms().addAll(CAMERA_TRANS, CAMERA_ROT);
		camera.setNearClip(CAMERA_NEAR);
		camera.setFarClip(CAMERA_FAR);
		worldScene.setCamera(camera);
		
		keyBindings();
		primaryStage.setResizable(false);
		primaryStage.show();

		new ExternalTicker().start();
		InternalTicker.start();

		pause.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				InternalTicker.stop();
			}
		});
	}

	private void keyBindings() {
		KeyMap.defaultKeys();
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				keyMap.keyPressed(keyEvent.getCode());
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				keyMap.keyReleased(keyEvent.getCode());
			}
		});
		
		scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				keyMap.keyTyped(keyEvent.getCode());
			}
		});
	}

	/**
	 * @return the world Group
	 */
	public static Group getWorld() {
		return world;
	}

	/**
	 * @return the overlay Group
	 */
	public static Group getOverlay() {
		return overlay;
	}
	
	/**
	 * @param args does nothing.
	 * @throws InterruptedException 
	 */
	public static void main(String... args) {
		State.resetAll();
		GameWindow.launch(args);
	}

}
