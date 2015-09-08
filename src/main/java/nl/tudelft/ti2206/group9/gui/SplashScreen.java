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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.ti2206.group9.gui.StartScreen;

/**
 * Created by Maikel on 08/09/2015.
 */
public class SplashScreen extends Application {

    public void start(final Stage primaryStage) {


        StackPane root = new StackPane();
        Scene scene = new Scene(root, 640, 480);

        Label text = new Label("Press any key to continue");


        root.setOnMouseClicked(new EventHandler<MouseEvent>() {


            public void handle(MouseEvent t) {
                StartScreen.start(primaryStage);
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent ke) {
                StartScreen.start(primaryStage);
            }
        });

        //add text to the canvas and give it a fade in/ fade out effect
        root.getChildren().add(text);
        root.setAlignment(Pos.CENTER);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), text);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1500);
        ft.setAutoReverse(true);
        ft.play();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
