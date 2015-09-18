package nl.tudelft.ti2206.group9.util;

import java.io.FileWriter;
import java.io.IOException;
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

	{
		final FileWriter fw;
		try {
			fw = new FileWriter(OUTFILE, false);
			fw.write("");
			fw.close();
		} catch (IOException e) {
		}
		
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

		lbl = " [PLAYER] ";
		STRINGS.put(Player.COLLISION, lbl + "Collided with %s.");
		STRINGS.put(Player.JUMP, lbl + "Jumping.");
		STRINGS.put(Player.SLIDE, lbl + "Sliding.");
		STRINGS.put(Player.START_MOVE, lbl + "Started moving from lane %d.");
		STRINGS.put(Player.STOP_MOVE, lbl + "Stopped moving in lane %d.");
	}

	public void gameUpdate(final Category cat, final Specific spec, 
			final Object... optionalArgs) {
		final String line = getLogString(spec, optionalArgs);

		final FileWriter fw;
		try {
			fw = new FileWriter(OUTFILE, true);
			fw.write(line + "\n");
			fw.flush();
			fw.close();
		} catch (IOException e) { }
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

}
