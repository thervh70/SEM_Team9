package nl.tudelft.ti2206.group9;

import nl.tudelft.ti2206.group9.gui.SplashScreen;
import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class ShaftEscape extends Application {

	/**
	 * Start the application in the SplashScreen.
	 * @param primaryStage the primary Stage for the Application. This is where
	 * the scenes are shown in.
	 */
	@Override
	public void start(Stage primaryStage) {
		new SplashScreen().start(primaryStage);
	}

	/**
	 * Launch JavaFX
	 * @param optional JavaFX arguments
	 */
	public static void main(String... args) {
		launch(args);
	}

}
