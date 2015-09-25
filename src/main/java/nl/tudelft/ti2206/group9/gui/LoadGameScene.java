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

/**
 * Scene that displays a list of previous player names,
 * from which you can select one to continue playing.
 * Created by Maikel on 24/09/2015.
 */
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
     * @return Returns a list.
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
                        ShaftEscape.setScene(new MainMenuScene());
                    } else {
                        GameObservable.notify(GameObserver.Category.MENU,
                                GameObserver.Menu.LOAD);
                        State.setPlayerName(
                                list.getSelectionModel().getSelectedItem());
                        ShaftEscape.setScene(new GameScene());
                    }
                        }
            });
        }

}

