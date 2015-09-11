package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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

    /**
     * Creating and displaying the scene.
     *
     * @param primaryStage The stage to be started.
     */
    public static void start(final Stage primaryStage) {


        //Creating a gridPane for the layout.
        GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        grid.setVgap(20);
        grid.setHgap(20);

        //Creating a back button.
        Button backButton;
        backButton = new Button("Back");
        GridPane.setConstraints(backButton, 3, 20);

        Label label = new Label("Hier komen settings lijstjes ofzo?");
        GridPane.setConstraints(label, 10, 10);

        grid.getChildren().addAll(backButton, label);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                StartScreen.start(primaryStage);
            }
        });

        settings = new Scene(grid, GUIConstant.WIDTH, GUIConstant.HEIGHT);

        primaryStage.setScene(settings);
        primaryStage.show();
    }
}
