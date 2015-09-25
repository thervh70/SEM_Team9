package nl.tudelft.ti2206.group9.gui;

import javafx.scene.control.*;
import nl.tudelft.ti2206.group9.ShaftEscape;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

/**
 * Ancestor for all MenuScenes. Subclasses should define an array of Nodes in
 * the abstract method {@link #createContent()}.
 * @author Maarten, Maikel
 */
@SuppressWarnings("restriction")
public abstract class MenuScene extends AbstractScene {

	protected static final int GRID_GAP = 20;
	protected static final int GRID_MARGIN = 10;
	protected static final int GRID_WIDTH = ShaftEscape.WIDTH / GRID_GAP;
	protected static final int GRID_HEIGHT = ShaftEscape.HEIGHT / GRID_GAP;

	/**
	 * Creating the SettingsScene.
	 * @return The root Node for this Scene.
	 */
	public Parent createRoot() {
	    GridPane grid = initializeGrid();
	
	    // Adding objects to grid.
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
		return "menuBackground.png";
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
	    Button button = new Button(name);
	    Style.setButtonStyle(button);
		button.setMinSize(120,10);
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
		Label label = new Label(text);
		Style.setLabelStyle(label);
		label.setMinSize(120,10);
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
        TextField tf = new TextField();
        tf.setPromptText(promptText);
        tf.setPrefSize(120, 10);
        GridPane.setConstraints(tf, column,row);
        return tf;
	}

	/**
	 * Create a listview.
	 * @param column Column index on Gridpane.
	 * @param row Row index on Gridpane.
	 * @return Returns the listview.
	 */
	protected static ListView createList(final int column, final int row) {
		final ListView list = new ListView();
		list.setEditable(true);
		GridPane.setConstraints(list, column, row);
		list.setPrefSize(80, 160);
		return list;
	}



}
