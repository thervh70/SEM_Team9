package nl.tudelft.ti2206.group9.gui.scene;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.audio.AudioPlayer;
import nl.tudelft.ti2206.group9.gui.popup.WarningPopup;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

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
        SHOP,
        /** Highscores button. */
        HIGHSCORES
    }

    /** ExitButton width. */
    private static final int EXIT_BUTTON_WIDTH = 60;
    /** Width for large buttons. */
    private static final int LARGE_BUTTON_WIDTH = 130;
    /** The AudioPlayer to be used for background music. */
    private static AudioPlayer apMainMenu = new AudioPlayer("src/main/"
            + "resources/nl/tudelft/ti2206/group9/audio/intro.wav");

    /**
     * Create Start, Settings and Exit buttons.
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        apMainMenu.play(true);
        final Button startButton = createButton("START!", 2, 22);
        final Button settingsButton = createButton("SETTINGS", 0, 24);
        final Button exitButton = createButton("EXIT", 4, 24);
        final Button loadButton = createButton("ACCOUNTS", 0, 22);
        final Button shopButton = createButton("SHOP", 4, 22);
        final Button highScoreButton = createButton("HIGHSCORES", 2, 24);
        final Label playerName = getPlayerLabelContent();
        exitButton.setMaxWidth(EXIT_BUTTON_WIDTH);
        /** Set functions of buttons.*/
        setButtonFunction(exitButton, BType.EXIT);
        setButtonFunction(startButton, BType.START);
        setButtonFunction(settingsButton, BType.SETTINGS);
        setButtonFunction(loadButton, BType.LOAD);
        setButtonFunction(shopButton, BType.SHOP);
        setButtonFunction(highScoreButton, BType.HIGHSCORES);

        /** Set tooltips. */
        startButton.setTooltip(new Tooltip("Start the game!"));
        exitButton.setTooltip(new Tooltip("Are you sure?"));
        settingsButton.setTooltip(new Tooltip("Change game settings"));
        loadButton.setTooltip(new Tooltip("Load an account"));
        highScoreButton.setTooltip(new Tooltip("Check the highscores"));

        return new Node[]{startButton, settingsButton, exitButton,
                loadButton, shopButton, highScoreButton, playerName};
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    private void setButtonFunction(final Button button,
            final BType type) {
        button.setOnAction(event -> {
            SplashScene.getButtonAudioPlayer().play(false);
            if (type == BType.EXIT) {
                apMainMenu.stop();
                OBSERVABLE.notify(Category.MENU, Menu.EXIT);
                ShaftEscape.exit();
            } else if (type == BType.START) {
                apMainMenu.stop();
                if (State.getPlayerName() ==  null) {
                    setPopup(new WarningPopup(event1 -> {
                        setPopup(null);
                        ShaftEscape.setScene(new AccountScene());
                    }, "Please load or create an account first"));
                    ShaftEscape.showPopup(getPopup());
                } else {
                    ShaftEscape.setScene(new GameScene());
                }
            } else if (type == BType.LOAD) {
                OBSERVABLE.notify(Category.MENU, Menu.LOAD_MENU);
                ShaftEscape.setScene(new AccountScene());
            } else if (type == BType.SHOP) {
                OBSERVABLE.notify(Category.MENU, Menu.SHOP);
                ShaftEscape.setScene(new ShopScene());
            } else {
                OBSERVABLE.notify(Category.MENU, Menu.SETTINGS);
                ShaftEscape.setScene(new SettingsScene());
            }
        });
    }

    /** Every MainMenuScene has an AudioPlayer for the soundtrack.
     * @return the apMainMenu AudioPlayer.
     */
    public static AudioPlayer getAudioPlayer() {
        return apMainMenu;
    }
}
