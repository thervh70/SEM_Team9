package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;
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

	/**
	 * Private constructor.
	 */
	private SaveGameParser() { }

	/** Playername. */
	private static String playername;
	/** Number of coins.*/
	private static int coins;
	/** Players highscore. */
	private static double highScore;
	/** Boolean to indicate whether the sound is enabled. */
	private static boolean soundEnabled;

	/** Default folder to read savefiles from. */
	private static String fileFolder =
			"src/main/resources/nl/tudelft/ti2206/group9/util/";

	/**
	 * Read a json savefile and store all data in the State class.
	 * @param fileName path to the file to be parsed.
	 */
	public static void loadGame(final String fileName) {
		try {
			URL path = new File(fileFolder + fileName).toURI().toURL();
			InputStream stream = path.openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(stream, "UTF-8"));

			JSONParser parser = new JSONParser();
			JSONObject mainObject = (JSONObject) parser.parse(reader);

			parseJSON(mainObject);
			writeToState();

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse all json data from the file.
	 * @param mainObject the main JSON object
	 */
	private static void parseJSON(final JSONObject mainObject) {
		playername = (String) mainObject.get("playername");
		coins = Integer.parseInt((String) mainObject.get("coins"));

		JSONObject settingsObj = (JSONObject) mainObject.get("settings");
		String soundEnabledString = (String) settingsObj.get("soundEnabled");
		soundEnabled = soundEnabledString.equals("true");

		JSONObject highScoreObj = (JSONObject) mainObject.get("highscore");
		highScore = Double.parseDouble((String) highScoreObj.get("score"));
	}

	/**
	 * Write all data to the State class.
	 */
	private static void writeToState() {
		State.setPlayerName(playername);
		State.setCoins(coins);
		State.setSoundEnabled(soundEnabled);
		State.setScore(highScore);
	}
}
