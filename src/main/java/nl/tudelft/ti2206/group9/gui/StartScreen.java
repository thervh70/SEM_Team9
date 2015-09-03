package nl.tudelft.ti2206.group9.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Created by Maikel on 02/09/2015.
 */
public class StartScreen extends Application {


    /**
     * Created by Maikel on 02/09/2015.
     */

    Stage window;

    Scene startScreen;

    Button startButton, settingsButton, exitButton;


    public void start(Stage primaryStage) throws Exception {



        window = primaryStage;

        GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        grid.setVgap(20);
        grid.setHgap(20);

        /* Add text to buttons */
        startButton = new Button("START");
        GridPane.setConstraints(startButton, 15,30);

        settingsButton = new Button("SETTINGS");
        GridPane.setConstraints(settingsButton, 10, 30);

        exitButton = new Button("EXIT");
        GridPane.setConstraints(exitButton, 20, 30);

        grid.getChildren().addAll(startButton,settingsButton,exitButton);

        startScreen = new Scene(grid, 800,800);


        window.setScene(startScreen);
        window.setTitle("Test_GUI");
        window.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}

