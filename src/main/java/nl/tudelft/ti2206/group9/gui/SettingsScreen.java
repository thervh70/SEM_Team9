package nl.tudelft.ti2206.group9.gui;

import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Created by Maikel on 08/09/2015.
 *
 * A screen for displaying a settings menu.
 */
@SuppressWarnings("restriction")
public final class SettingsScreen {

    /** The scene. */
    private static Scene settings;
    /** Boolean for sound status. */
    private static boolean sound = true;
    
	/** Hide public constructor. */
	private SettingsScreen() { }
	
	/**
	 * Type of buttons that exist.
	 */
	private enum BType {
		SETTINGS_BACK, SETTING_SOUND
	 }

    /**
     * Creating and displaying the scene.
     *
     * @param primaryStage The stage to be started.
     */
    public static void start(final Stage primaryStage) {
        GridPane grid = initializeGrid();

        final Button backButton = createButton("Back", 2, 26);
        final Button soundButton = createButton("Sound: ON", 5, 18);
        soundButton.setFont(Font.font("Roboto", FontWeight.BOLD, 20));

        /** Adding buttons to grid. */
        grid.getChildren().addAll(backButton, soundButton);

        setButtonFunction(primaryStage, backButton, BType.SETTINGS_BACK);
        setButtonFunction(primaryStage, soundButton, BType.SETTING_SOUND);

        settings = new Scene(grid, GUIConstant.WIDTH, GUIConstant.HEIGHT);
        primaryStage.setScene(settings);
        primaryStage.show();
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
     * @param colum Colum index on the grid.
     * @param row Row index on the grid.
     * @return the created button.
     */
    private static Button createButton(String name, int colum, int row) {
        Button button = new Button(name);
        Style.setButtonStyle(button);
        GridPane.setConstraints(button, colum, row);
		return button;
	}
    
    /**
     * This method sets the function of a button.
     * @param stage given PrimaryStage.
     * @param button Button to be set.
     * @param type Type of button
     */
	private static void setButtonFunction(final Stage stage, 
									final Button button, final BType type) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                if (type == BType.SETTINGS_BACK) { 
                    GameObservable.notify(Category.MENU, Menu.SETTINGS_BACK);
                    StartScreen.start(stage);
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
