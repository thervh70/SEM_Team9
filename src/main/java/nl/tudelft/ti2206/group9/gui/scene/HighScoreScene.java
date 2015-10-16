package nl.tudelft.ti2206.group9.gui.scene;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.util.GameObserver;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

/**
 * @author Maikel on 16/10/2015.
 */
public class HighScoreScene extends AbstractMenuScene {

    /** Types of buttons. */
    enum BType {
        /** Back to main. */
        HIGHSCORES_BACK,
        /** Send local highscores to server. */
        SEND,
        /** Fetch highscores from server. */
        FETCH
    }

    /**
     * Setting Table span for list.
     */
    private static final int TABLE_SPAN = 3;
    /**
     * Set row for table.
     */
    private static final int TABLE_ROW = 14;
    /**
     * Set height of list.
     */
    private static final int SCORELIST_HEIGHT = 150;
    /** Score list row span. */
    private static final int LIST_ROW_SPAN = 4;
    /** List column constraint. */
    private static final int LIST_COLUMN = 3;
    /**
     * Back button row.
     */
    private static final int BACKB_ROW = 18;
    /** Send button row. */
    private static final int SEND_ROW = 16;
    /** Fetch button row. */
    private static final int FETCH_ROW = 15;
    /** Input row. */
    private static final int INPUT_ROW = 14;
    /** Text field for input. */
    private static TextField input = createTextField("INPUT URL", 0, INPUT_ROW);
    /**
     * List for displaying highscores.
     */
    private static ListView<String> scoreList = new ListView<>();

    /**
     * Creates content for scene.
     *
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        Button backButton = createButton("BACK", 0, BACKB_ROW);
        Button sendButton = createButton("SEND", 0, SEND_ROW);
        Button fetchButton = createButton("FETCH SCORES", 0 , FETCH_ROW);

        sendButton.disableProperty().bind(
                Bindings.isEmpty(input.textProperty()));
        fetchButton.disableProperty().bind(
                Bindings.isEmpty(input.textProperty()));

        setButtonFunction(backButton, BType.HIGHSCORES_BACK);
        return new Node[]{createScoreList(), backButton,
                sendButton, fetchButton, input};
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
            if (type == BType.HIGHSCORES_BACK) {
                OBSERVABLE.notify(GameObserver.Category.MENU,
                        GameObserver.Menu.HIGHSCORES_BACK);
                ShaftEscape.setScene(new MainMenuScene());
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

//        List<Highscores.Highscore> highscoreList =
//              Highscores.get(SCORE_COUNT);
//
//
//        ObservableList<String> observableScoreList =
//                FXCollections.observableArrayList();
//
//        //numberColumn.setCellValueFactory(State, State.getHighscore());
//        for (Highscores.Highscore hs : highscoreList) {
//            String score = hs.getUser() + "  -  "
//                    + Integer.toString(hs.getScore());
//            observableScoreList.add(score);
//        }
//        scoreList.setItems(observableScoreList);
        return scoreList;
    }
}
