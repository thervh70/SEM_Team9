package nl.tudelft.ti2206.group9.gui;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Created by Maikel on 08/09/2015.
 *
 * A splashcreen that show a "Press any key to continue", also starts the entire application.
 */
public class SplashScreen extends Application {

    /**
     * Creating and  displaying the scene.
     * @param primaryStage The stage to be started.
     */
    public void start(final Stage primaryStage) {


        /**Creating a new stackpane and scene. */
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 640, 480);

        /**Creating a new label for displaying text. */
        Label text = new Label("Press any key to continue");


        /**
         * Defining what has happens in case of a mouseClickEvent.
         */
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                StartScreen.start(primaryStage);
            }
        });

        /**
         * Defining what happens in case of a random keyPressedEvent.
         */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent ke) {
                StartScreen.start(primaryStage);
            }
        });
        /**Add the text to the canvas and give it a fade in/ fade out effect.*/
        root.getChildren().add(text);
        root.setAlignment(Pos.CENTER);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), text);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1500);
        ft.setAutoReverse(true);
        ft.play();

        /**Setting the right scene and displaying it. */
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method to launch the application.
     * @param args -
     */
    public static void main(String[] args) {
        launch(args);
    }
}
