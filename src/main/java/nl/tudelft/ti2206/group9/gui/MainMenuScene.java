package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

/**
 * A Main Menu with different options/buttons like a options menu, start button
 * and exit button.
 * @author Maikel, Maarten, Mitchell and Robin
 */
@SuppressWarnings("restriction")
public final class MainMenuScene extends MenuScene {

	/**
	 * Type of buttons that exist.
	 */
	private enum BType {
		/** Exit button. */
		EXIT,
		/** Start button. */
		START,
		/** Settings button. */
		SETTINGS,
		/** Load Game button. */
		LOAD
	}

	/**
	 * Create Start, Settings and Exit buttons.
	 * @return an array of Nodes to be added to the Scene.
	 */
	@Override
	public Node[] createContent() {
        final Button startButton = createButton("START", 6, 26);
		final Button settingsButton = createButton("SETTINGS", 2, 26);
		final Button exitButton = createButton("EXIT", 10, 26);
		final Button loadButton = createButton("LOAD GAME", 6, 20);

		setButtonFunction(exitButton, BType.EXIT);
		setButtonFunction(startButton, BType.START);
		setButtonFunction(settingsButton, BType.SETTINGS);
		setButtonFunction(loadButton, BType.LOAD);

		return new Node[]{startButton, settingsButton, exitButton, loadButton};
	}

	/**
	 * This method sets the function of a button.
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
				} else if (type == BType.LOAD) {
					GameObservable.notify(Category.MENU, Menu.LOAD_MENU);
					ShaftEscape.setScene(new LoadGameScene());
				} else {
					GameObservable.notify(Category.MENU, Menu.SETTINGS);
					ShaftEscape.setScene(new SettingsScene());
				}
			}
		});

	}
}
