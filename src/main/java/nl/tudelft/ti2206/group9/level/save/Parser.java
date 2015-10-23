package nl.tudelft.ti2206.group9.level.save;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.io.IOException;
import java.io.InputStream;

import nl.tudelft.ti2206.group9.gui.skin.Skin;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Base64Reader;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;
import nl.tudelft.ti2206.group9.util.Resource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class takes care of the parsing of JSON objects
 * into the game. This way already saved games can be loaded.
 * @author Mathias
 */
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
            final InputStream stream = Resource.getStream(path);
            final Base64Reader reader = new Base64Reader(stream);
            final String mainString = reader.readString();
            final JSONParser parser = new JSONParser();
            final JSONObject mainObject =
                    (JSONObject) parser.parse(mainString);

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
     * Parse all json data from the file.
     * @param mainObject the main JSON object
     */
    private static void parseJSON(final JSONObject mainObject) {
        System.out.println(mainObject);
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

}
