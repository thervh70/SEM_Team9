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
        /** Sound Effects toggle. */
        SETTING_SOUNDEFFECTS,
        /** Soundtrack toggle. */
        SETTING_SOUNDTRACK
     }

    /** Toggle width. */
    private static final int TOGGLE_WIDTH = 270;
    /** Toggle text size. */
    private static final int TOGGLE_TEXT_SIZE = 16;
    /** Initially the toggles are set to off. */
    private static final String INIT_TOGGLE = "OFF";

    /**
     * Creates a Sound toggle button and a Back button.
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        final Button backButton = createButton("BACK", 5, 24);

        String soundtrToggle = INIT_TOGGLE;
        if (State.isSoundtrackEnabled()) {
            soundtrToggle = "ON";
        }
        final Button soundtrButton = createButton("Soundtrack: "
        + soundtrToggle, 5, 14);
        soundtrButton.setFont(Style.getFont(TOGGLE_TEXT_SIZE));
        soundtrButton.setPrefWidth(TOGGLE_WIDTH);
        String soundEfToggle = INIT_TOGGLE;
        if (State.isSoundEffectsEnabled()) {
            soundEfToggle = "ON";
        }
        final Button soundEfButton = createButton("Sound effects: "
        + soundEfToggle, 5, 17);
        soundEfButton.setFont(Style.getFont(TOGGLE_TEXT_SIZE));
        soundEfButton.setPrefWidth(TOGGLE_WIDTH);

        setButtonFunction(backButton, BType.SETTINGS_BACK);
        setButtonFunction(soundtrButton, BType.SETTING_SOUNDTRACK);
        setButtonFunction(soundEfButton, BType.SETTING_SOUNDEFFECTS);

        // Set Tooltips.
        soundtrButton.setTooltip(new Tooltip("Enable/disable soundtrack"));
        soundEfButton.setTooltip(new Tooltip("Enable/disable sound effects"));
        backButton.setTooltip(new Tooltip("Back to main menu"));
        return new Node[]{backButton, soundtrButton, soundEfButton};
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
            String s;
            if (type == BType.SETTINGS_BACK) {
                OBSERVABLE.notify(Category.MENU, Menu.SETTINGS_BACK);
                ShaftEscape.setScene(new MainMenuScene());
                } else if (type == BType.SETTING_SOUNDTRACK) {
                MainMenuScene.getAudioPlayer().stop();
                State.setSoundtrackEnabled(!State.isSoundtrackEnabled());
                if (State.isSoundtrackEnabled()) {
                    s = "ON";
                } else {
                    s = INIT_TOGGLE;
                }
                button.setText("Soundtrack: " + s);
                OBSERVABLE.notify(Category.MENU, Menu.SETTING_SOUNDTRACK, s);
            } else {
                State.setSoundEffectsEnabled(!State.isSoundEffectsEnabled());
                if (State.isSoundEffectsEnabled()) {
                    s = "ON";
                } else {
                    s = INIT_TOGGLE;
                }
                button.setText("Sound effects: " + s);
                OBSERVABLE.notify(Category.MENU, Menu.SETTING_SOUNDEFFECTS, s);
            }
        });
    }


    /** Override background, the Settings background shows "Settings". */
    @Override
    public String getBackgroundPath() {
        return "settingsBackground.png";
    }

}
