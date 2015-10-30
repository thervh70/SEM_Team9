package nl.tudelft.ti2206.group9.gui.scene;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.popup.WarningPopup;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.server.Highscore;
import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter;
import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter.ResultCallback;
import nl.tudelft.ti2206.group9.util.GameObserver;

import java.util.List;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

/**
 * @author Maikel on 16/10/2015.
 */
public class HighscoreScene extends AbstractMenuScene {

    /** Types of buttons. */
    enum BType {
        /** Back to main. */
        HIGHSCORES_BACK,
        /**
         * Send local highscores to server and retrieve list of highscores.
         */
        UPDATE
    }

    /** Setting Table span for list. */
    private static final int TABLE_SPAN = 3;
    /** Set row for table. */
    private static final int TABLE_ROW = 14;
    /** Set height of list. */
    private static final int SCORELIST_HEIGHT = 150;
    /** Score list row span. */
    private static final int LIST_ROW_SPAN = 4;
    /** List column constraint. */
    private static final int LIST_COLUMN = 3;
    /** Back button row. */
    private static final int BACKB_ROW = 18;
    /** Send button row. */
    private static final int YOURS_ROW = 17;
    /** Send button row. */
    private static final int UPDATE_ROW = 16;
    /** Input row. */
    private static final int INPUT_ROW = 14;

    /** Fetch the top 10 scores. */
    private static final int SCORE_COUNT = 11;

    /** Text field for input. */
    private static TextField input = createTextField("HighscoreServer IP",
            0, INPUT_ROW);

    static {
        final int fontSize = 12;
        input.setText("localhost");
        input.setFont(Style.getFont(fontSize));
        input.setTooltip(new Tooltip("Enter the IP of the HighscoreServer"
                + " you want to connect to here."));
    }

    /**
     * List for getting global highscores.
     */
    private static List<Highscore> globalList;
    /**
     * List for getting local highscores.
     */
    private static List<Highscore> localList;
    /**
     * ObservableList to edit when Global Highscores come in.
     */
    private static final ObservableList<String> GLOBAL_LIST =
            FXCollections.observableArrayList();
    /**
     * ObservableList to edit when Local Highscores come in.
     */
    private static final ObservableList<String> LOCAL_LIST =
            FXCollections.observableArrayList();

    /** Callback for not being able to connect. */
    private static final ResultCallback FAIL_CALLBACK = success -> {
        if (!success) {
            setPopup(new WarningPopup(e -> setPopup(null),
                    "Could not connect to " + input.getText() + "!"));
            ShaftEscape.showPopup(getPopup());
        }
    };
    /**
     * Callback for getUser(10).
     */
    private static final ResultCallback USER_CALLBACK = success -> {
        Platform.runLater(() -> {
            LOCAL_LIST.clear();
            if (success && localList != null) {
                /** Looping over each Highscore in the list. */
                for (final Highscore hs : localList) {
                    if (hs == null) {
                        break;
                    }
                    LOCAL_LIST.add(hs.getUser() + "  -  " + hs.getScore());
                }
            } else {
                FAIL_CALLBACK.callback(success);
            }
        });
    };
    /** Callback for getGlobal(10). */
    private static final ResultCallback GLOBAL_CALLBACK = success -> {
        Platform.runLater(() -> {
            GLOBAL_LIST.clear();
            if (success && globalList != null) {
                /** Looping over each Highscore in the list. */
                for (final Highscore hs : globalList) {
                    if (hs == null) {
                        break;
                    }
                    GLOBAL_LIST.add(hs.getUser() + "  -  " + hs.getScore());
                }
                localList = HighscoreClientAdapter.getUser(
                        State.getPlayerName(), SCORE_COUNT, USER_CALLBACK);
            } else {
                FAIL_CALLBACK.callback(success);
            }
        });
    };
    /**
     * Callback for add(Name, Highscore).
     */
    private static final ResultCallback SEND_CALLBACK = success -> {
        if (success) {
            globalList = HighscoreClientAdapter.getGlobal(
                    SCORE_COUNT, GLOBAL_CALLBACK);
        }
        FAIL_CALLBACK.callback(success);
    };

    /**
     * Creates content for scene.
     *
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        final Button backButton = createButton("BACK", 0, BACKB_ROW);
        final Button updateButton = createButton("UPDATE", 0, UPDATE_ROW);
        final Label highLabel = createLabel("You: " + State.getHighscore(),
                0, YOURS_ROW);

        updateButton.setTooltip(new Tooltip("Press this button to fetch the"
                + " highscores from the server."));
        updateButton.disableProperty().bind(
                Bindings.isEmpty(input.textProperty()));

        setButtonFunction(backButton, BType.HIGHSCORES_BACK);
        setButtonFunction(updateButton, BType.UPDATE);
        return new Node[]{backButton, updateButton, input, createScorePane(),
                highLabel};
    }

    /**
     * This method sets the function of a button.
     *
     * @param button Button to be set.
     * @param type   Type of button
     */
    protected static void setButtonFunction(final Button button,
            final BType type) {
        button.setOnAction(event -> {
            playButtonSound();
            if (type == BType.HIGHSCORES_BACK) {
                HighscoreClientAdapter.disconnect();
                OBSERVABLE.notify(GameObserver.Category.MENU,
                        GameObserver.Menu.HIGHSCORES_BACK);
                ShaftEscape.setScene(new MainMenuScene());
            } else if (type == BType.UPDATE) {
                if (HighscoreClientAdapter.connect(input.getText())) {
                    HighscoreClientAdapter.add(State.getPlayerName(),
                            State.getHighscore(), SEND_CALLBACK);
                } else {
                    FAIL_CALLBACK.callback(false);
                }
            }
        });
    }

    /**
     * @return a TabPane with two tabs: global list and user list.
     */
    public static TabPane createScorePane() {
        final TabPane tabPane = new TabPane();
        GridPane.setRowSpan(tabPane, LIST_ROW_SPAN);
        GridPane.setConstraints(tabPane, LIST_COLUMN, TABLE_ROW);
        GridPane.setColumnSpan(tabPane, TABLE_SPAN);
        tabPane.setMinHeight(SCORELIST_HEIGHT);

        final Tab globalTab = new Tab("Global");
        globalTab.setClosable(false);
        globalTab.setContent(createScoreList(GLOBAL_LIST));
        globalTab.setTooltip(new Tooltip("This tab displays the best highscores"
                + " of everyone on the server."));

        final Tab localTab = new Tab("Local");
        localTab.setClosable(false);
        localTab.setContent(createScoreList(LOCAL_LIST));
        localTab.setTooltip(new Tooltip("This tab displays the best scores"
                + " that you have submitted to the server."));

        tabPane.getTabs().addAll(globalTab, localTab);
        return tabPane;
    }

    /**
     * Creating the score list.
     * @param list list to put in the listView.
     * @return Listview with scores.
     */
    public static ListView<String> createScoreList(
            final ObservableList<String> list) {
        final ListView<String> scoreList = new ListView<>();
        scoreList.setFocusTraversable(false);
        scoreList.setItems(list);
        return scoreList;
    }

    /**
     * Override background, the Highscore background shows "Highscore".
     */
    @Override
    public String getBackgroundPath() {
        return "highscoreBackground.png";
    }
}
