package nl.tudelft.ti2206.group9.gui;

import java.awt.font.ShapeGraphicAttribute;

import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * @author Maikel and Mitchell
 *
 * A screen for displaying a settings menu.
 */
@SuppressWarnings("restriction")
public final class SettingsScene extends AbstractScene {

    /** Boolean for sound status. */
    private static boolean sound = true;

	/**
	 * Type of buttons that exist.
	 */
	private enum BType {
		/** Back button. */
		SETTINGS_BACK, 
		/** Sound toggle. */
		SETTING_SOUND
	 }

    /**
     * Creating the SettingsScene.
     */
    public Parent createRoot() {
        GridPane grid = initializeGrid();

        final Button backButton = createButton("Back", 2, 26);
        final Button soundButton = createButton("Sound: ON", 5, 18);
        soundButton.setFont(Font.font("Roboto", FontWeight.BOLD, 20));

        setButtonFunction(backButton, BType.SETTINGS_BACK);
        setButtonFunction(soundButton, BType.SETTING_SOUND);

        // Adding buttons to grid.
        grid.getChildren().addAll(backButton, soundButton);
        
		return grid;
    }

    /**
     * Return whether sound is enabled.
     * @return whether sound is enabled.
     */
    public static boolean isSoundEnabled() {
    	return sound;
    }

	/**
     * This method creates the gridPane which is used for the layout.
     * @return grid that is going to be used.
     */
    private static GridPane initializeGrid() {
        final GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        grid.setVgap(20);
        grid.setHgap(20);
        Style.setBackground("sc2.jpg", grid);
        return grid;
    }

    /**
     * This method adds text to buttons and give them a location on the grid.
     * @param name Name of the button.
     * @param column Column index on the grid.
     * @param row Row index on the grid.
     * @return the created button.
     */
    private static Button createButton(final String name, final int column, 
    									final int row) {
        Button button = new Button(name);
        Style.setButtonStyle(button);
        GridPane.setConstraints(button, column, row);
		return button;
	}

    /**
     * This method sets the function of a button.
     * @param stage given PrimaryStage.
     * @param button Button to be set.
     * @param type Type of button
     */
	private static void setButtonFunction(final Button button,
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
                    GameObservable.notify(Category.MENU, Menu.SETTING_SOUND, s);
                }
            }
        });
	}

}
