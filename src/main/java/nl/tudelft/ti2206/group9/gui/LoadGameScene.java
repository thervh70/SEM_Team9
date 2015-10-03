package nl.tudelft.ti2206.group9.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver;
import javafx.scene.control.ListView;
import nl.tudelft.ti2206.group9.util.SaveGameParser;

import java.io.File;

/**
 * Scene that displays a list of previous player names,
 * from which you can select one to continue playing.
 * Created by Maikel on 24/09/2015.
 */
@SuppressWarnings("restriction")
public class LoadGameScene extends AbstractMenuScene {

    /** Row in Grid of list. */
    private static final int LIST_ROW = 16;

    /** Creating the list. */
    private static ObservableList<String> players =
            FXCollections.observableArrayList();
    /** Creating the listview used to display the list. */
    private static ListView<String> list = createList(2, LIST_ROW);

    /**
     * Type of buttons that exist.
     */
    enum BType {
        /** Back button. */
        LOAD_BACK,
        /** Button to load a game. */
        LOAD_START
    }

    /**
     * Returns the list of players.
     * @return List of players.
     */
    public static ObservableList<String> getPlayers() {
        return players;
    }

    /**
     * Returns the list view itself.
     * @return a list.
     */
    public static ListView<String> getList() {
        return list;
    }

    /**
     * Creates the buttons.
     * @return array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        readPlayerNames();
        list.setItems(players);
        final Button backButton = createButton("BACK", 0, 20);
        final Button loadButton = createButton("LOAD & START!", 2, 20);
        /** Set button functions. */
        setButtonFunction(backButton, BType.LOAD_BACK);
        setButtonFunction(loadButton, BType.LOAD_START);
        /** Set Tooltips. */
        backButton.setTooltip(new Tooltip("Back to main menu"));
        loadButton.setTooltip(new Tooltip("Start!"));
        list.setTooltip(new Tooltip("Select player"));
        return new Node[]{backButton, loadButton, list};
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    protected static void setButtonFunction(final Button button,
                                            final BType type) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                if (type == BType.LOAD_BACK) {
                    GameObservable.notify(GameObserver.Category.MENU,
                            GameObserver.Menu.LOAD_BACK);
                    players.clear();
                    ShaftEscape.setScene(new MainMenuScene());
                } else {
                    GameObservable.notify(GameObserver.Category.MENU,
                            GameObserver.Menu.LOAD);
                    final String loadFile =
                            list.getSelectionModel().getSelectedItem();
                    if (loadFile == null) {
                        System.out.println("This really needs a Popup");
                    } else {
                        SaveGameParser.loadGame(State.getDefaultSaveDir()
                                + loadFile + ".json");
                        State.setPlayerName(loadFile);
                        players.clear();
                        ShaftEscape.setScene(new GameScene());
                    }
                }
            }
        });
    }

    /**
     * Read all names of the files in the default savegame directory
     * and store them in ObservableList players.
     */
    private void readPlayerNames() {
        final File folder = new File(State.getDefaultSaveDir());
        for (final File file : folder.listFiles()) {
            if (file.isDirectory()) {
                continue;
            }
            final String fileName = removeExtension(file.getName());
            players.add(fileName);
        }
    }

    /**
     * Removes the extension from a filename.
     * @param file the file from which the extension should be removed
     * @return a String containing the trimmed filename
     */
    private String removeExtension(final String file) {
        if (file.lastIndexOf('.') == -1) {
            return file;
        }
        return file.substring(0, file.lastIndexOf('.'));
    }

}


