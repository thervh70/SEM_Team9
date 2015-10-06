package nl.tudelft.ti2206.group9.util;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Add this Logger to GameObservable, and it will log every single action in
 * the game. The log file will only be written to the (@link #OUTFILE} when the
 * {@link Logger#writeToFile} method is called.
 * @author Maarten
 */
public class Logger implements GameObserver {

	/** String buffer that should be written to the output.
	 *  The buffer is reset every time it is written to the log file (NOPMD). */
	private final StringBuilder buffer = new StringBuilder();     //NOPMD
	/** Path of the file this Logger will write its logs to. */
	private String path = OUTFILE;
	/** Map containing all Strings to output to log file. */
	private static final Map<Specific, String> STRINGS =
			new ConcurrentHashMap<Specific, String>();

	/** Location of the log file. */
	public static final String OUTFILE = "./events.log";
	/** Format of the Timestamp in the log file. */
	public static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	static {
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

		lbl = " [ERROR ] ";
		final String info = "\n    in %s" + "\n    Message: %s";
		STRINGS.put(Error.IOEXCEPTION,
				lbl + "Exception while reading or writing files!" + info);
		STRINGS.put(Error.MALFORMEDURLEXCEPTION,
				lbl + "Exception while parsing URL!" + info);
		STRINGS.put(Error.MEDIAEXCEPTION,
				lbl + "Exception with audio player!" + info);
		STRINGS.put(Error.PARSEEXCEPTION,
				lbl + "Exception while parsing JSON!" + info);
	}

	/**
	 * Default constructor. Empties the existing log file at path
	 * {@link #OUTFILE}.
	 */
	public Logger() {
		this(OUTFILE);
	}

	/**
	 * Creates a new Logger, that will log to the filePath. Empties the existing
	 * log file at filePath.
	 * @param filePath path of the file to log to.
	 */
	public Logger(final String filePath) {
		super();
		path = filePath;
		writeToOutput("", false);
	}

	public void update(final Observable o, final Object arg) {
		final GameUpdate update = (GameUpdate) arg;
		buffer.append(getLogString(update.getSpec(), update.getArgs()));
		buffer.append('\n');
	}

	/**
	 * Writes the current buffer to the output file. This should be called
	 * before exiting the application. The buffer is used instead of writing to
	 * the log file on each {@link #gameUpdate} call.
	 * @param append whether the buffer should be appended to the current log
	 *        file or not. A new file is created at every application startup.
	 * @return whether the writing has been successful.
	 */
	public boolean writeToFile(final boolean append) {
		final boolean success = writeToOutput(new String(buffer), append);
		if (success) {
			buffer.setLength(0);
		}
		return success;
	}

	/**
	 * Default behaviour for the Logger when writing to the hard disk.
	 * <pre>append = true;</pre>
	 * @see Logger#writeToFile(boolean)
	 * @return whether the writing has been successful.
	 */
	public boolean writeToFile() {
		return writeToFile(true);
	}

	/**
	 * Retrieves the string to be logged from the hashmap in correct format.
	 * @param spec Specific
	 * @param optionalArgs Array of optional args
	 * @return Get part of log as a string
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
	 * @return whether the logging has been successful.
	 */
	private boolean writeToOutput(final String str,
			final boolean append) {
		boolean ret = true;
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path, append), "UTF-8"
					));
			fw.write(str);
			fw.flush();
		} catch (IOException e) {
			ret = false;
			OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
					"Logger.writeToOutput(String, boolean)", e.getMessage());
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				ret = false;
				OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
						"Logger.writeToOutput(String, boolean) (2)",
						e.getMessage());
			}
		}
		return ret;
	}

}
