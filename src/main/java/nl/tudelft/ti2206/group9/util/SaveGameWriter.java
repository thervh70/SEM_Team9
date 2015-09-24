package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Mathias
 */
public final class SaveGameWriter {

    /** Default folder to write JSON savefiles to. */
    private static String fileFolder =
            "src/main/resources/nl/tudelft/ti2206/group9/util/";

    /**
     * Private constructor.
     */
    private SaveGameWriter() { }

    /**
     * Save a game by writing it to a JSON file.
     * @param fileName the path of the JSON file it has to be written to
     */
    public static void saveGame(final String fileName) {
        String mainObject = writeToJSON();

        try {
            FileWriter writer = new FileWriter(fileFolder + fileName);

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
     */
    private static String writeToJSON() {
        JSONObject mainObject = new JSONObject();

        JSONObject settings = new JSONObject();
        String soundString = "false";
        if (State.isSoundEnabled()) {
            soundString = "true";
        }
        settings.put("soundEnabled", soundString);
        mainObject.put("settings", settings);

        mainObject.put("playername", State.getPlayerName());
        mainObject.put("coins", new Integer(State.getCoins()).toString());

        JSONObject highscore = new JSONObject();
        highscore.put("score", new Double(State.getScore()).toString());
        mainObject.put("highscore", highscore);

        return mainObject.toJSONString();
    }
}
