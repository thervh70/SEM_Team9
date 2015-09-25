package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;


/**
 * A screen for displaying a settings menu.
 * @author Maikel, Maarten and Mitchell
 */
@SuppressWarnings("restriction")
public final class SettingsScene extends MenuScene {

    /** Boolean for sound status. */
    private static boolean sound = true;

	/**
	 * Type of buttons that exist.
	 */
	enum BType {
		/** Back button. */
		SETTINGS_BACK,
		/** Sound toggle. */
		SETTING_SOUND
	 }

    /**
     * Return whether sound is enabled.
     * @return whether sound is enabled.
     */
    public static boolean isSoundEnabled() {
    	return sound;
    }

    /**
     * Creates a Sound toggle button and a Back button.
     * @return an array of Nodes to be added to the Scene.
     */
	@Override
	public Node[] createContent() {
	    final Button backButton = createButton("BACK", 2, 26);
	    final Button soundButton = createButton("SOUND: ON", 3, 18);
		// Override default button size from Style
	    final Font font = Font.font("Roboto", FontWeight.BOLD, 20);
	    soundButton.setFont(font);
	    setButtonFunction(backButton, BType.SETTINGS_BACK);
	    setButtonFunction(soundButton, BType.SETTING_SOUND);
        /** Set Tooltips. */
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
	    button.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(final ActionEvent event) {
	            if (type == BType.SETTINGS_BACK) {
	                GameObservable.notify(Category.MENU, Menu.SETTINGS_BACK);
	                ShaftEscape.setScene(new MainMenuScene());
	            } else {
	                sound = !sound;
	                String s;
	                if (sound) {
	                    s = "ON";
	                } else {
	                    s = "OFF";
	                }
	                button.setText("Sound: " + s);
	                GameObservable.notify(Category.MENU, Menu.SETTING_SOUND,
							s);
	            }
	        }
	    });
	}

	/** Override background, the Settings background shows "Settings". */
	@Override
	public String getBackgroundPath() {
		return "settingsBackground.png";
	}

}
