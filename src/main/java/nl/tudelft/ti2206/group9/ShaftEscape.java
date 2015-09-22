package nl.tudelft.ti2206.group9;

import javafx.application.Application;
import javafx.stage.Popup;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.gui.AbstractScene;
import nl.tudelft.ti2206.group9.gui.SplashScene;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.Logger;

@SuppressWarnings("restriction")
public class ShaftEscape extends Application {

	private static Stage stage;

	/**
	 * Start the application in the SplashScene.
	 * @param appStage the primary Stage for the Application. This is where
	 * the scenes are shown in.
	 */
	@Override
	public void start(Stage appStage) {
		stage = appStage;
		stage.setResizable(false);
		
    	GameObservable.addObserver(new Logger());
		setScene(new SplashScene());
	}

	/** Setting the right scene and displaying it. */
	public static void setScene(AbstractScene newScene) {
		stage.setScene(newScene);
		stage.show();
	}

	/** Shows a popup on the screen. */
	public static void showPopup(Popup popup) {
		popup.show(stage);
		popup.setAnchorX(stage.getX() + stage.getWidth() / 2
				- popup.getWidth() / 2);
		popup.setAnchorY(stage.getY() + stage.getHeight() / 2
				- popup.getHeight() / 2);
	}

	/** Exits the Application. */
	public static void exit() {
		stage.close();
	}

	/**
	 * Launch JavaFX.
	 * @param optional JavaFX arguments
	 */
	public static void main(String... args) {
		launch(args);
	}

}
