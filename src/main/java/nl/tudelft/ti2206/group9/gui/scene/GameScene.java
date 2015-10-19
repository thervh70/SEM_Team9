package nl.tudelft.ti2206.group9.gui.scene; // NOPMD - many imports are needed

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

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
import nl.tudelft.ti2206.group9.audio.SoundEffectPlayer;
import nl.tudelft.ti2206.group9.audio.SoundtrackPlayer;
import nl.tudelft.ti2206.group9.gui.ExternalTicker;
import nl.tudelft.ti2206.group9.gui.popup.DeathPopup;
import nl.tudelft.ti2206.group9.gui.popup.PausePopup;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.entity.Player;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.util.Direction;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Game;
import nl.tudelft.ti2206.group9.util.KeyMap;

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
    /** Default countdown amount after resuming the game. */
    private static final int COUNTDOWN = 3;

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
    private static SoundtrackPlayer soundtrackPlayer =
            new SoundtrackPlayer(
                    "nl/tudelft/ti2206/group9/audio/soundtrack.aiff");

    /** The Sound-effects player. */
    private static SoundEffectObserver soundEffectObserver =
            new SoundEffectObserver();
    /** The Player Death observer. */
    private static PlayerDeathObserver playerDeathObserver =
            new PlayerDeathObserver();

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

        final Group root = new Group();
        root.setDepthTest(DepthTest.ENABLE);
        root.setAutoSizeChildren(true);

        setupSubScenes(root);
        setupCamera();
        keyBindings();

        soundtrackPlayer.play();
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
        setupKeyMap();
        setOnKeyPressed(keyEvent -> {
            if (running) {
                keyMap.keyPressed(keyEvent.getCode());
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

    /** Sets up the KeyMap with the actions used in the game. */
    private void setupKeyMap() {
        final Player player = State.getTrack().getPlayer();
        keyMap.addKey(KeyCode.UP,    () -> player.move(Direction.JUMP));
        keyMap.addKey(KeyCode.DOWN,  () -> player.move(Direction.SLIDE));
        keyMap.addKey(KeyCode.LEFT,  () -> player.move(Direction.LEFT));
        keyMap.addKey(KeyCode.RIGHT, () -> player.move(Direction.RIGHT));

        keyMap.addKey(KeyCode.W,     () -> player.move(Direction.JUMP));
        keyMap.addKey(KeyCode.S,     () -> player.move(Direction.SLIDE));
        keyMap.addKey(KeyCode.A,     () -> player.move(Direction.LEFT));
        keyMap.addKey(KeyCode.D,     () -> player.move(Direction.RIGHT));

        keyMap.addKey(KeyCode.ESCAPE, () -> {
            keyMap.releaseAll();   // The popup blocks released keys propagating
            if (getPopup() == null) {   // If we have no popup already
                soundtrackPlayer.pause();
                showPauseMenu();
            }
        });
    }

    /** Start the tickers. */
    public static void startTickers() {
        extTicker = new ExternalTicker();
        extTicker.handle(System.currentTimeMillis()); // Render first frame
        extTicker.countdown(COUNTDOWN); // Countdown will call resumeTickers()
        playerDeathObserver = new PlayerDeathObserver();
        soundEffectObserver = new SoundEffectObserver();
    }

    /** Resumes the tickers. */
    public static void resumeTickers() {
        running = true;
        InternalTicker.start();
        extTicker.start();
        OBSERVABLE.addObserver(playerDeathObserver);
        OBSERVABLE.addObserver(soundEffectObserver);
        OBSERVABLE.notify(Category.GAME, Game.RESUMED);
    }

    /** Stop the tickers. */
    public static void stopTickers() {
        running = false;
        extTicker.stop();
        InternalTicker.stop();
        OBSERVABLE.deleteObserver(playerDeathObserver);
        OBSERVABLE.deleteObserver(soundEffectObserver);
        OBSERVABLE.notify(Category.GAME, Game.STOPPED);
    }

    /** Show a pause menu. */
    public static void showPauseMenu() {
        stopTickers();
        OBSERVABLE.notify(Category.GAME, Game.PAUSED);
        setPopup(new PausePopup(e -> {
            extTicker.countdown(COUNTDOWN);
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
        soundtrackPlayer.resetSpeed();
        soundtrackPlayer.stop();
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
    public static SoundtrackPlayer getSoundtrackPlayer() {
        return soundtrackPlayer;
    }

    /** @return the ExternalTicker of the GameScene. */
    public static ExternalTicker getExternalTicker() {
        return extTicker;
    }

    /** Stops the game when the Player dies. */
    private static class PlayerDeathObserver implements GameObserver {
        @Override
        public void update(final Observable o, final Object arg) {
            final GameUpdate update = (GameUpdate) arg;
            if (update.getCat().equals(Category.PLAYER)
                    && update.getSpec().equals(Player.COLLISION)
                    && update.getArgs()[0].equals("AbstractObstacle")) {
                State.checkHighscore();
                stopTickers();
                showDeathMenu();
            }
        }
    }

    /**
     * The SoundEffectsPlayer plays the SoundEffects that accompany the player's
     * movement.
     * @author Maarten and Mitchell
     */
    private static class SoundEffectObserver implements GameObserver {

        /** Constant which is used for increasing the soundtrack speed. */
        private static final double SPEED_INCREASE = Math.pow(2, 1. / 12.);

        /** The Map that decides which sound to play for Player events. */
        private final Map<Player, SoundEffectPlayer> soundMap =
                new ConcurrentHashMap<>();
        /** The Map that decides which sound to play for collisions. */
        private final Map<String, SoundEffectPlayer> soundMapCollide =
                new ConcurrentHashMap<>();

        /** State that remembers the previous distance. */
        private int prevDist;

        /** Default constructor. */
        SoundEffectObserver() {
            super();
            soundMap.put(Player.JUMP, createPlayer("jump"));
            soundMap.put(Player.SLIDE, createPlayer("slide"));
            soundMap.put(Player.START_MOVE, createPlayer("move"));

            soundMapCollide.put("AbstractObstacle", createPlayer("death"));
            soundMapCollide.put("Coin", createPlayer("coin"));
        }

        @Override
        public void update(final Observable o, final Object arg) {
            final GameUpdate update = (GameUpdate) arg;
            if (update.getCat() != Category.PLAYER) {
                return;
            }
            if (update.getSpec() == Player.DISTANCE_INCREASE) {
                final int mod = 250;
                if (State.getDistance() >= prevDist + mod) {
                    prevDist += mod;
                    new Thread(() -> {
                        GameScene.getSoundtrackPlayer().setSpeed(
                                GameScene.getSoundtrackPlayer().getSpeed()
                                * SPEED_INCREASE
                                );
                    }).start();
                }
            } else if (update.getSpec() == Player.COLLISION) {
                if (soundMapCollide.get(update.getArgs()[0]) != null) {
                    soundMapCollide.get(update.getArgs()[0]).play();
                }
            } else {
                if (soundMap.get(update.getSpec()) != null) {
                    soundMap.get(update.getSpec()).play();
                }
            }
        }

        /**
         * @param effectName the effect to create a SoundEffectPlayer for.
         * @return a new SoundEffectPlayer that plays the indicated effectName.
         */
        private SoundEffectPlayer createPlayer(final String effectName) {
            final String audioPath = "nl/tudelft/ti2206/group9/audio/";
            return new SoundEffectPlayer(audioPath + effectName + ".wav");
        }

    }

}
