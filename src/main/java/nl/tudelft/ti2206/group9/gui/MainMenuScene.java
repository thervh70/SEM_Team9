package nl.tudelft.ti2206.group9.gui;

import javafx.beans.binding.Bindings;
import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;
import nl.tudelft.ti2206.group9.util.SaveGame;

/**
 * A Main Menu with different options/buttons like a options menu, start button
 * and exit button.
 * @author Maikel, Maarten, Mathias, Mitchell and Robin
 */
@SuppressWarnings("restriction")
public final class MainMenuScene extends AbstractMenuScene {

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

	/** The input field for the name of the player. */
    static final TextField INPUT = createTextField("PLAYER NAME", 2, 22);

	/**
	 * Create Start, Settings and Exit buttons.
	 * @return an array of Nodes to be added to the Scene.
	 */
	@Override
	public Node[] createContent() {
        final Button startButton = createButton("START!", 4, 22);
		startButton.disableProperty().bind(
				Bindings.isEmpty(INPUT.textProperty()));
		final Button settingsButton = createButton("SETTINGS", 0, 24);
		final Button exitButton = createButton("EXIT", 4, 24);
		final Button loadButton = createButton("LOAD GAME", 2, 24);
		final Label nameLabel = createLabel("NEW PLAYER:", 0, 22);


        /** Set functions of buttons.*/
		setButtonFunction(exitButton, BType.EXIT);
		setButtonFunction(startButton, BType.START);
		setButtonFunction(settingsButton, BType.SETTINGS);
		setButtonFunction(loadButton, BType.LOAD);

        /** Set tooltips. */
        startButton.setTooltip(new Tooltip("Start the game!"));
        exitButton.setTooltip(new Tooltip("Are you sure?"));
        settingsButton.setTooltip(new Tooltip("Change game settings"));
        loadButton.setTooltip(new Tooltip("Continue a game"));
        INPUT.setTooltip(new Tooltip("Enter your name"));

		return new Node[]{startButton, settingsButton, exitButton,
				loadButton, nameLabel, INPUT};
	}

	/**
	 * This method sets the function of a button.
	 * @param button Button to be set.
	 * @param type Type of button
	 */
	private void setButtonFunction(final Button button,
			final BType type) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent event) {
				if (type == BType.EXIT) {
					OBSERVABLE.notify(Category.MENU, Menu.EXIT);
					ShaftEscape.exit();
				} else if (type == BType.START) {
					if (checkPlayerName(INPUT.getText())) {
						createNewGame();
					} else {
						setPopup(new WarningPopup(
								new EventHandler<MouseEvent>() {
							public void handle(final MouseEvent event) {
								setPopup(null);
							}
						}, "The given name is invalid."));
						ShaftEscape.showPopup(getPopup());
					}
				} else if (type == BType.LOAD) {
					OBSERVABLE.notify(Category.MENU, Menu.LOAD_MENU);
					ShaftEscape.setScene(new LoadGameScene());
				} else {
					OBSERVABLE.notify(Category.MENU, Menu.SETTINGS);
					ShaftEscape.setScene(new SettingsScene());
				}
			}
		});
	}

	/**
	 * Checks whether the playername is a valid name.
	 * Invalid options:
	 * <ul>
	 *     <li>Empty name</li>
	 *     <li>Name contains a '.'</li>
	 *     <li>Name contains a '/'</li>
	 *     <li>Name contains a '\'</li>
	 * </ul>
	 * @param name the name to be checked
	 * @return boolean to indicate whether the name is valid
	 */
	private boolean checkPlayerName(final String name) {
		return !(name.contains(".") || name.contains("/")
				|| name.contains("\\"));
	}

	/**
	 * Check whether the given input corresponds to
	 * an already existing savefile. If so, load
	 * that file, otherwise create a new game with that name.
	 */
	private static void createNewGame() {
		State.resetAll();
		State.setPlayerName(INPUT.getText());
		if (!tryLoadPlayerName(INPUT.getText())) {
			SaveGame.saveGame();
		}
		INPUT.clear();
		State.getSaveGames().clear();
		OBSERVABLE.notify(Category.MENU, Menu.START);
		ShaftEscape.setScene(new GameScene());
	}

	/**
	 * Check if the given name corresponds with an alread existing
	 * file. If so, return true, otherwise, retrn false.
	 * @param name the given name to check against the savefiles
	 * @return boolean to indiccate whether a savfile was found
	 */
	private static boolean tryLoadPlayerName(final String name) {
		SaveGame.readPlayerNames();
		if (State.getSaveGames().contains(name)) {
			SaveGame.loadGame(name);
			return true;
		}
		return false;
	}

}
