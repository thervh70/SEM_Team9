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
	private String playername;
	/** Number of coins.*/
	private int coins;
	/** Players highscore. */
	private double highScore;
	/** Number of coins collected in players last highscore run. */
	private int highCoins;
	/** Boolean to indicate whether the sound is enabled. */
	private boolean soundEnabled;

	/**
	 * Load all data from the given file and save it in State.
	 * @param filePath the path to the file to be read.
	 */
	public final void loadGame(final String filePath) {
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
	 * @throws IOException IOException
	 * @throws ParseException ParseException
	 */
	private JSONObject parserInit(final String filePath) throws IOException, ParseException {
		FileReader reader = new FileReader(filePath);
		JSONParser parser = new JSONParser();
		JSONObject mainObject = (JSONObject) parser.parse(reader);

		return mainObject;
	}

	/**
	 * Write all data to the State class.
	 */
	private void writeToState() {
		State.setPlayerName(playername);
		State.setCoins(coins);
		State.setSoundEnabled(soundEnabled);
		State.setScore(highScore);
	}
}
