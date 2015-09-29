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
import javafx.scene.media.MediaException;
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

	@Override
	public void start(final Stage primaryStage) {
		letPlayerSurvive();
		stage = primaryStage;
		new ShaftEscape().start(stage);
	}

	@Test
	public void test() throws IOException {
		boolean soundEnabled = State.isSoundEnabled();
		clickOn(stage, MouseButton.PRIMARY);
		sleep(SHORT);

		mainMenu(1);				// Click settings
		settings(1);				// Toggle sound
		assertFalse(State.isSoundEnabled() == soundEnabled);
		settings(1);				// Toggle sound
		assertTrue(State.isSoundEnabled() == soundEnabled);
		settings(0);				// Click Back

		mainMenu(5);				// Select textfield
		typeName();					// Enter name
		mainMenu(0);				// Click start
		keyboard(KeyCode.ESCAPE);	// Press Escape
		pausePopup(0);				// Click resume
		moveAround();				// Move around
		keyboard(KeyCode.ESCAPE);	// Press Escape
		pausePopup(1);				// Click "Main menu"

		mainMenu(3);				// Click Load game
		loadMenu(0);				// Back to main
		mainMenu(3);				// Back to Load game
		loadMenu(2);				// Select name
		loadMenu(1);				// Click load

		mainMenu(0);				// Click start
		playerDies();				// Player dies
		deathPopup(0);				// Click "Try Again"
		playerDies();				// Player dies
		deathPopup(1);				// Click "Main Menu"

		mainMenu(2);				// Click quit
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

	private void typeName() {
		keyboard(KeyCode.CAPS);
		keyboard(KeyCode.F);
		keyboard(KeyCode.CAPS);
		keyboard(KeyCode.R);
		keyboard(KeyCode.E);
		keyboard(KeyCode.D);
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

	private void loadMenu(int buttonNo) {
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
