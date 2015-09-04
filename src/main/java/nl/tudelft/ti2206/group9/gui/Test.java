package nl.tudelft.ti2206.group9.gui;

/**
 * Created by Robin.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class Test extends Application {

	private void buildAxes(Group root) {
		final Group axisGroup = new Group();

		System.out.println("buildAxes()");
		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		final Box xAxis = new Box(20, 1, 1);
		final Box yAxis = new Box(1, 20, 1);
		final Box zAxis = new Box(1, 1, 20);

		xAxis.setMaterial(redMaterial);
		yAxis.setMaterial(greenMaterial);
		zAxis.setMaterial(blueMaterial);

		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		root.getChildren().addAll(axisGroup);
	}

	@Override
	public void start(Stage primaryStage) {

		Group root = new Group();
		Scene scene = new Scene(root, 640, 480);
		primaryStage.setScene(scene);

		final Box player = new Box(10, 10, 10);
		player.setMaterial(new PhongMaterial(Color.ORANGE));

		final Box backwall = new Box(1000, 1000, 1);
		backwall.setMaterial(new PhongMaterial(Color.AQUA));
		backwall.setTranslateZ(500);

		final Box track = new Box(50, 5, 500);
		track.setMaterial(new PhongMaterial(Color.WHITESMOKE));
		track.setTranslateY(10);

		// Create and position camera
		final PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.getTransforms().addAll(
				new Translate(0, -15, -100),
				new Rotate(-10, Rotate.X_AXIS),
				new Rotate(0.01, Rotate.Z_AXIS) // else it bugs, for some reason
				);
		camera.setNearClip(0);
		camera.setFarClip(1000);

		scene.setCamera(camera);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				KeyCode code = keyEvent.getCode();
				System.out.println("Key Pressed: " + code.toString());

				switch (code) {
				case LEFT:
					player.setTranslateX(-15);
					break;
				case DOWN:
					player.setTranslateX(0); 
				break;
				case RIGHT: 
					player.setTranslateX(15);
				break;
				default: break;
				}
			}
		});

		//Track first because of layering (kind of buggy)
		root.getChildren().add(backwall);
		root.getChildren().add(track);

		buildAxes(root);
		root.getChildren().add(camera);
		root.getChildren().add(player);


		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Java main for when running without JavaFX launcher
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
