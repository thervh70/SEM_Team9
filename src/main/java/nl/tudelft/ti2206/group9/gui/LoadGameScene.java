package nl.tudelft.ti2206.group9.gui;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver;
import javafx.scene.control.ListView;

import javafx.scene.input.MouseEvent;
import nl.tudelft.ti2206.group9.util.SaveGame;


/**
 * Scene that displays a list of previous player names,
 * from which you can select one to continue playing.
 * Created by Maikel on 24/09/2015.
 */
@SuppressWarnings("restriction")
public class LoadGameScene extends AbstractMenuScene {

    /** Row in Grid of list. */
    private static final int LIST_ROW = 16;

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
        SaveGame.readPlayerNames();
        list.setItems(State.getSaveGames());
        list.getSelectionModel().selectFirst();
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
                    OBSERVABLE.notify(GameObserver.Category.MENU,
                            GameObserver.Menu.LOAD_BACK);
                    State.getSaveGames().clear();
                    ShaftEscape.setScene(new MainMenuScene());
                } else {
                    OBSERVABLE.notify(GameObserver.Category.MENU,
                            GameObserver.Menu.LOAD);
                    final String loadFile =
                            list.getSelectionModel().getSelectedItem();
                    if (loadFile == null) {
                        setPopup(new WarningPopup(
                                new EventHandler<MouseEvent>() {
                                    public void handle(final MouseEvent event) {
                                        setPopup(null);
                                    }
                                }, "Please select a valid file!"));
                        ShaftEscape.showPopup(getPopup());
                    } else {
                        loadGame(loadFile);
                    }
                }
            }
        });
    }

    /**
     * Load an existing game with the given name.
     * @param loadFile the name of the game to be loaded
     */
    private static void loadGame(final String loadFile) {
//        if (State.getPlayerName() != null) {
//            SaveGame.saveGame();
//        }
        SaveGame.loadGame(loadFile);
        State.getSaveGames().clear();
        ShaftEscape.setScene(new GameScene());
    }

}


