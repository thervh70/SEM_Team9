package nl.tudelft.ti2206.group9.gui.scene;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.audio.SoundtrackPlayer;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;


/**
 * A Main Menu with different options/buttons like a options menu, start button
 * and exit button.
 * @author Maikel, Maarten, Mitchell and Robin
 */
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
        /** Accounts button. */
        ACCOUNT,
        /** Shop button. */
        SHOP,
        /** Highscores button. */
        HIGHSCORES
    }

    /** ExitButton width. */
    private static final int EXIT_BUTTON_WIDTH = 60;
    /** The AudioPlayer to be used for background music. */
    private static SoundtrackPlayer apMainMenu = new SoundtrackPlayer(
            "nl/tudelft/ti2206/group9/audio/intro.mp3");

    /**
     * Create Start, Settings and Exit buttons.
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        apMainMenu.play();
        final Button startButton = createButton("START!", 2, 22);
        final Button settingsButton = createButton("SETTINGS", 0, 24);
        final Button exitButton = createButton("EXIT", 4, 24);
        final Button accountsButton = createButton("ACCOUNTS", 0, 22);
        final Button shopButton = createButton("SHOP", 4, 22);
        final Button highScoreButton = createButton("HIGHSCORES", 2, 24);
        final Label playerName = getPlayerLabelContent();

        exitButton.setMaxWidth(EXIT_BUTTON_WIDTH);
        /** Set functions of buttons.*/
        setButtonFunction(exitButton, BType.EXIT);
        setButtonFunction(startButton, BType.START);
        setButtonFunction(settingsButton, BType.SETTINGS);
        setButtonFunction(accountsButton, BType.ACCOUNT);
        setButtonFunction(shopButton, BType.SHOP);
        setButtonFunction(highScoreButton, BType.HIGHSCORES);

        /** Set tooltips. */
        startButton.setTooltip(new Tooltip("Start the game!"));
        exitButton.setTooltip(new Tooltip("Are you sure?"));
        settingsButton.setTooltip(new Tooltip("Change game settings"));
        accountsButton.setTooltip(new Tooltip("Load an account"));
        highScoreButton.setTooltip(new Tooltip("Check the highscores"));

        return new Node[]{startButton, settingsButton, exitButton,
                accountsButton, shopButton, highScoreButton, playerName};
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    private void setButtonFunction(final Button button, final BType type) {
        button.setOnAction(event -> {
            playButtonSound();
            if (type == BType.EXIT) {
                apMainMenu.stop();
                OBSERVABLE.notify(Category.MENU, Menu.EXIT);
                ShaftEscape.exit();
            } else if (type == BType.START) {
                apMainMenu.stop();
                ShaftEscape.setScene(new GameScene());
            } else if (type == BType.ACCOUNT) {
                OBSERVABLE.notify(Category.MENU, Menu.LOAD_MENU);
                SaveGame.saveGame();
                ShaftEscape.setScene(new AccountScene());
            } else if (type == BType.SHOP) {
                OBSERVABLE.notify(Category.MENU, Menu.SHOP);
                ShaftEscape.setScene(new ShopScene());
            } else if (type == BType.SETTINGS) {
                    OBSERVABLE.notify(Category.MENU, Menu.SETTINGS);
                    ShaftEscape.setScene(new SettingsScene());
            } else {
                OBSERVABLE.notify(Category.MENU, Menu.HIGHSCORES);
                ShaftEscape.setScene(new HighScoreScene());
            }
        });
    }

    /** Every MainMenuScene has an AudioPlayer for the soundtrack.
     * @return the apMainMenu AudioPlayer.
     */
    public static SoundtrackPlayer getAudioPlayer() {
        return apMainMenu;
    }
}
