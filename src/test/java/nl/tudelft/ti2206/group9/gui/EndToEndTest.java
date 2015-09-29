package nl.tudelft.ti2206.group9.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

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

	@Override
	public void start(final Stage primaryStage) {
		letPlayerSurvive();
		stage = primaryStage;
		new ShaftEscape().start(stage);
		State.resetAll();
	}

	@Test
	public void test() throws IOException { //NOPMD - assert is done in Settings
		clickOn(stage, MouseButton.PRIMARY);
		sleep(SHORT);
		mainMenu(MAIN_SETTINGS);
		clickAllSettings();
		mainMenu(MAIN_TEXTFIELD);
		typeName();

		mainMenu(MAIN_START);
		keyboard(KeyCode.ESCAPE);
		pausePopup(PAUSE_RESUME);
		moveAround();
		keyboard(KeyCode.ESCAPE);
		pausePopup(PAUSE_TOMAIN);

		mainMenu(MAIN_LOADGAME);
		loadMenu(LOAD_BACK);
		mainMenu(MAIN_LOADGAME);
		loadMenu(LOAD_NAMECONTAINER);
		loadMenu(LOAD_START);

		mainMenu(MAIN_START);
		playerDies();
		deathPopup(DEATH_RETRY);
		playerDies();
		deathPopup(DEATH_TOMAIN);

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
		assertTrue(State.isSoundEnabled());
		settings(SETTINGS_SOUND);
		assertFalse(State.isSoundEnabled());
		settings(SETTINGS_SOUND);
		assertTrue(State.isSoundEnabled());
		settings(SETTINGS_SOUND);
		assertFalse(State.isSoundEnabled());

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

	private void moveAround() {
		final int before = 5;
		final int after = 75;

		keyboard(KeyCode.LEFT);
		sleep(before * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		assertTrue(State.getTrack().getPlayer().getCenter().getX() < 0);
		keyboard(KeyCode.RIGHT);
		sleep(after * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		assertEquals(0, State.getTrack().getPlayer().getCenter().getX(), DELTA);

		keyboard(KeyCode.D);
		sleep(before * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		assertTrue(State.getTrack().getPlayer().getCenter().getX() > 0);
		keyboard(KeyCode.A);
		sleep(after * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		assertEquals(0, State.getTrack().getPlayer().getCenter().getX(), DELTA);

		keyboard(KeyCode.UP);
		sleep(before * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		assertTrue(State.getTrack().getPlayer().getCenter().getY() > 1);
		sleep(after * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);

		keyboard(KeyCode.DOWN);
		sleep(before * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		assertTrue(State.getTrack().getPlayer().getSize().getY()
				< Player.HEIGHT);
		sleep(after * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
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

	private void pausePopup(final int buttonNo) {
		ObservableList<Node> buttons;
		buttons = ((VBox) GameScene.getPopup().getContent().get(1))
				.getChildren();
		buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
		clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
		sleep(LONG);
	}

	private void playerDies() {
		State.getTrack().getPlayer().die();
		sleep(2 * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
		letPlayerSurvive();			// Make sure there are no obstacles
		sleep(LONG);
	}

	private void deathPopup(final int buttonNo) {
		ObservableList<Node> buttons;
		sleep(1);
		buttons = ((VBox) GameScene.getPopup().getContent().get(1))
				.getChildren();
		buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
		clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
		sleep(SHORT);
	}

}
