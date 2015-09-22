package nl.tudelft.ti2206.group9.gui;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * @author Maarten
 *
 */
@SuppressWarnings("restriction")
public abstract class MenuScene extends AbstractScene {

	protected static final int GRID_GAP = 20;
	protected static final int GRID_MARGIN = 10;
	protected static final int GRID_WIDTH = GUIConstant.WIDTH / GRID_GAP;
	protected static final int GRID_HEIGHT = GUIConstant.HEIGHT / GRID_GAP;

	/**
	 * Creating the SettingsScene.
	 */
	public Parent createRoot() {
	    GridPane grid = initializeGrid();
	
	    // Adding buttons to grid.
	    grid.getChildren().addAll(createContent());
	    
		return grid;
	}

	abstract Node[] createContent();

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

	abstract String getBackgroundPath();

	/**
	 * This method adds text to buttons and give them a location on the grid.
	 * @param name Name of the button.
	 * @param column Column index on the grid.
	 * @param row Row index on the grid.
	 * @return the created button.
	 */
	protected static Button createButton(final String name,
			final int column, final int row) {
	    Button button = new Button(name);
	    Style.setButtonStyle(button);
	    GridPane.setConstraints(button, column, row);
		return button;
	}

}
