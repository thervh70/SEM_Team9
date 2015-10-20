package nl.tudelft.ti2206.group9.level.save;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.io.File;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver;

/**
 * This class takes care of all the saving and loading of the game.
 * @author Mathias
 */
public final class SaveGame {

    /** Private constructor. */
    private SaveGame() { }

    /**
     * Delegate the loading of the game to Parser class.
     * @param fileName the file to be loaded
     */
    public static void loadGame(final String fileName) {
        final String filePath = State.getDefaultSaveDir() + fileName + ".ses";
        Parser.loadGame(filePath);
    }

    /**
     * Delegate the saving of the game to Writer class.
     */
    public static void saveGame() {
        final String filePath = State.getDefaultSaveDir()
                + State.getPlayerName() + ".ses";
        Writer.saveGame(filePath);
    }

    /**
     * Read all names of the files in the default savegame directory
     * and store them in ObservableList players.
     */
    public static void readPlayerNames() {
        try {
            final File folder = new File(State.getDefaultSaveDir());
            for (final File file : folder.listFiles()) {
                if (file.isDirectory() || !checkExtention(file.getName())) {
                    continue;
                }
                final String fileName = removeExtension(file.getName());
                State.getSaveGames().add(fileName);
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
}
