package nl.tudelft.ti2206.group9.gui;

import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.audio.AudioPlayer;
import nl.tudelft.ti2206.group9.audio.SoundEffectsPlayer;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Game;
import nl.tudelft.ti2206.group9.util.KeyMap;
import nl.tudelft.ti2206.group9.util.SaveGame;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

/**
 * This scene shows the 3D Game world and the 2D score overlay.
 * @author Robin, Maarten
 */
@SuppressWarnings("restriction")
public final class GameScene extends AbstractScene {

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
	/** The world. */
	private static Group world;
	/** The overlay. */
	private static Group overlay;
	/** The worldscene. */
	private static SubScene worldScene;
	/** The overlayscene. */
	private static SubScene overlayScene;

	/** The ExternalTicker to be used. */
	private static ExternalTicker extTicker;
	/** Indicate whether the game is running. */
	private static boolean running;

	/** The AudioPlayer to be used for background music. */
	private static AudioPlayer audioPlayer = new AudioPlayer("src/main/"
			+ "resources/nl/tudelft/ti2206/group9/audio/soundtrack.aiff");

	/** The Sound-effects player. */
	private static SoundEffectsPlayer soundEffectsPlayer =
			new SoundEffectsPlayer();

	/**
	 * Default constructor, Scene of default {@link ShaftEscape#WIDTH} and
	 * {@link ShaftEscape#HEIGHT} is created.
	 */
	public GameScene() {
		super(true);
		setFill(Color.BLACK);
	}

	/**
	 * Creating the GameScene.
	 * @return The root Node for this Scene.
	 */
	public Parent createRoot() {
		State.reset();
		Style.loadTextures();

		final Group root = new Group();
		root.setDepthTest(DepthTest.ENABLE);
		root.setAutoSizeChildren(true);

		setupSubScenes(root);
		setupCamera();
		keyBindings();

		audioPlayer.play(false);
		startTickers();
		return root;
	}

	/**
	 * In this method, the SubScenes for the world and overlay are created.
	 * @param root the Group to which the SubScenes are added to.
	 */
	private static void setupSubScenes(final Group root) {
		world = new Group();
		overlay = new Group();
		worldScene = new SubScene(world, ShaftEscape.WIDTH,
                ShaftEscape.HEIGHT, true, SceneAntialiasing.BALANCED);
		overlayScene = new SubScene(overlay, ShaftEscape.WIDTH,
				ShaftEscape.HEIGHT);
		overlayScene.setFill(Color.TRANSPARENT);
		root.getChildren().add(worldScene);
		root.getChildren().add(overlayScene);
	}

	/** Create and setup camera, adding it to worldScene. */
	private static void setupCamera() {
		final PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.getTransforms().addAll(CAMERA_TRANS, CAMERA_ROT);
		camera.setNearClip(CAMERA_NEAR);
		camera.setFarClip(CAMERA_FAR);
		worldScene.setCamera(camera);
	}

	/** Make sure KeyEvents are handled in {@link KeyMap}. */
	private void keyBindings() {
		KeyMap.defaultKeys();
		setOnKeyPressed(keyEvent -> {
			if (running) {
				keyMap.keyPressed(keyEvent.getCode());
				if (keyEvent.getCode().equals(KeyCode.ESCAPE)
						&& getPopup() == null) {
					showPauseMenu();
				}
			}
		});
		setOnKeyReleased(keyEvent -> {
			if (running) {
				keyMap.keyReleased(keyEvent.getCode());
			}
		});
		setOnKeyTyped(keyEvent -> {
			if (running) {
				keyMap.keyTyped(keyEvent.getCode());
			}
		});
	}

	/** Start the tickers. */
	public static void startTickers() {
		final int countdown = 3;
		extTicker = new ExternalTicker();
		extTicker.start();
		extTicker.countdown(countdown);
		OBSERVABLE.addObserver(soundEffectsPlayer);
		OBSERVABLE.notify(Category.GAME, Game.STARTED);
	}

	/** Resumes the tickers. */
	public static void resumeTickers() {
		final int countdown = 3;
		extTicker.start();
		extTicker.countdown(countdown);
		OBSERVABLE.addObserver(soundEffectsPlayer);
		OBSERVABLE.notify(Category.GAME, Game.RESUMED);
	}

	/** Stop the tickers. */
	public static void stopTickers() {
		running = false;
		extTicker.stop();
		InternalTicker.stop();
		OBSERVABLE.deleteObserver(soundEffectsPlayer);
		OBSERVABLE.notify(Category.GAME, Game.STOPPED);
	}

	/** Show a pause menu. */
	public static void showPauseMenu() {
		stopTickers();
		OBSERVABLE.notify(Category.GAME, Game.PAUSED);
		setPopup(new PausePopup(e -> {
            resumeTickers();
            setPopup(null);
        }, e -> {
            OBSERVABLE.notify(Category.GAME, Game.TO_MAIN_MENU);
			SaveGame.saveGame();
            State.reset();
            ShaftEscape.setScene(new MainMenuScene());
            setPopup(null);
        }));
		ShaftEscape.showPopup(getPopup());
	}

	/** Show a death menu. */
	public static void showDeathMenu() {
		audioPlayer.stop();
		setPopup(new DeathPopup(e -> {
            OBSERVABLE.notify(Category.GAME, Game.RETRY);
            State.reset();
            ShaftEscape.setScene(new GameScene());
            setPopup(null);
        }, e -> {
            OBSERVABLE.notify(Category.GAME, Game.TO_MAIN_MENU);
			SaveGame.saveGame();
            State.reset();
            ShaftEscape.setScene(new MainMenuScene());
            setPopup(null);
        }));
		ShaftEscape.showPopup(getPopup());
	}

	/**
	 * Adds node to the world.
	 * @param node the Node
	 * @return true (as specified by Collections.add)
	 */
	public static boolean addWorld(final Node... node) {
		return world.getChildren().addAll(node);
	}

	/** Clears the world. */
	public static void clearWorld() {
		world.getChildren().clear();
	}

	/**
	 * Adds node to the overlay.
	 * @param node the Node
	 * @return true (as specified by Collections.add)
	 */
	public static boolean addOverlay(final Node... node) {
		return overlay.getChildren().addAll(node);
	}

	/** Clears the overlay. */
	public static void clearOverlay() {
		overlay.getChildren().clear();
	}

	/**
	 * Set the running attribute of this class.
	 * @param b New value for running.
	 */
	public static void setRunning(final boolean b) {
		running = b;
	}

	/**
	 * Every GameScene has an AudioPlayer for the soundtrack.
	 * @return the soundtrack AudioPlayer.
	 */
	public static AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	/** @return the ExternalTicker of the GameScene. */
	public static ExternalTicker getExternalTicker() {
		return extTicker;
	}

}
