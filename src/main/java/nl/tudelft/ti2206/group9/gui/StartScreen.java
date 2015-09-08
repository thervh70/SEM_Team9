package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Created by Maikel on 02/09/2015.
 */
public class StartScreen {


    /**
     * Created by Maikel on 02/09/2015.
     */

    public static Stage window;

    public static Scene startScreen;

    public static Button startButton, settingsButton, exitButton;




    public static void start(final Stage primaryStage) {



        window = primaryStage;

        GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        grid.setVgap(20);
        grid.setHgap(20);

       // grid.getChildren().add(Style.setBackground("/View/Resources/background_splashscreen.png"));

        /* Add text to buttons */
        startButton = new Button("START");
        GridPane.setConstraints(startButton, 10, 18);

        settingsButton = new Button("SETTINGS");
        GridPane.setConstraints(settingsButton, 6, 18);

        exitButton = new Button("EXIT");
        GridPane.setConstraints(exitButton, 14, 18);

        grid.getChildren().addAll(startButton, settingsButton, exitButton);

        startScreen = new Scene(grid, 640,480);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });


        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                SettingsScreen.start(primaryStage);
            }
        });

        window.setScene(startScreen);
        window.show();

    }


}

