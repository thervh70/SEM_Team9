package nl.tudelft.ti2206.group9.gui.scene;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.popup.WarningPopup;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

import java.io.File;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

/**
 * @author Mathias
 */
public class AccountScene extends AbstractMenuScene {

    /** Row in Grid of list. */
    private static final int LIST_ROW = 16;
    /** Colomn of the list position. */
    private static final int LIST_COLOMN = 2;
    /** Font size for input. */
    private static final int FONT_SIZE = 12;
    /** Height of the ListView. */
    private static final double LIST_HEIGHT = 160;

    /** Creating the listview used to display the list. */
    private static ListView<String> list = createList(LIST_COLOMN, LIST_ROW);
    /** The input field for the name of the player. */
    static final TextField INPUT = createTextField("PLAYER NAME", 0, 16);

    /**
     * Type of buttons that exist.
     */
    public enum BType {
        /** Load button. */
        ACC_LOAD,
        /** Newgame button. */
        ACC_NEW,
        /** Delete button. */
        ACC_DEL
    }

    @Override
    public Node[] createContent() {
        setListContent();
        /** Create Buttons. */
        final Button newButton = createButton("NEW", 0, 18);
        final Button loadButton = createButton("LOAD", 2, 18);
        final Button deleteButton = createButton("DELETE", 4, 18);
        list.setMinHeight(LIST_HEIGHT);
        list.setPlaceholder(new Text("No content"));
        /** Set Button Functions .*/
        setButtonFunction(loadButton, BType.ACC_LOAD);
        setButtonFunction(newButton, BType.ACC_NEW);
        setButtonFunction(deleteButton, BType.ACC_DEL);
        newButton.disableProperty().bind(
                Bindings.isEmpty(INPUT.textProperty()));
        loadButton.disableProperty().bind(Bindings.isEmpty(list.getItems()));
        /** Set tooltips. */
        loadButton.setTooltip(new Tooltip("Load an existing game"));
        newButton.setTooltip(new Tooltip("Create a new game"));
        deleteButton.setTooltip(new Tooltip("Delete this account"));
        list.setTooltip(new Tooltip("Select player"));
        INPUT.setTooltip(new Tooltip("Enter your name"));
        INPUT.setFont(Style.getFont(FONT_SIZE));
        return new Node[]{loadButton, newButton, deleteButton, INPUT, list};
    }

    /**
     * Set the function of a button.
     * @param button the button of which the function has to be set
     * @param type the type of the incoming button
     */
    protected void setButtonFunction(final Button button,
                                     final BType type) {
        button.setOnAction(event -> {
            playButtonSound();
            if (type == BType.ACC_LOAD) {
                if (list.getSelectionModel().getSelectedItem() == null) {
                    showWarningPopup("Select an account to load");
                } else {
                    OBSERVABLE.notify(Category.MENU, Menu.ACC_LOAD);
                    SaveGame.loadGame(
                            list.getSelectionModel().getSelectedItem());
                    goToMainMenuScene();
                }
            } else if (type == BType.ACC_NEW) {
                if (checkPlayerName(INPUT.getText())) {
                    createNewAccount(INPUT.getText());
                    fadeoutAnimation();
                } else {
                    showWarningPopup("The given name is either invalid\n"
                                    + "or already exists");
                }
            } else {
                final String selected = list.getSelectionModel().
                        getSelectedItem();
                new File(SaveGame.getDefaultSaveDir() + selected + ".ses")
                            .delete();
                refreshContent();
            }
        });
    }

    /**
     * Generate a fade transition to the {@link IntroScene}.
     * Only used in case of a new user.
     */
    private void fadeoutAnimation() {
        setFill(Color.BLACK);
        this.getRoot().setDisable(true);

        final Timeline timeline = new Timeline();
        final int duration = 2000;
        final KeyFrame key = new KeyFrame(Duration.millis(duration),
                new KeyValue(this.getRoot().opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> {
            ShaftEscape.setScene(new IntroScene());
            clearAccountScene();
        });
        timeline.play();
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
     * Clear the saved of the AccountScene and switch to the MainMenuScene.
     */
    private static void goToMainMenuScene() {
        clearAccountScene();
        ShaftEscape.setScene(new MainMenuScene());
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

    /**
     * Show a warningPopUp with a given message.
     * Default event calls setPopup with parameter null, which hides the Popup.
     * @param message the message to be displayed
     */
    private static void showWarningPopup(final String message) {
        setPopup(new WarningPopup(event1 -> setPopup(null), message));
        ShaftEscape.showPopup(getPopup());
    }

    /** Override background, the Account background shows "Accounts". */
    @Override
    public String getBackgroundPath() {
        return "accountsBackground.png";
    }
}
