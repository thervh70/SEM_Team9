package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Mathias
 */
public class SaveGameParser {

	/** Playername. */
	static String playername;
	/** Number of coins.*/
	static int coins;
	/** Players highscore. */
	static double highScore;
	/** Number of coins collected in players last highscore run. */
	static int highCoins;
	/** Boolean to indicate whether the sound is enabled. */
	static boolean soundEnabled;

	/**
	 * Main is only for testing purposes!
	 */
	public static void main(String[] args) {
		loadGame("src/main/resources/nl/tudelft/ti2206/group9/util/firstSaveGame.json");
	}

	/**
	 * Load all data from the given file and save it in State
	 * @param filePath the path to the file to be read.
	 */
	public static void loadGame(String filePath) {
		try {
			JSONObject mainObject = parserInit(filePath);
			playername = (String) mainObject.get("playername");
			coins = Integer.valueOf((String) mainObject.get("coins"));

			JSONObject settingsObj = (JSONObject) mainObject.get("settings");
			String soundEnabledString = (String) settingsObj.get("soundEnabled");
			soundEnabled = soundEnabledString.equals("true");

			JSONObject highScoreObj = (JSONObject) mainObject.get("highscore");
			highScore = Double.valueOf((String) highScoreObj.get("score"));
			highCoins = Integer.valueOf((String) highScoreObj.get("coins"));

			printAllJSON();
			writeToState();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the parsing process.
	 * @param filePath path to the file to be parsed.
	 * @return JSONObject the main JSONObjct from the read file
	 * @throws IOException
	 * @throws ParseException
	 */
	private static JSONObject parserInit(String filePath) throws IOException, ParseException {
		FileReader reader = new FileReader(filePath);
		JSONParser parser = new JSONParser();
		JSONObject mainObject = (JSONObject) parser.parse(reader);

		return mainObject;
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

	/**
	 * For testing purposes only
	 */
	public static void printAllJSON() {
		System.out.println("JSONPARSINGSTUFF!!\n");
		System.out.println("PlayerName: " + playername);
		System.out.println("Coins: " + coins);
		System.out.println("Settings:");
		System.out.println("  SoundEnabled: " + soundEnabled + "\n");
		System.out.println("HighScore:");
		System.out.println("  Score: " + highScore);
		System.out.println("  Coins: " + highCoins);
	}
}
