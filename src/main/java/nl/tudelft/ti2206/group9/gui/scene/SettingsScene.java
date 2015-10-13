package nl.tudelft.ti2206.group9.gui.scene;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

/**
 * A screen for displaying a settings menu.
 * @author Maikel, Maarten and Mitchell
 */
@SuppressWarnings("restriction")
public final class SettingsScene extends AbstractMenuScene {

    /**
     * Type of buttons that exist.
     */
    enum BType {
        /** Back button. */
        SETTINGS_BACK,
        /** Sound toggle. */
        SETTING_SOUND
     }

    /** Toggle width. */
    private static final int TOGGLE_WIDTH = 150;
    /** Toggle text size. */
    private static final int TOGGLE_TEXT_SIZE = 16;
    /**
     * Creates a Sound toggle button and a Back button.
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        final Button backButton = createButton("BACK", 0, 25);

        String soundToggle = "OFF";
        if (State.isSoundEnabled()) {
            soundToggle = "ON";
        }
        final Button soundButton = createButton("Sound: " + soundToggle, 2, 18);
        soundButton.setFont(Style.getFont(TOGGLE_TEXT_SIZE));
        soundButton.setPrefWidth(TOGGLE_WIDTH);

        setButtonFunction(backButton, BType.SETTINGS_BACK);
        setButtonFunction(soundButton, BType.SETTING_SOUND);

        // Set Tooltips.
        soundButton.setTooltip(new Tooltip("Enable/disable sound"));
        backButton.setTooltip(new Tooltip("Back to main menu"));
        return new Node[]{backButton, soundButton};
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    protected static void setButtonFunction(final Button button,
            final BType type) {
        button.setOnAction(event -> {
            SplashScene.getButtonAudioPlayer().play();
            if (type == BType.SETTINGS_BACK) {
                OBSERVABLE.notify(Category.MENU, Menu.SETTINGS_BACK);
                ShaftEscape.setScene(new MainMenuScene());
            } else {
                MainMenuScene.getAudioPlayer().stop();
                State.setSoundEnabled(!State.isSoundEnabled());
                String s;
                if (State.isSoundEnabled()) {
                    s = "ON";
                } else {
                    s = "OFF";
                }
                button.setText("Sound: " + s);
                OBSERVABLE.notify(Category.MENU, Menu.SETTING_SOUND, s);
            }
        });
    }


    /** Override background, the Settings background shows "Settings". */
    @Override
    public String getBackgroundPath() {
        return "settingsBackground.png";
    }

}
