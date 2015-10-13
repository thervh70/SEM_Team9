package nl.tudelft.ti2206.group9.gui.scene;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.audio.SoundtrackPlayer;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.popup.WarningPopup;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

/**
 * A Main Menu with different options/buttons like a options menu, start button
 * and exit button.
 * @author Maikel, Maarten, Mitchell and Robin
 */
@SuppressWarnings("restriction")
public final class MainMenuScene extends AbstractMenuScene {

    /**
     * Type of buttons that exist.
     */
    private enum BType {
        /** Exit button. */
        EXIT,
        /** Start button. */
        START,
        /** Settings button. */
        SETTINGS,
        /** Load Game button. */
        LOAD,
        /** Shop button. */
        SHOP
    }

    /** The input field for the name of the player. */
    static final TextField INPUT = createTextField("PLAYER NAME", 2, 22);
    /** ExitButton width. */
    private static final int EXIT_BUTTON_WIDTH = 60;
    /** Font size for input. */
    private static final int FONT_SIZE = 12;
    /** The AudioPlayer to be used for background music. */
    private static SoundtrackPlayer apMainMenu = new SoundtrackPlayer(
    		"src/main/resources/nl/tudelft/ti2206/group9/audio/intro.wav");

    /**
     * Create Start, Settings and Exit buttons.
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
    	apMainMenu.play();
        final Button startButton = createButton("START!", 4, 22);
        startButton.disableProperty().bind(
                Bindings.isEmpty(INPUT.textProperty()));
        final Button settingsButton = createButton("SETTINGS", 0, 24);
        final Button exitButton = createButton("EXIT", 0, 0);
        final Button loadButton = createButton("LOAD GAME", 2, 24);
        final Button shopButton = createButton("SHOP", 4, 24);
        final Label nameLabel = createLabel("NEW PLAYER:", 0, 22);
        exitButton.setMaxWidth(EXIT_BUTTON_WIDTH);

        /** Set functions of buttons.*/
        setButtonFunction(exitButton, BType.EXIT);
        setButtonFunction(startButton, BType.START);
        setButtonFunction(settingsButton, BType.SETTINGS);
        setButtonFunction(loadButton, BType.LOAD);
        setButtonFunction(shopButton, BType.SHOP);

        /** Set tooltips. */
        startButton.setTooltip(new Tooltip("Start the game!"));
        exitButton.setTooltip(new Tooltip("Are you sure?"));
        settingsButton.setTooltip(new Tooltip("Change game settings"));
        loadButton.setTooltip(new Tooltip("Continue a game"));
        INPUT.setTooltip(new Tooltip("Enter your name"));
        INPUT.setFont(Style.getFont(FONT_SIZE));

        return new Node[]{startButton, settingsButton, exitButton,
                loadButton, nameLabel, INPUT, shopButton};
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    private void setButtonFunction(final Button button,
            final BType type) {
        button.setOnAction(event -> {
            SplashScene.getButtonAudioPlayer().play();
            if (type == BType.EXIT) {
                apMainMenu.stop();
                OBSERVABLE.notify(Category.MENU, Menu.EXIT);
                ShaftEscape.exit();
            } else if (type == BType.START) {
                apMainMenu.stop();
                if (checkPlayerName(INPUT.getText())) {
                    createNewGame();
                } else {
                    setPopup(new WarningPopup(
                            event1 -> setPopup(null),
                            "The given name is invalid."));
                    ShaftEscape.showPopup(getPopup());
                }
            } else if (type == BType.LOAD) {
                OBSERVABLE.notify(Category.MENU, Menu.LOAD_MENU);
                ShaftEscape.setScene(new LoadGameScene());
            } else if (type == BType.SHOP) {
                OBSERVABLE.notify(Category.MENU, Menu.SHOP);
                ShaftEscape.setScene(new ShopScene());
            } else {
                OBSERVABLE.notify(Category.MENU, Menu.SETTINGS);
                ShaftEscape.setScene(new SettingsScene());
            }
        });
    }

    /**
     * Checks whether the playername is a valid name.
     * Invalid options:
     * <ul>
     *     <li>Empty name</li>
     *     <li>Name contains a '.'</li>
     *     <li>Name contains a '/'</li>
     *     <li>Name contains a '\'</li>
     * </ul>
     * @param name the name to be checked
     * @return boolean to indicate whether the name is valid
     */
    private boolean checkPlayerName(final String name) {
        return !(name.contains(".") || name.contains("/")
                || name.contains("\\"));
    }

    /**
     * Check whether the given input corresponds to
     * an already existing savefile. If so, load
     * that file, otherwise create a new game with that name.
     */
    private static void createNewGame() {
        if (State.getPlayerName() != null) {
            SaveGame.saveGame();
        }
        final boolean load = tryLoadPlayerName(INPUT.getText());
        if (!load) {
            State.resetAll();
            State.setPlayerName(INPUT.getText());
        }
        INPUT.clear();
        State.getSaveGames().clear();
        OBSERVABLE.notify(Category.MENU, Menu.START);
        ShaftEscape.setScene(new GameScene());
    }

    /**
     * Check if the given name corresponds with an already existing
     * file. If so, return true, otherwise, return false.
     * @param name the given name to check against the savefiles
     * @return boolean to indicate whether a savfile was found
     */
    private static boolean tryLoadPlayerName(final String name) {

        SaveGame.readPlayerNames();
        if (State.getSaveGames().contains(name)) {
            SaveGame.loadGame(name);
            return true;
        }
        return false;
    }

    /** Every MainMenuScene has an AudioPlayer for the soundtrack.
     * @return the apMainMenu AudioPlayer.
     */
    public static SoundtrackPlayer getAudioPlayer() {
        return apMainMenu;
    }
}
