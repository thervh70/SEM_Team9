package nl.tudelft.ti2206.group9.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Add this Logger to GameObservable, and it will log every single action in
 * the game.
 * @author Maarten
 */
public class Logger implements GameObserver {

	/** Map containing all Strings to output to log file. */
	private static final Map<Specific, String> STRINGS =
			new ConcurrentHashMap<Specific, String>();
	/** Location of the log file. */
	public static final String OUTFILE = "./events.log";
	/** Format of the Timestamp in the log file. */
	public static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	static {
		writeToOutput("", false); // Empty existing log

		String lbl;

		lbl = " [ GAME ] ";
		STRINGS.put(Game.PAUSED, lbl + "Game has been paused.");
		STRINGS.put(Game.RESUMED, lbl + "Game has been resumed.");
		STRINGS.put(Game.RETRY, lbl + "Game will retry a new run.");
		STRINGS.put(Game.STARTED, lbl + "Game has been started.");
		STRINGS.put(Game.STOPPED, lbl + "Game has been stopped.");
		STRINGS.put(Game.TO_MAIN_MENU, lbl + "Game exited to main menu.");

		lbl = " [INPUT ] ";
		STRINGS.put(Input.KEYBOARD, lbl + "Pressed keyboard key %s.");
		STRINGS.put(Input.MOUSE, lbl + "Pressed mouse button %s.");

		lbl = " [ MENU ] ";
		STRINGS.put(Menu.ANY_KEY, lbl + "Any key pressed.");
		STRINGS.put(Menu.EXIT, lbl + "Pressed \"Exit\".");
		STRINGS.put(Menu.SETTINGS, lbl + "Pressed \"Settings\".");
		STRINGS.put(Menu.SETTING_SOUND, lbl + "  Toggled sound, is now %s.");
		STRINGS.put(Menu.SETTINGS_BACK, lbl + "  Back to Main Menu.");
		STRINGS.put(Menu.START, lbl + "Pressed \"Start game\".");
		STRINGS.put(Menu.LOAD_MENU, lbl + "Pressed \"Load game\".");
		STRINGS.put(Menu.LOAD_BACK, lbl + "Back to main menu.");
		STRINGS.put(Menu.LOAD, lbl + "Pressed \"Start load game\".");

		lbl = " [PLAYER] ";
		STRINGS.put(Player.COLLISION, lbl + "Collided with %s.");
		STRINGS.put(Player.JUMP, lbl + "Jumping.");
		STRINGS.put(Player.SLIDE, lbl + "Sliding.");
		STRINGS.put(Player.START_MOVE, lbl + "Started moving from lane %d.");
		STRINGS.put(Player.STOP_MOVE, lbl + "Stopped moving in lane %d.");
	}

	/**
	 * Is called when the game is updated. The internal classes should call
	 * {@link GameObservable#notify(Category, Specific, Object...)}
	 * to update GameObservers.
	 * @param cat the Category of this update.
	 * @param spec the Specific action of this update.
	 * @param optionalArgs Optional arguments that come with the update
	 * 			(e.g. lane numbers, mouse buttons, keyboard keys, ...)
	 */
	public void gameUpdate(final Category cat, final Specific spec,
			final Object... optionalArgs) {
		writeToOutput(getLogString(spec, optionalArgs) + "\n", true);
	}

	/** Retrieves the string to be logged from the hashmap in correct format.
	 *  @param spec Specific
	 *  @param optionalArgs Array of optional args
	 *  @return Get part of log as a string
	 */
	private String getLogString(final Specific spec,
			final Object... optionalArgs) {
		final LocalDateTime date = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
		final String time = date.format(formatter);
		return time + String.format(STRINGS.get(spec), optionalArgs);
	}

	/**
	 * Writes <pre>str</pre> to the {@link #OUTFILE}.
	 * @param str String to write.
	 * @param append Whether to append or create an empty file.
	 */
	private static void writeToOutput(final String str, final boolean append) {
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new OutputStreamWriter(
			    new FileOutputStream(OUTFILE, append), "UTF-8"
			));
			fw.write(str);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
