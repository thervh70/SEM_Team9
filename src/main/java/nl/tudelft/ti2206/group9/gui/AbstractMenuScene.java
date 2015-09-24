package nl.tudelft.ti2206.group9.gui;

import nl.tudelft.ti2206.group9.ShaftEscape;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Ancestor for all MenuScenes. Subclasses should define an array of Nodes in
 * the abstract method {@link #createContent()}.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public abstract class AbstractMenuScene extends AbstractScene {

	/** Gap between grid cells. */
	protected static final int GRID_GAP = 20;
	/** Margin of grid cells. */
	protected static final int GRID_MARGIN = 10;
	/** Amount of columns in grid. */
	protected static final int GRID_WIDTH = ShaftEscape.WIDTH / GRID_GAP;
	/** Amount of rows in grid. */
	protected static final int GRID_HEIGHT = ShaftEscape.HEIGHT / GRID_GAP;

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
		return "menuBackground.png";
	}

	/**
	 * This method adds text to buttons and give them a location on the grid.
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

}
