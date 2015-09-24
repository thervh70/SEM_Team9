package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
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

    final static TextField input = createTextField("PLAYER NAME", 2, 22);

	/**
	 * Create Start, Settings and Exit buttons.
	 * @return an array of Nodes to be added to the Scene.
	 */
	@Override
	public Node[] createContent() {
        final Button startButton = createButton("START!", 4, 22);
		final Button settingsButton = createButton("SETTINGS", 0, 24);
		final Button exitButton = createButton("EXIT", 4, 24);
		final Button loadButton = createButton("LOAD GAME", 2, 24);
		final Label nameLabel = createLabel("NEW PLAYER:", 0, 22);


        /** Set functions of buttons.*/
		setButtonFunction(exitButton, BType.EXIT);
		setButtonFunction(startButton, BType.START);
		setButtonFunction(settingsButton, BType.SETTINGS);
		setButtonFunction(loadButton, BType.LOAD);

		return new Node[]{startButton, settingsButton, exitButton,
				loadButton, nameLabel, input};
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
                    State.setPlayerName(input.getText());
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
