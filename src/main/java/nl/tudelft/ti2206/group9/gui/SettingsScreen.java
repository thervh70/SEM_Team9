package nl.tudelft.ti2206.group9.gui;

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

	/** Hide public constructor. */
	private SettingsScreen() { }
    /** Boolean for sound status. */
    public static boolean sound = true;

    /**
     * Creating and displaying the scene.
     *
     * @param primaryStage The stage to be started.
     */
    public static void start(final Stage primaryStage) {

        /**Creating a gridPane for the layout. */
        GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        grid.setVgap(20);
        grid.setHgap(20);

        /** Setting the background image */
        Style.setBackground("sc2.jpg", grid);

        /**Creating a back button. */
        Button backButton;
        backButton = new Button("Back");
        Style.setButtonStyle(backButton);
        GridPane.setConstraints(backButton, 2, 26);

        /** Creating the sound toggle. */
        final Button soundButton;
        soundButton = new Button("Sound: ON");
        if (!sound) {
            soundButton.setText("Sound: OFF");
        }

        Style.setButtonStyle(soundButton);
        soundButton.setFont(Font.font("Roboto", FontWeight.BOLD, 20));
        GridPane.setConstraints(soundButton, 5, 18);

        /** Adding buttons to grid. */
        grid.getChildren().addAll(backButton, soundButton);

        /** Assigning a function to the buttons. */
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                StartScreen.start(primaryStage);
            }
        });

        soundButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                if (sound) {
                    soundButton.setText("Sound: OFF");
                    sound = false;
                } else {
                    soundButton.setText("Sound: ON");
                    sound = true;
                }
            }
        });

        settings = new Scene(grid, GUIConstant.WIDTH, GUIConstant.HEIGHT);

        primaryStage.setScene(settings);
        primaryStage.show();
    }
}
