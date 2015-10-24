package nl.tudelft.ti2206.group9.level.save;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver;

import java.io.File;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

/**
 * This class takes care of all the saving and loading of the game.
 * @author Mathias
 */
@SuppressWarnings("restriction") // SuppressWarnings, because
// the ObservableList is for maintaining a list of saved games.
public final class SaveGame {

    /**
     * Default savegame directory.
     * Attention! Only use 1 subfolder!
     */
    private static String defaultSaveDir = "sav/";
    /** List of the names of all the saved games. */
    private static ObservableList<String> saveGames =
            FXCollections.observableArrayList();

    /** Private constructor. */
    private SaveGame() { }

    /**
     * Delegate the loading of the game to Parser class.
     * @param fileName the file to be loaded
     */
    public static void loadGame(final String fileName) {
        final String filePath = getDefaultSaveDir() + fileName + ".ses";
        Parser.loadGame(filePath);
    }

    /**
     * Delegate the saving of the game to Writer class.
     */
    public static void saveGame() {
        final String filePath = getDefaultSaveDir()
                + State.getPlayerName() + ".ses";
        Writer.saveGame(filePath);
    }

    /**
     * Read all names of the files in the default savegame directory
     * and store them in ObservableList players.
     */
    public static void readPlayerNames() {
        try {
            final File folder = new File(getDefaultSaveDir());
            for (final File file : folder.listFiles()) {
                if (file.isDirectory() || !checkExtention(file.getName())) {
                    continue;
                }
                final String fileName = removeExtension(file.getName());
                getSaveGames().add(fileName);
            }
        } catch (NullPointerException e) { //NOPMD
            //This PMD check is to make FindBugs happy
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SaveGame.readPlayerNames()", e.getMessage());
        }
    }

    /**
     * Checks whether the extension of a file is .ses.
     * @param file The file of which the extension is checked
     * @return a boolean to idicate whether the extension equals .ses
     */
    private static boolean checkExtention(final String file) {
        final String ses = file.substring(file.lastIndexOf('.'));
        return ses.equals(".ses");
    }

    /**
     * Removes the extension from a filename.
     * @param file the file from which the extension should be removed
     * @return a String containing the trimmed filename
     */
    private static String removeExtension(final String file) {
        if (file.lastIndexOf('.') == -1) {
            return file;
        }
        return file.substring(0, file.lastIndexOf('.'));
    }

    /**
     * Get the default savegame directory.
     * @return defaultSaveDir
     */
    public static String getDefaultSaveDir() {
        return defaultSaveDir;
    }

    /**
     * Set a new default savegame directory.
     * @param newSaveDir the new savegame directory
     */
    public static void setDefaultSaveDir(final String newSaveDir) {
        defaultSaveDir = newSaveDir;
    }

    /**
     * Get the list of the names of all the saved games.
     * @return ObservableList which contains all names of the saved games
     */
    public static ObservableList<String> getSaveGames() {
        return saveGames;
    }
}
