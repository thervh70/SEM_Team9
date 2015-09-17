package nl.tudelft.ti2206.group9.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Add this Logger to GameObservable, and it will log every single action in
 * the game.
 * @author Maarten
 */
public class Logger implements GameObserver {

	/** Map containing all Strings to output to log file. */
	public static final Map<Specific, String> STRINGS =
			new HashMap<Specific, String>();
	/** Location of the log file. */
	public static final String OUTFILE = "./events.log";
	/** Format of the Timestamp in the log file. */
	public static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	{
		FileWriter fw;
		try {
			fw = new FileWriter(OUTFILE, false);
			fw.write("");
			fw.close();
		} catch (IOException e) { }
		String lbl;

		lbl = " [ GAME ] ";
		STRINGS.put(Game.PAUSED, 		lbl + "Game has been paused.");
		STRINGS.put(Game.RESUMED, 		lbl + "Game has been resumed.");
		STRINGS.put(Game.RETRY, 		lbl + "Game will retry a new run.");
		STRINGS.put(Game.STARTED, 		lbl + "Game has been started.");
		STRINGS.put(Game.STOPPED, 		lbl + "Game has been stopped.");
		STRINGS.put(Game.TO_MAIN_MENU, 	lbl + "Game exited to main menu.");

		lbl = " [INPUT ] ";
		STRINGS.put(Input.KEYBOARD, 	lbl + "Pressed keyboard key %s.");
		STRINGS.put(Input.MOUSE, 		lbl + "Pressed Mouse button %s.");

		lbl = " [ MENU ] ";
		STRINGS.put(Menu.ANY_KEY, 		lbl + "Any key pressed.");
		STRINGS.put(Menu.EXIT, 			lbl + "Clicked \"Exit\".");
		STRINGS.put(Menu.SETTINGS, 		lbl + "Clicked \"Settings\".");
		STRINGS.put(Menu.START, 		lbl + "Clicked \"Start game\".");

		lbl = " [PLAYER] ";
		STRINGS.put(Player.COLLISION, 	lbl + "Collided with %s.");
		STRINGS.put(Player.JUMP, 		lbl + "Jumping.");
		STRINGS.put(Player.SLIDE, 		lbl + "Sliding.");
		STRINGS.put(Player.START_MOVE, 	lbl + "Started moving from lane %d.");
		STRINGS.put(Player.STOP_MOVE, 	lbl + "Stopped moving in lane %d.");
	}

	@Override
	public void gameUpdate(Category cat, Specific spec, Object[] optionalArgs) {
		String line = getLogString(spec, optionalArgs);

		FileWriter fw;
		try {
			fw = new FileWriter(OUTFILE, true);
			fw.write(line + "\n");
			fw.flush();
			fw.close();
		} catch (IOException e) { }
	}

	/** Retrieves the string to be logged from the hashmap in correct format. */
	private String getLogString(Specific spec, Object[] optionalArgs) {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
		String time = date.format(formatter);
		return time + String.format(STRINGS.get(spec), optionalArgs);
	}

}
