package nl.tudelft.ti2206.group9.level.save;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.ti2206.group9.gui.skin.Skin;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sun.misc.BASE64Decoder; //NOPMD - I need this package

/**
 * This class takes care of the parsing of JSON objects
 * into the game. This way already saved games can be loaded.
 * @author Mathias
 */
@SuppressWarnings("restriction")
public final class Parser {

    /** Playername. */
    private static String playername;
    /** Number of coins.*/
    private static long coins;
    /** Players highscore. */
    private static long highScore;
    /** Boolean to indicate if skins are unlocked. */
    private static boolean andy, captain, boy, plank, iron;
    /**
     * Boolean for sound track enabled.
     */
    private static boolean soundtrackEnabled;
    /**
     * Boolean to indicate whether the sound is enabled.
     */
    private static boolean soundEffectsEnabled;

    /**
     * Private constructor.
     */
    private Parser() { }

    /**
     * Read a json savefile and store all data in the State class.
     * @param path path to the file to be parsed.
     */
    static void loadGame(final String path) {
        try {
            final List<String> lines  = new ArrayList<>();
            final URL pathURL = new File(path).toURI().toURL();
            final InputStream stream = pathURL.openStream();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, "UTF-8"));

            while (reader.ready()) {
                lines.add(reader.readLine());
            }
            final String mainString = createString(lines);
            final String decryptedMain = decrypt(mainString);
            final JSONParser parser = new JSONParser();
            final JSONObject mainObject =
                    (JSONObject) parser.parse(decryptedMain);

            parseJSON(mainObject);
            writeToState();
            writeToSkins();
            reader.close();
        } catch (IOException e) {
            OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
                    "Parser.loadGame(String)", e.getMessage());
        } catch (ParseException e) {
            OBSERVABLE.notify(Category.ERROR, Error.PARSEEXCEPTION,
                    "Parser.loadGame(String)", e.getMessage());
        }
    }

    /**
     * Decrypt a given String.
     * @param input the String to be decrypted
     * @return the decrypted version of the input
     */
    static String decrypt(final String input) {
        final BASE64Decoder decoder = new BASE64Decoder();
        try {
            final byte[] decodedBytes = decoder.decodeBuffer(input);
            return new String(decodedBytes, "UTF8");
        } catch (IOException e) {
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.IOEXCEPTION,
                    "Parser.decode()", e.getMessage());
            return null;
        }
    }

    /**
     * Parse all json data from the file.
     * @param mainObject the main JSON object
     */
    private static void parseJSON(final JSONObject mainObject) {
        playername = (String) mainObject.get("playername");
        coins = (Long) mainObject.get("coins");

        final JSONObject settingsObj =
                (JSONObject) mainObject.get("settings");

        final JSONObject soundtrObj =
                (JSONObject) settingsObj.get("soundtracksettings");
        soundtrackEnabled = (Boolean) soundtrObj.get("soundtrackEnabled");

        final JSONObject soundEfObj =
                (JSONObject) settingsObj.get("soundEffectssettings");
        soundEffectsEnabled = (Boolean) soundEfObj.get("soundEffectsEnabled");

        final JSONObject highObj = (JSONObject) mainObject.get("highscore");
        highScore = (Long) highObj.get("score");

        andy = (boolean) mainObject.get("andy");
        boy = (boolean) mainObject.get("boy");
        captain = (boolean) mainObject.get("captain");
        iron = (boolean) mainObject.get("iron");
        plank = (boolean) mainObject.get("plank");
    }

    /**
     * Write all data to the State class.
     */
    private static void writeToState() {
        State.setPlayerName(playername);
        State.setCoins((int) coins);
        State.setSoundtrackEnabled(soundtrackEnabled);
        State.setSoundEffectsEnabled(soundEffectsEnabled);
        State.setHighscore(highScore);
    }

    /**
     * Write states of skins to Style.
     */
    private static void writeToSkins() {
        Skin.setUnlocked("Andy", andy);
        Skin.setUnlocked("B-man", boy);
        Skin.setUnlocked("Captain", captain);
        Skin.setUnlocked("Iron Man", iron);
        Skin.setUnlocked("Plank", plank);
    }

    /**
     * Create a single String from a list of Strings.
     * @param lines the list of Strings
     * @return a single String
     */
    private static String createString(final List<String> lines) {
        final StringBuilder builder = new StringBuilder();
        lines.forEach(builder::append);
        return builder.toString();
    }

}
