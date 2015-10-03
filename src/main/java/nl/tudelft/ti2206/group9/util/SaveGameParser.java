package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Mathias
 */
public final class SaveGameParser {

	/** Playername. */
	private static String playername;
	/** Number of coins.*/
	private static long coins;
	/** Players highscore. */
	private static long highScore;
	/** Boolean to indicate whether the sound is enabled. */
	private static boolean soundEnabled;

	/**
	 * Private constructor.
	 */
	private SaveGameParser() { }

	/**
	 * Read a json savefile and store all data in the State class.
	 * @param path path to the file to be parsed.
	 */
	public static void loadGame(final String path) {
		try {
			final URL pathURL = new File(path).toURI().toURL();
			final InputStream stream = pathURL.openStream();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(stream, "UTF-8"));

			final JSONParser parser = new JSONParser();
			final JSONObject mainObject = (JSONObject) parser.parse(reader);

			parseJSON(mainObject);
			writeToState();

			reader.close();
		} catch (IOException e) {
			GameObservable.notify(Category.ERROR, Error.IOEXCEPTION,
					"SaveGameParser.loadGame(String)", e.getMessage());
		} catch (ParseException e) {
			GameObservable.notify(Category.ERROR, Error.PARSEEXCEPTION,
					"SaveGameParser.loadGame(String)", e.getMessage());
		}
	}

	/**
	 * Parse all json data from the file.
	 * @param mainObject the main JSON object
	 */
	private static void parseJSON(final JSONObject mainObject) {
		playername = (String) mainObject.get("playername");
		coins = (Long) mainObject.get("coins");

		final JSONObject settingsObj = (JSONObject) mainObject.get("settings");
		soundEnabled = (Boolean) settingsObj.get("soundEnabled");

		final JSONObject highObj = (JSONObject) mainObject.get("highscore");
		highScore = (Long) highObj.get("score");
	}

	/**
	 * Write all data to the State class.
	 */
	private static void writeToState() {
		State.setPlayerName(playername);
		State.setCoins((int) coins);
		State.setSoundEnabled(soundEnabled);
		State.setHighscore(highScore);
	}

}
