package nl.tudelft.ti2206.group9.gui;

/**
 * @author Robin, Maarten
 */

import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Popup;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.KeyMap;

@SuppressWarnings("restriction")
public final class GameScreen {

	private static final Translate CAMERA_TRANS = new Translate(0, -5, -12);
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
	private static ExternalTicker extTicker;
	private static boolean running;
	private static Stage primaryStage;

	private static Popup pause;
	private static Popup death;
	
	/** Hide public constructor. */
	private GameScreen() { }

	/** Start the Application. */
	public static void start(Stage stage) {
		State.reset();

		primaryStage = stage;

		root = new Group();
		root.setDepthTest(DepthTest.ENABLE);
		root.setAutoSizeChildren(true);

		scene = new Scene(root, GUIConstant.WIDTH, GUIConstant.HEIGHT, true);
		scene.setFill(Color.AQUA);
		primaryStage.setScene(scene);

		world = new Group();
		overlay = new Group();
		worldScene = new SubScene(world, GUIConstant.WIDTH, GUIConstant.HEIGHT, 
				true, SceneAntialiasing.BALANCED);
		overlayScene = new SubScene(overlay, GUIConstant.WIDTH,
				GUIConstant.HEIGHT);
		overlayScene.setFill(Color.TRANSPARENT);
		root.getChildren().add(worldScene);
		root.getChildren().add(overlayScene);

		setupCamera();
		keyBindings(primaryStage);
		primaryStage.setResizable(false);
		primaryStage.show();

		startTickers();
	}

	/**
	 * Create and setup camera, adding it to worldScene.
	 */
	private static void setupCamera() {
		final PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.getTransforms().addAll(CAMERA_TRANS, CAMERA_ROT);
		camera.setNearClip(CAMERA_NEAR);
		camera.setFarClip(CAMERA_FAR);
		worldScene.setCamera(camera);
	}

	/**
	 * Make sure KeyEvents are handled in {@link KeyMap}.
	 */
	private static void keyBindings(final Stage primaryStage) {
		KeyMap.defaultKeys();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (running && keyEvent.getCode().equals(KeyCode.ESCAPE)) {
					showPauseMenu(primaryStage);
				} else if (running) {
					keyMap.keyPressed(keyEvent.getCode());
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (running) {
					keyMap.keyReleased(keyEvent.getCode());
				}
			}
		});

		scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (running) {
					keyMap.keyTyped(keyEvent.getCode());
				}
			}
		});
	}
	
	/** Start the tickers. */
	public static void startTickers() {
		extTicker = new ExternalTicker();
		extTicker.start();
		InternalTicker.start();
		running = true;
	}

	/** Resumes the tickers. */
	public static void resumeTickers() {
		extTicker.start();
		InternalTicker.start();
		running = true;
	}
	
	/** Stop the tickers. */
	public static void stopTickers() {
		running = false;
		extTicker.stop();
		InternalTicker.stop();
	}

	/**
	 * Show a pause menu.
	 * @param primaryStage main stage of the game.
	 */
	public static void showPauseMenu(final Stage primaryStage) {
		stopTickers();

		EventHandler<MouseEvent> menu = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				State.reset();
				StartScreen.start(primaryStage);
				pause = null;
			}
		};

		EventHandler<MouseEvent> resume = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				resumeTickers();
				pause = null;
			}
		};

		pause = PopupMenu.makeMenu("Paused", "Resume",
				"Return to Main Menu", resume, menu);
		pause.show(primaryStage);
	}

	/**
	 * Show a death menu.
	 */
	public static void showDeathMenu() {
		EventHandler<MouseEvent> menu = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				State.reset();
				StartScreen.start(primaryStage);
				death = null;
			}
		};

		EventHandler<MouseEvent> retry = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				State.reset();
				GameScreen.start(primaryStage);
				death = null;
			}
		};

		death = PopupMenu.makeFinalMenu("Game Ended", State.getScore(),
			State.getCoins(), "Try again", "Return to Main Menu", retry, menu);
		death.show(primaryStage);
	}

	/**
	 * Adds node to the world.
	 * @return true (as specified by Collections.add)
	 */
	public static boolean addWorld(final Node node) {
		return world.getChildren().add(node);
	}

	/**
	 * Clears the world.
	 */
	public static void clearWorld() {
		world.getChildren().clear();
	}

	/**
	 * Adds node to the overlay.
	 * @return true (as specified by Collections.add)
	 */
	public static boolean addOverlay(final Node node) {
		return overlay.getChildren().add(node);
	}

	/**
	 * Clears the overlay.
	 */
	public static void clearOverlay() {
		overlay.getChildren().clear();
	}
	
	/** @return current Popup. Is null if no Popup is present. */
	static Popup getPopup() {
		if (pause != null) {
			return pause;
		} else {
			return death;
		}
	}
}
