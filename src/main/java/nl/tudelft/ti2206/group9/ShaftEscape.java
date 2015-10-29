package nl.tudelft.ti2206.group9;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Popup;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.scene.AbstractScene;
import nl.tudelft.ti2206.group9.gui.scene.MainMenuScene;
import nl.tudelft.ti2206.group9.gui.scene.SplashScene;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter;
import nl.tudelft.ti2206.group9.shop.CurrentItems;
import nl.tudelft.ti2206.group9.shop.ShopItemLoader;
import nl.tudelft.ti2206.group9.shop.ShopItemUnlocker;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;
import nl.tudelft.ti2206.group9.util.Logger;

import java.io.File;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

/**
 * Starting point of the Application.
 * Contains method to switch Scenes or show Popups.
 * @author Maarten
 */
public class ShaftEscape extends Application {

    /** Width of the Window. */
    public static final int WIDTH = 464; // 480 - 16
    /** Height of the Window. */
    public static final int HEIGHT = 640;
    /** The logger that logs all events in the game. */
    public static final Logger LOGGER = new Logger();

    /** Primary stage where the Scenes are shown in. */
    private static Stage stage;

    /**
     * Start the application in the SplashScene.
     * @param appStage the primary Stage for the Application. This is where
     * the scenes are shown in.
     */
    @Override
    public final void start(final Stage appStage) {
        //Loading textures
        Style.loadTextures();
        ShopItemLoader.loadShopItems();
        ShopItemUnlocker.createUnlockedShopItemsMap();

        State.resetAll();
        setStage(appStage);
        stage.setResizable(false);
        stage.setWidth(ShaftEscape.WIDTH);
        stage.setHeight(ShaftEscape.HEIGHT);
        stage.setMinWidth(ShaftEscape.WIDTH);
        stage.setMinHeight(ShaftEscape.HEIGHT);
        stage.setMaxWidth(ShaftEscape.WIDTH);
        stage.setMaxHeight(ShaftEscape.HEIGHT);

        // Make sure the game is saved on exit
        stage.setOnCloseRequest(e -> exit());

        OBSERVABLE.addObserver(LOGGER);
        createSaveDirectory();
        setScene(new SplashScene());
    }

    /** Creates the savefile directory. */
    private static void createSaveDirectory() {
        final File saveDir = new File(SaveGame.getDefaultSaveDir());

        // if the directory does not exist, create it
        if (!saveDir.exists()) {
            try {
                saveDir.mkdirs();
            } catch (SecurityException e) {
                OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
                        "ShaftEscape.createSaveDirectory()", e.getMessage());
            }
        }
    }

    /** @param newStage the new stage to set as private static field. */
    private static void setStage(final Stage newStage) {
        stage = newStage;
    }

    /**
     * Get the Scene at which the stage is currently on.
     * @return an AbstractScene
     */
    public static AbstractScene getScene() {
        return (AbstractScene) stage.getScene();
    }

    /**
     * Setting the right scene and displaying it.
     * @param newScene the new Scene that is to be showed.
     */
    public static void setScene(final AbstractScene newScene) {
        stage.setScene(newScene);
        stage.show();
    }

    /**
     * Shows a popup on the screen.
     * @param popup the Popup that is to be shown.
     */
    public static void showPopup(final Popup popup) {
        Platform.runLater(() -> {
            popup.show(stage);
            popup.setAnchorX(stage.getX() + stage.getWidth() / 2
                    - popup.getWidth() / 2);
            popup.setAnchorY(stage.getY() + stage.getHeight() / 2
                    - popup.getHeight() / 2);
        });
    }

    /** Exits the Application. */
    public static void exit() {
        createSaveDirectory();
        if (State.getPlayerName() != null) {
            SaveGame.saveGame();
        }
        LOGGER.writeToFile();
        stage.close();
        InternalTicker.stop();
        HighscoreClientAdapter.disconnect();
        MainMenuScene.getAudioPlayer().stop();
        CurrentItems.getSoundtrackPlayer().resetSpeed();
        CurrentItems.getSoundtrackPlayer().stop();
    }

    /**
     * Launch JavaFX.
     * @param args optional JavaFX arguments
     */
    public static void main(final String... args) {
        launch(args);
    }

}
