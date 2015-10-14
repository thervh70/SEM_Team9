package nl.tudelft.ti2206.group9.level.save;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

import org.json.simple.JSONObject;

import sun.misc.BASE64Encoder; //NOPMD - I need this package

/**
 * This class takes care of the writing of JSON
 * into external files. This way games can be saved.
 * @author Mathias
 */
@SuppressWarnings("restriction")
public final class Writer {

    /**
     * Private constructor.
     */
    private Writer() { }

    /**
     * Save a game by writing it to a JSON file.
     * @param path the path of the JSON file it has to be written to
     */
    static void saveGame(final String path) {
        final String mainObject = writeToJSON();
        final String encryptedMain = encrypt(mainObject);

        BufferedWriter fw = null;
        try {
            fw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "UTF-8"
            ));
            fw.write(encryptedMain);
            fw.flush();
        } catch (IOException e) {
            OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
                    "Writer.saveGame(String)", e.getMessage());
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
                        "Writer.saveGame(String) (2)", e.getMessage());
            }
        }
    }

    /**
     * Encrypt a given String.
     * @param input the String to be encrypted
     * @return the encrypted version of the input
     */
    static String encrypt(final String input) {
        final BASE64Encoder encoder = new BASE64Encoder();
        try {
            final byte[] utf8 = input.getBytes("UTF8");
            return encoder.encode(utf8);
        } catch (UnsupportedEncodingException e) {
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.UNSUPPORTEDENCODINGEXCEPTION,
                    "Writer.encrypt()", e.getMessage());
            return null;
        }
    }

    /**
     * Create a JSON string from all the data
     * which can be writte to a JSON file.
     * @return the JSON string
     */         // JSONObject.put uses HashMap, that gives unchecked warnings.
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
