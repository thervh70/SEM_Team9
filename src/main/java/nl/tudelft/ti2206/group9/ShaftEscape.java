package nl.tudelft.ti2206.group9;

import javafx.application.Application;
import javafx.stage.Popup;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.gui.AbstractScene;
import nl.tudelft.ti2206.group9.gui.SplashScene;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.Logger;

/**
 * Starting point of the Application.
 * Contains method to switch Scenes or show Popups.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class ShaftEscape extends Application {

	private static Stage stage;
	/** Width of the Window. */
	public static final int WIDTH = 480 - 16;
	/** Height of the Window. */
	public static final int HEIGHT = 640;
	/** Lock used so that the tickers won't use the Track concurrently. */
	public static final Object TICKER_LOCK = new Object();

	/**
	 * Start the application in the SplashScene.
	 * @param appStage the primary Stage for the Application. This is where
	 * the scenes are shown in.
	 */
	@Override
	public void start(Stage appStage) {
		setStage(appStage);
		stage.setResizable(false);
		stage.setWidth(ShaftEscape.WIDTH);
		stage.setHeight(ShaftEscape.HEIGHT);
		stage.setMinWidth(ShaftEscape.WIDTH);
		stage.setMinHeight(ShaftEscape.HEIGHT);
		stage.setMaxWidth(ShaftEscape.WIDTH);
		stage.setMaxHeight(ShaftEscape.HEIGHT);

		GameObservable.addObserver(new Logger());
		setScene(new SplashScene());
	}
	
	/** @param newStage the new stage to set as private static field. */
	private static void setStage(Stage newStage) {
		stage = newStage;
	}

	/**
	 * Setting the right scene and displaying it.
	 * @param newScene the new Scene that is to be showed.
	 */
	public static void setScene(AbstractScene newScene) {
		stage.setScene(newScene);
		stage.show();
	}

	/**
	 * Shows a popup on the screen.
	 * @param popup the Popup that is to be shown.
	 */
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
	 * @param args optional JavaFX arguments
	 */
	public static void main(String... args) {
		launch(args);
	}

}
