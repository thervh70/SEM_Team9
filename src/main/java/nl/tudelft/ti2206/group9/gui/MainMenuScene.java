package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

/**
 * @author Maikel, Mitchell and Robin
 *
 * A startscreen with different options/buttons like a options menu, startbutton
 * and exit button.
 */
@SuppressWarnings("restriction")
public final class MainMenuScene extends AbstractScene {

	/**
	 * Type of buttons that exist.
	 */
	private enum BType {
		/** Exit button. */
		EXIT,
		/** Start button. */
		START,
		/** Settings button. */
		SETTINGS
	 }

    /**
     * Creating the MainMenuScene.
     */
    public Parent createRoot() {
        GridPane grid = initializeGrid();

        final Button startButton = createButton("START", 6, 26);
        final Button settingsButton = createButton("SETTINGS", 2, 26);
        final Button exitButton = createButton("EXIT", 10, 26);

        // Adding all buttons to the gridpane.
        grid.getChildren().addAll(startButton, settingsButton, exitButton);

        setButtonFunction(exitButton, BType.EXIT);
        setButtonFunction(startButton, BType.START);
        setButtonFunction(settingsButton, BType.SETTINGS);
        
        return grid;
    }

	/**
     * This method creates the gridPane which is used for the layout.
     * @return grid that is going to be used.
     */
    private static GridPane initializeGrid() {
        final GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        grid.setVgap(20);
        grid.setHgap(20);
        Style.setBackground("sc.png", grid);
        return grid;
    }

    /**
     * This method adds text to buttons and give them a location on the grid.
     * @param name Name of the button.
     * @param colum Colum index on the grid.
     * @param row Row index on the grid.
     * @return the created button.
     */
    private static Button createButton(final String name, final int colum, 
    									final int row) {
        Button button = new Button(name);
        Style.setButtonStyle(button);
        GridPane.setConstraints(button, colum, row);
		return button;
	}

    /**
     * This method sets the function of a button.
     * @param stage given PrimaryStage.
     * @param button Button to be set.
     * @param type Type of button
     */
	private static void setButtonFunction(final Button button,
			final BType type) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                if (type == BType.EXIT) {
                	GameObservable.notify(Category.MENU, Menu.EXIT);
                	ShaftEscape.exit();
                } else if (type == BType.START) {
                    GameObservable.notify(Category.MENU, Menu.START);
                    ShaftEscape.setScene(new GameScene());
                } else {
                    GameObservable.notify(Category.MENU, Menu.SETTINGS);
                    ShaftEscape.setScene(new SettingsScene());
                }
            }
        });
	}

}

