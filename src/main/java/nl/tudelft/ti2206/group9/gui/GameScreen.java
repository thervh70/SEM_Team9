package nl.tudelft.ti2206.group9.gui;

/**
 * @author Robin, Maarten
 */

import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;

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
import nl.tudelft.ti2206.group9.audio.AudioPlayer;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.KeyMap;

/**
 * @author Robin, Maarten
 */
@SuppressWarnings("restriction")
public final class GameScreen {

	/** The translation of the camera. */
	public static final Translate CAMERA_TRANS = new Translate(0, -5, -12);
	/** The rotation of the camera. */
	private static final Rotate CAMERA_ROT = new Rotate(-10, Rotate.X_AXIS);
	/** The near end of the camera. */
	private static final double CAMERA_NEAR = 0.1;
	/** The far end of the camera. */
	private static final double CAMERA_FAR = 1000;

	/** The KeyMap to be used. */
	private static KeyMap keyMap = new KeyMap();
	/** The root. */
	private static Group root;
	/** The world. */
	private static Group world;
	/** The overlay. */
	private static Group overlay;
	/** The scene. */
	private static Scene scene;
	/** The worldscene. */
	private static SubScene worldScene;
	/** The overlayscene. */
	private static SubScene overlayScene;

	/** The ExternalTicker to be used. */
	private static ExternalTicker extTicker;
	/** Indicate whether the game is running. */
	private static boolean running;
	/** The primarystage. */
	private static Stage primaryStage;
	
	private static Mixer mixer;
	private static Clip clip;

	private static Popup pause;
	private static Popup death;
	
	/** Hide public constructor. */
	private GameScreen() { }

	/** Start the Application.
	 * @param stage stage
	 */
	public static void start(final Stage stage) {
		State.reset();
		
		AudioPlayer ap = new AudioPlayer(mixer, clip);
		ap.initialiseTune("sounds/soundtrack.aiff");
		ap.play();

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
	 * @param primeStage the primaryStage
	 */
	private static void keyBindings(final Stage primeStage) {
		KeyMap.defaultKeys();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (running && keyEvent.getCode()
						.equals(KeyCode.ESCAPE)) {
					showPauseMenu(primeStage);
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
	 * @param primeStage main stage of the game.
	 */
	public static void showPauseMenu(final Stage primeStage) {
		stopTickers();

		EventHandler<MouseEvent> menu = new EventHandler<MouseEvent>() {

			public void handle(final MouseEvent e) {
				State.reset();
				StartScreen.start(primeStage);
				pause = null;
			}
		};

		EventHandler<MouseEvent> resume
				= new EventHandler<MouseEvent>() {

			public void handle(final MouseEvent e) {
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

			public void handle(final MouseEvent e) {
				State.reset();
				StartScreen.start(primaryStage);
				death = null;
			}
		};

		EventHandler<MouseEvent> retry
				= new EventHandler<MouseEvent>() {

			public void handle(final MouseEvent e) {
				State.reset();
				GameScreen.start(primaryStage);
				death = null;
			}
		};

		death = PopupMenu.makeFinalMenu("Game Ended", (int) State.getScore(),
			State.getCoins(), "Try again", "Return to Main Menu", retry, menu);
		death.show(primaryStage);
	}

	/**
	 * Adds node to the world.
	 * @param node the Node
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
	 * @param node the Node
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
