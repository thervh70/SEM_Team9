package nl.tudelft.ti2206.group9.gui.scene;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.popup.WarningPopup;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.server.Highscore;
import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter;
import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter.ResultCallback;
import nl.tudelft.ti2206.group9.util.GameObserver;

/**
 * @author Maikel on 16/10/2015.
 */
@SuppressWarnings("restriction")
public class HighscoreScene extends AbstractMenuScene {

    /** Types of buttons. */
    enum BType {
        /** Back to main. */
        HIGHSCORES_BACK,
        /** Send local highscores to server and retrieve list of highscores. */
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
        input.setText("localhost");
    }

    /** List for displaying highscores. */
    private static ListView<String> scoreList = new ListView<>();
    /** List for getting highscores. */
    private static List<Highscore> highscoreList;
    /** ObservableList to edit when Highscores come in. */
    private static final ObservableList<String> SCORE_LIST =
            FXCollections.observableArrayList();

    /** Callback for not being able to connect. */
    private static final ResultCallback FAIL_CALLBACK = success -> {
        if (!success) {
            setPopup(new WarningPopup(e -> setPopup(null),
                    "Could not connect to " + input.getText() + "!"));
            ShaftEscape.showPopup(getPopup());
        }
    };
    /** Callback for getGlobal(10). */
    private static final ResultCallback FETCH_CALLBACK = success -> {
        Platform.runLater(() -> {
            SCORE_LIST.clear();
            if (success && highscoreList != null) {
                /** Looping over each Highscore in the list. */
                for (final Highscore hs : highscoreList) {
                    if (hs == null) {
                        break;
                    }
                    SCORE_LIST.add(hs.getUser() + "  -  " + hs.getScore());
                }
            } else {
                FAIL_CALLBACK.callback(success);
            }
        });
    };
    /** Callback for add(Name, Highscore). */
    private static final ResultCallback SEND_CALLBACK = success -> {
        if (success) {
            highscoreList = HighscoreClientAdapter.getGlobal(
                    SCORE_COUNT, FETCH_CALLBACK);
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

        updateButton.disableProperty().bind(
                Bindings.isEmpty(input.textProperty()));

        setButtonFunction(backButton, BType.HIGHSCORES_BACK);
        setButtonFunction(updateButton, BType.UPDATE);
        return new Node[]{createScoreList(), backButton,
                updateButton, input, highLabel};
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
     * Creating the score list.
     * @return Listview with scores.
     */
    public static ListView<String> createScoreList() {
        GridPane.setRowSpan(scoreList, LIST_ROW_SPAN);
        GridPane.setConstraints(scoreList, LIST_COLUMN, TABLE_ROW);
        GridPane.setColumnSpan(scoreList, TABLE_SPAN);
        scoreList.setMinHeight(SCORELIST_HEIGHT);
        scoreList.setFocusTraversable(false);
        scoreList.setItems(SCORE_LIST);
        return scoreList;
    }
}
