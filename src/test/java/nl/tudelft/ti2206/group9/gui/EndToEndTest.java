package nl.tudelft.ti2206.group9.gui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@SuppressWarnings("restriction")
public class EndToEndTest extends ApplicationTest {
	
	/** Saved to use <pre>rootNode()</pre>. */
	private Stage stage;
	
	/** Multiplier for Robot sleeps. */
	private static final long TARDINESS = 10;
	/** Amount of milliseconds the Robot sleeps when sleeping "short". */
	public static final long SHORT = 2 * TARDINESS;
	/** Amount of milliseconds the Robot sleeps when sleeping "long". */
	public static final long LONG = 5 * TARDINESS;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		new SplashScreen().start(stage);
	}

	@Test
	public void test() {
		ObservableList<Node> buttons;
		
		sleep(LONG);
		clickOn(stage, MouseButton.PRIMARY);
		sleep(SHORT);
		
		buttons = rootNode(stage).getScene().getRoot()
				.getChildrenUnmodifiable();
		clickOn(buttons.get(0), MouseButton.PRIMARY);	// Click start
		sleep(LONG);
		
		press(KeyCode.ESCAPE);							// Press Escape
		release(KeyCode.ESCAPE);
		sleep(SHORT);
		
		buttons = ((VBox) GameScreen.getPopup().getContent().get(1))
				.getChildren();
		buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
		clickOn(buttons.get(0), MouseButton.PRIMARY);	// Click resume
		sleep(LONG);
		
		press(KeyCode.ESCAPE);							// Press Escape
		release(KeyCode.ESCAPE);
		sleep(SHORT);
		
		buttons = ((VBox) GameScreen.getPopup().getContent().get(1))
				.getChildren();
		buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
		clickOn(buttons.get(1), MouseButton.PRIMARY);	// Click "Main menu"
		sleep(SHORT);
		
		buttons = rootNode(stage).getScene().getRoot()
				.getChildrenUnmodifiable();
		clickOn(buttons.get(0), MouseButton.PRIMARY);	// Click start
		sleep(LONG);
		
		State.getTrack().getPlayer().die();				// Player dies
		sleep(InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		sleep(LONG);

		buttons = ((VBox) GameScreen.getPopup().getContent().get(1))
				.getChildren();
		buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
		clickOn(buttons.get(0), MouseButton.PRIMARY);	// Click "Try Again"
		sleep(LONG);
		
		State.getTrack().getPlayer().die();				// Player dies
		sleep(InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		sleep(LONG);

		buttons = ((VBox) GameScreen.getPopup().getContent().get(1))
				.getChildren();
		buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
		clickOn(buttons.get(1), MouseButton.PRIMARY);	// Click "Main Menu"
		sleep(SHORT);
		
		buttons = rootNode(stage).getScene().getRoot()
				.getChildrenUnmodifiable();
		clickOn(buttons.get(2), MouseButton.PRIMARY);	// Click quit
	}

}
