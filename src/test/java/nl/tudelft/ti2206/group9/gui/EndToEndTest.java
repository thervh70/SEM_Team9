package nl.tudelft.ti2206.group9.gui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Logger;
import nl.tudelft.ti2206.group9.util.Point3D;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;


@SuppressWarnings("restriction")
public class EndToEndTest extends ApplicationTest {

	/** Saved to use <pre>rootNode()</pre>. */
	private Stage stage;

	/** Multiplier for Robot sleeps. */
	private static final long TARDINESS = 10;
	/** Amount of milliseconds the Robot sleeps when sleeping "short". */
	private static final long SHORT = 2 * TARDINESS;
	/** Amount of milliseconds the Robot sleeps when sleeping "long". */
	private static final long LONG = 5 * TARDINESS;
	/** Sleep countdown. */
	private static final long COUNTDOWN = 3500;

	/** Delta for double equality. */
	private static final double DELTA = 0.000001;

	private static final int MAIN_START = 0;
	private static final int MAIN_SETTINGS = 1;
	private static final int MAIN_QUIT = 2;
	private static final int MAIN_LOADGAME = 3;
	private static final int MAIN_TEXTFIELD = 5;

	private static final int LOAD_BACK = 0;
	private static final int LOAD_START = 1;
	private static final int LOAD_NAMECONTAINER = 2;

	private static final int SETTINGS_BACK = 0;
	private static final int SETTINGS_SOUND = 1;

	private static final int PAUSE_RESUME = 0;
	private static final int PAUSE_TOMAIN = 1;

	private static final int DEATH_RETRY = 0;
	private static final int DEATH_TOMAIN = 1;

	private static final int WARNING_OK = 0;

	@Override
	public void start(final Stage primaryStage) {
		letPlayerSurvive();
		stage = primaryStage;
		new ShaftEscape().start(stage);
		State.resetAll();
	}

	@Test
	public void test() throws IOException { //NOPMD - assert is done in subs.
		clickOn(stage, MouseButton.PRIMARY);
		sleep(SHORT);
		mainMenu(MAIN_SETTINGS);
		clickAllSettings();
		mainMenu(MAIN_START);
		assertNull(ShaftEscape.getScene().getPopup());
		mainMenu(MAIN_TEXTFIELD);
		typeFaultyName();
		mainMenu(MAIN_START);
		clickPopup(WARNING_OK);
		mainMenu(MAIN_TEXTFIELD);
		keyboard(KeyCode.BACK_SPACE);
		typeName();

		mainMenu(MAIN_START);
		sleep(COUNTDOWN);
		keyboard(KeyCode.ESCAPE);
		clickPopup(PAUSE_RESUME);
		sleep(COUNTDOWN);
		moveAround();
		keyboard(KeyCode.ESCAPE);
		clickPopup(PAUSE_TOMAIN);

		mainMenu(MAIN_LOADGAME);
		loadMenu(LOAD_BACK);
		mainMenu(MAIN_LOADGAME);
		loadMenu(LOAD_START);
		clickPopup(WARNING_OK);
		loadMenu(LOAD_NAMECONTAINER);
		loadMenu(LOAD_START);

		sleep(COUNTDOWN);
		playerDies();
		sleep(LONG);
		clickPopup(DEATH_RETRY);
		sleep(COUNTDOWN);
		playerDies();
		clickPopup(DEATH_TOMAIN);

		mainMenu(MAIN_QUIT);
		outputEventLog();
	}

	private void outputEventLog() throws IOException {
		final String log = new String(Files.readAllBytes(
				Paths.get(Logger.OUTFILE)), StandardCharsets.UTF_8);
		System.out.println("\n== EVENT_LOG ==");     //NOPMD - Intended use of
		System.out.println(log);                     //NOPMD - System.out.print
		System.out.println("== END_EVENT_LOG ==\n"); //NOPMD - for Travis log
	}

	private void letPlayerSurvive() {
		State.getTrack().getPlayer().setInvincible(true);
	}

	private void clickAllSettings() {
		assertTrue("Sound should enabled at startup.", State.isSoundEnabled());
		settings(SETTINGS_SOUND);
		assertFalse("Sound disabled. (1)", State.isSoundEnabled());
		settings(SETTINGS_SOUND);
		assertTrue("Sound enabled. (2)", State.isSoundEnabled());
		settings(SETTINGS_SOUND);
		assertFalse("Sound disabled. (3)", State.isSoundEnabled());

		settings(SETTINGS_BACK);
	}

	private void typeName() {
		keyboard(KeyCode.CAPS);
		keyboard(KeyCode.F);
		keyboard(KeyCode.CAPS);
		keyboard(KeyCode.R);
		keyboard(KeyCode.E);
		keyboard(KeyCode.D);
	}

	private void typeFaultyName() {
		keyboard(KeyCode.SLASH);
	}

	private void moveAround() {
		final int s1 = 5 * InternalTicker.NANOS_PER_TICK / InternalTicker.E6;
		final int s2 = 75 * InternalTicker.NANOS_PER_TICK / InternalTicker.E6;
		final Point3D center = State.getTrack().getPlayer().getCenter();
		final Point3D size = State.getTrack().getPlayer().getSize();

		keyboard(KeyCode.LEFT);
		sleep(s1);
		assertTrue("Player moves to the left", center.getX() < 0);
		keyboard(KeyCode.RIGHT);
		sleep(s2);
		assertEquals("Player centers from the left", 0, center.getX(), DELTA);

		keyboard(KeyCode.D);
		sleep(s1);
		assertTrue("Player moves to the right", center.getX() > 0);
		keyboard(KeyCode.A);
		sleep(s2);
		assertEquals("Player centers from the right", 0, center.getX(), DELTA);

		keyboard(KeyCode.UP);
		sleep(s1);
		assertTrue("Player jumps", center.getY() > 1);
		sleep(s2);

		keyboard(KeyCode.DOWN);
		sleep(s1);
		assertTrue("Player slides", size.getY() < Player.HEIGHT);
		sleep(s2);
	}

	private void keyboard(final KeyCode kc) {
		press(kc);
		release(kc);
		sleep(SHORT);
	}

	private void mainMenu(final int buttonNo) {
		ObservableList<Node> buttons;
		buttons = rootNode(stage).getScene().getRoot()
				.getChildrenUnmodifiable();
		clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
		letPlayerSurvive();			// Make sure there are no obstacles
		sleep(LONG);
	}

	private void settings(final int buttonNo) {
		ObservableList<Node> buttons;
		buttons = rootNode(stage).getScene().getRoot()
				.getChildrenUnmodifiable();
		clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
		sleep(SHORT);
	}

	private void loadMenu(final int buttonNo) {
		ObservableList<Node> buttons;
		buttons = rootNode(stage).getScene().getRoot()
				.getChildrenUnmodifiable();
		clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
		sleep(SHORT);
	}

	private void playerDies() {
		State.getTrack().getPlayer().die();
		sleep(2 * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		letPlayerSurvive();			// Make sure there are no obstacles
		sleep(LONG);
	}

	private void clickPopup(final int buttonNo) {
		AbstractScene scene = ShaftEscape.getScene();
		if (scene.getPopup() == null) {
			fail("The Popup is not available.");
		}
		ObservableList<Node> buttons;
		sleep(1);
		buttons = ((VBox) scene.getPopup().getContent().get(1))
				.getChildren();
		buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
		try {
			clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
		} catch(ArrayIndexOutOfBoundsException e) {
			fail("ButtonNo " + buttonNo + " does not exist");
		}
		sleep(SHORT);
	}
}
