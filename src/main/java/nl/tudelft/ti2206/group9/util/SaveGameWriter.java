package nl.tudelft.ti2206.group9.util;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

import org.json.simple.JSONObject;

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

		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new OutputStreamWriter(
			    new FileOutputStream(path), "UTF-8"
			));
			fw.write(mainObject);
			fw.flush();
		} catch (IOException e) {
			OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
					"SaveGameWriter.saveGame(String)", e.getMessage());
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
						"SaveGameWriter.saveGame(String) (2)", e.getMessage());
			}
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
