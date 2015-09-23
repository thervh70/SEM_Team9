package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Mathias
 */
public class SaveGameWriter {

    public static void main(String[] args) {
        State.setCoins(42);

        saveGame("src/main/resources/nl/tudelft/ti2206/group9/util/testWrite.json");
    }

    public static void saveGame(String filePath) {
        JSONObject mainObject = new JSONObject();
        //mainObject.put("playername", State.getPlayerName());
        mainObject.put("coins", new Integer(State.getCoins()));

        try {
            FileWriter writer = new FileWriter(filePath);

            writer.write(mainObject.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
