package nl.tudelft.ti2206.group9.gui.scene;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.popup.WarningPopup;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;
import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

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
        ACC_NEW
    }

    @Override
    public Node[] createContent() {
        setListContent();
        /** Create Buttons. */
        final Button newButton = createButton("NEW", 2, 18);
        final Button loadButton = createButton("LOAD", 4, 18);
        list.setMinHeight(LIST_HEIGHT);
        list.setPlaceholder(new Text("No content"));
        /** Set Button Functions .*/
        setButtonFunction(loadButton, BType.ACC_LOAD);
        setButtonFunction(newButton, BType.ACC_NEW);
        newButton.disableProperty().bind(
                Bindings.isEmpty(INPUT.textProperty()));
        loadButton.disableProperty().bind(Bindings.isEmpty(list.getItems()));
        /** Set tooltips. */
        loadButton.setTooltip(new Tooltip("Load an existing game"));
        newButton.setTooltip(new Tooltip("Create a new game"));
        list.setTooltip(new Tooltip("Select player"));
        INPUT.setTooltip(new Tooltip("Enter your name"));
        INPUT.setFont(Style.getFont(FONT_SIZE));
        return new Node[]{loadButton, newButton, INPUT, list};
    }

    /**
     * Set the function of a button.
     * @param button the button of which the function has to be set
     * @param type the type of the incoming button
     */
    protected static void setButtonFunction(final Button button,
                                            final BType type) {
        button.setOnAction(event -> {
            playButtonSound();
            if (type == BType.ACC_LOAD) {
                if (list.getSelectionModel().getSelectedItem() == null) {
                    setPopup(new WarningPopup(event1 -> setPopup(null),
                            "Select an account to load"));
                    ShaftEscape.showPopup(getPopup());
                } else {
                    OBSERVABLE.notify(Category.MENU, Menu.ACC_LOAD);
                    SaveGame.loadGame(
                            list.getSelectionModel().getSelectedItem());
                    clearAccountScene();
                    ShaftEscape.setScene(new MainMenuScene());
                }
            } else if (type == BType.ACC_NEW) {
                if (checkPlayerName(INPUT.getText())) {
                    createNewAccount(INPUT.getText());
                    clearAccountScene();
                    ShaftEscape.setScene(new MainMenuScene());
                } else {
                    setPopup(new WarningPopup(event1 -> setPopup(null),
                            "The given name is either invalid\n"
                                    + "or already exists"));
                    ShaftEscape.showPopup(getPopup());
                }
            }
        });
    }

    /**
     * Refresh the content of the scene.
     */
    private static void refreshContent() {
        SaveGame.getSaveGames().clear();
        final AbstractScene scene = ShaftEscape.getScene();
        scene.setRoot(scene.createRoot());
    }

    /**
     * Clear all textfields and lists in AccountScene.
     */
    private static void clearAccountScene() {
        SaveGame.getSaveGames().clear();
        INPUT.clear();
    }

    /**
     * Set the content of the list that should contain all saved accounts.
     */
    private static void setListContent() {
        SaveGame.readPlayerNames();
        list.setItems(SaveGame.getSaveGames());
        list.getSelectionModel().selectFirst();
    }

    /**
     * Create a new account with the given name.
     * @param name the name of the new account
     */
    private static void createNewAccount(final String name) {
        OBSERVABLE.notify(Category.MENU, Menu.ACC_NEW);
        State.resetAll();
        State.setPlayerName(name);
        SaveGame.saveGame();
        refreshContent();
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
                || name.contains("\\") || alreadyExists(name));
    }

    /**
     * Checks whether there already exists a file with the given name.
     * @param name name to be checked
     * @return a boolean to indicate whether the name already exists
     */
    private static boolean alreadyExists(final String name) {
        return SaveGame.getSaveGames().contains(name);
    }
}
