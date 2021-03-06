package nl.tudelft.ti2206.group9.gui.scene;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.audio.SoundEffectPlayer;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.State;

/**
 * Ancestor for all MenuScenes. Subclasses should define an array of Nodes in
 * the abstract method {@link #createContent()}.
 * @author Maarten, Maikel, Mitchell
 */
public abstract class AbstractMenuScene extends AbstractScene {

    /** Margin of grid cells. */
    private static final int GRID_MARGIN = 10;
    /** Gap between grid cells. */
    protected static final int GRID_GAP = 20;
    /** Amount of columns in grid. */
    protected static final int GRID_WIDTH = ShaftEscape.WIDTH / GRID_GAP;
    /** Amount of rows in grid. */
    protected static final int GRID_HEIGHT = ShaftEscape.HEIGHT / GRID_GAP;

    /** Width for label and text input.*/
    private static final int CELL_WIDTH = 120;
    /** Height for label and text input.*/
    private static final int CELL_HEIGHT = 20;
    /** WIDTH for list. */
    private static final int LIST_WIDTH = 80;
    /** HEIGHT for list. */
    private static final int LIST_HEIGHT = 160;

    /** The AudioPlayer to be used for a button sound effect. */
    private static final SoundEffectPlayer BUTTON_SOUND = new SoundEffectPlayer(
            "nl/tudelft/ti2206/group9/audio/button.wav");

    /**
     * Creating the SettingsScene.
     * @return The root Node for this Scene.
     */
    public Parent createRoot() {
        final GridPane grid = initializeGrid();
        grid.getChildren().addAll(createContent());
        return grid;
    }

    /**
     * @return a list of Nodes which should be added to the Root (which is a
     * {@link GridPane}).
     */
    public abstract Node[] createContent();

    /**
     * This method creates the gridPane which is used for the layout.
     * @return grid that is going to be used.
     */
    private GridPane initializeGrid() {
        final GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(
                GRID_MARGIN, GRID_MARGIN, GRID_MARGIN, GRID_MARGIN));
        grid.setVgap(GRID_GAP);
        grid.setHgap(GRID_GAP);
        Style.setBackground(getBackgroundPath(), grid);
        return grid;
    }

    /**
     * @return the path to the background image (located in the gui package).
     */
    public String getBackgroundPath() {
        return "standardBackground.png";
    }

    /**
     * This method adds text to buttons and gives them a location on the grid.
     * @param name Name of the button.
     * @param column Column index on the grid.
     * @param row Row index on the grid.
     * @return the created button.
     */
    protected static Button createButton(final String name,
            final int column, final int row) {
        final Button button = new Button(name);
        Style.setButtonStyle(button);
        GridPane.setConstraints(button, column, row);
        return button;
    }

    /**
     * This method creates labels and gives them a location on the grid.
     * @param text Label text.
     * @param column Column index on the grid.
     * @param row Row index on the grid.
     * @return the created Label.
     */
    protected static Label createLabel(final String text,
                                       final int column, final int row) {
        final Label label = new Label(text);
        Style.setLabelStyle(label);
        label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
        GridPane.setConstraints(label, column, row);
        return label;
    }

    /**
     * Create a new textfield.
     * @param promptText PromtText for the textfield.
     * @param column Column index for gridPane.
     * @param row Row index for gridPane.
     * @return The created textField.
     */
    protected static TextField createTextField(
            final String promptText, final int column, final int row) {
        final TextField tf = new TextField();
        tf.setPromptText(promptText);
        tf.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        GridPane.setConstraints(tf, column, row);
        return tf;
    }

    /**
     * Create a listview.
     * @param column Column index on Gridpane.
     * @param row Row index on Gridpane.
     * @return Returns the listview. */
    protected static ListView<String> createList(final int column,
                                                 final int row) {
        final ListView<String> list = new ListView<>();
        list.setEditable(true);
        GridPane.setConstraints(list, column, row);
        list.setPrefSize(LIST_WIDTH, LIST_HEIGHT);
        return list;
    }

    /**
     * Create a Label in the left-upper corner with the name of
     * the current selected account in it. Value is 'Guest' when no
     * account is selected.
     * @return the Label containing the players name
     */
    protected static Label getPlayerLabelContent() {
        String name = "";
        if (State.getPlayerName() == null) {
            name = "Guest";
        } else {
            name = State.getPlayerName();
        }
        final Label res = createLabel(name, 0, 0);
        GridPane.setRowSpan(res, 2);
        return res;
    }

    /**
     * Plays the button sound once, given a volumelevel.
     */
    protected static void playButtonSound() {
        BUTTON_SOUND.setVolume(State.getSoundEffectVolume());
        BUTTON_SOUND.play();
    }

}
