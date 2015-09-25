package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Mathias
 */
public final class SaveGameWriter {

    /**
     * Private constructor.
     */
    private SaveGameWriter() { }

    /**
     * Save a game by writing it to a JSON file.
     * @param path the path of the JSON file it has to be written to
     */
    public static void saveGame(final String path) {
    	final String mainObject = writeToJSON();

        try {
        	final FileWriter writer = new FileWriter(path);

            writer.write(mainObject);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a JSON string from all the data
     * which can be writte to a JSON file.
     * @return the JSON string
     */                      // Eclipse thinks JSONObject.put belongs to HashMap
    @SuppressWarnings("unchecked")
	private static String writeToJSON() {
        final JSONObject mainObject = new JSONObject();

        final JSONObject settings = new JSONObject();
        settings.put("soundEnabled", State.isSoundEnabled());
        mainObject.put("settings", settings);

        mainObject.put("playername", State.getPlayerName());
        mainObject.put("coins", State.getCoins());

        final JSONObject highscore = new JSONObject();
        highscore.put("score", State.getHighscore());
        mainObject.put("highscore", highscore);

        return mainObject.toJSONString();
    }
}
