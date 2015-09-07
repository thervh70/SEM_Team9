package nl.tudelft.ti2206.group9.gui;

/**
 * Created by Robin.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.Track;
import nl.tudelft.ti2206.group9.util.KeyMap;

@SuppressWarnings("restriction")
public class GameWindow extends Application {

	/** Width of the Window. */
	public static final int WIDTH = 640;
	/** Height of the Window. */
	public static final int HEIGHT = 480;

	private static Group root;
	private static KeyMap keyMap = new KeyMap();
    
	@Override
	public void start(Stage primaryStage) {
		
		root = new Group();
		root.setDepthTest(DepthTest.ENABLE);
		root.setAutoSizeChildren(true);
		
		Scene scene = new Scene(root, WIDTH, HEIGHT, true);
		primaryStage.setScene(scene);

		// Create and position camera
		final PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.getTransforms().addAll(
				new Translate(0, -3, -12),
				new Rotate(-10, Rotate.X_AXIS)//,
//				new Rotate(0.01, Rotate.Z_AXIS) // else it bugs, for some reason
				);
		camera.setNearClip(0.1);
		camera.setFarClip(Track.LENGTH * Track.LENGTH);

		scene.setCamera(camera);

		KeyMap.defaultKeys();
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				KeyCode code = keyEvent.getCode();
				System.out.println("Key Pressed: " + code.toString());
				keyMap.keyPressed(code);
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				KeyCode code = keyEvent.getCode();
				System.out.println("Key Pressed: " + code.toString());
				keyMap.keyReleased(code);
			}
		});
		scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				KeyCode code = keyEvent.getCode();
				System.out.println("Key Pressed: " + code.toString());
				keyMap.keyTyped(code);
			}
		});
		
		primaryStage.setResizable(false);
		primaryStage.show();

		new ExternalTicker().start();
		InternalTicker.start();
	}

	/**
	 * @return the root Group
	 */
	public static Group getRoot() {
		return root;
	}
	
	/**
	 * @param args does nothing.
	 * @throws InterruptedException 
	 */
	public static void main(String... args) {
		State.resetAll();
//		Main.addKeys();
//		window.addKeyListener(new KeyMap());
		GameWindow.launch(args);
	}

}
