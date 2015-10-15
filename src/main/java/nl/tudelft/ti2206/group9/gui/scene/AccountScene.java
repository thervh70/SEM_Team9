package nl.tudelft.ti2206.group9.gui.scene;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.popup.WarningPopup;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

/**
 * @author Mathias
 */
public class AccountScene extends AbstractMenuScene {

    /** Row in Grid of list. */
    private static final int LIST_ROW = 16;
    /** Colomn of the list position. */
    private static final int LIST_COLOMN = 4;
    /** Font size for input. */
    private static final int FONT_SIZE = 12;
    /** Height of the ListView. */
    private static final double LIST_HEIGHT = 160;

    /** Creating the listview used to display the list. */
    private static ListView<String> list = createList(LIST_COLOMN, LIST_ROW);
    /** The input field for the name of the player. */
    static final TextField INPUT = createTextField("PLAYER NAME", 2, 16);

    /**
     * Type of buttons that exist.
     */
    public enum BType {
        /** Load button. */
        ACC_LOAD,
        /** Newgame button. */
        ACC_NEW,
        /** Back button. */
        ACC_BACK
    }

    @Override
    public Node[] createContent() {
        setListContent();
        /** Create Buttons. */
        final Button newButton = createButton("NEW", 2, 18);
        final Button loadButton = createButton("LOAD", 4, 18);
        final Button backButton = createButton("BACK", 0, 18);
        list.setMinHeight(LIST_HEIGHT);
        /** Set Button Functions .*/
        setButtonFunction(loadButton, BType.ACC_LOAD);
        setButtonFunction(newButton, BType.ACC_NEW);
        setButtonFunction(backButton, BType.ACC_BACK);
        newButton.disableProperty().bind(
                Bindings.isEmpty(INPUT.textProperty()));
        final String text = setTextLabel();
        final Label textLabel = createLabel(text, 0, 16);
        /** Set tooltips. */
        loadButton.setTooltip(new Tooltip("Load an existing game"));
        newButton.setTooltip(new Tooltip("Create a new game"));
        backButton.setTooltip(new Tooltip("Back to Main Menu"));
        list.setTooltip(new Tooltip("Select player"));
        INPUT.setTooltip(new Tooltip("Enter your name"));
        INPUT.setFont(Style.getFont(FONT_SIZE));
        return new Node[]{backButton, loadButton, newButton, textLabel,
                INPUT, list};
    }

    /**
     * Set the function of a button.
     * @param button the button of which the function has to be set
     * @param type the type of the incoming button
     */
    protected static void setButtonFunction(final Button button,
                                            final BType type) {
        button.setOnAction(event -> {
            if (type == BType.ACC_LOAD) {
                OBSERVABLE.notify(Category.MENU, Menu.ACC_LOAD);
                SaveGame.loadGame(list.getSelectionModel().getSelectedItem());
                refreshContent();
            } else if (type == BType.ACC_NEW) {
                if (checkPlayerName(INPUT.getText())) {
                    OBSERVABLE.notify(Category.MENU, Menu.ACC_NEW);
                    State.resetAll();
                    State.setPlayerName(INPUT.getText());
                    SaveGame.saveGame();
                } else {
                    setPopup(new WarningPopup(event1 -> setPopup(null),
                            "The given name is invalid"));
                    ShaftEscape.showPopup(getPopup());
                }
            } else {
                OBSERVABLE.notify(Category.MENU, Menu.ACC_BACK);
                State.getSaveGames().clear();
                ShaftEscape.setScene(new MainMenuScene());
            }
        });
    }

    /**
     * Refresh the content of the scene.
     */
    private static void refreshContent() {
        State.getSaveGames().clear();
        final AbstractScene scene = ShaftEscape.getScene();
        scene.setRoot(scene.createRoot());
    }

    /**
     * Set the content of the list that should contain all saved accounts.
     */
    private static void setListContent() {
        SaveGame.readPlayerNames();
        list.setItems(State.getSaveGames());
        list.getSelectionModel().selectFirst();
    }

    /**
     * Create the playername text.
     * @return a String containing the playername status messages
     */
    private static String setTextLabel() {
        final StringBuilder builder = new StringBuilder();
        builder.append("");
        if (State.getPlayerName() == null) {
            builder.append("Guest");
        } else {
            builder.append(State.getPlayerName());
        }
        return builder.toString();
    }

    /**
     * Checks whether the playername is a valid name.
     * Invalid options:
     * <ul>
     *     <li>Empty name</li>
     *     <li>Name contains a '.'</li>
     *     <li>Name contains a '/'</li>
     *     <li>Name contains a '\'</li>
     * </ul>
     * @param name the name to be checked
     * @return boolean to indicate whether the name is valid
     */
    private static boolean checkPlayerName(final String name) {
        return !(name.contains(".") || name.contains("/")
                || name.contains("\\"));
    }
}
