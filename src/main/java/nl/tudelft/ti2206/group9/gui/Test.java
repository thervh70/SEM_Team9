package nl.tudelft.ti2206.group9.gui;

/**
 * Created by Robin.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Test extends Application {

    public Parent createContent() throws Exception {

        // Box
        final Box testBox = new Box(10, 10, 10);
        testBox.setMaterial(new PhongMaterial(Color.RED));

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, -15, -100));

        // Build the Scene Graph
        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(testBox);



        // Use a SubScene
        SubScene subScene = new SubScene(root, 800,800);
        subScene.setFill(Color.LIGHTGRAY);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);

        subScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                System.out.println(mouseEvent.getButton());
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    testBox.setTranslateX(-15);
                }
                else if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                    testBox.setTranslateX(0);
                }
                else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    testBox.setTranslateX(15);
                }

            }
        });


        return group;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}
