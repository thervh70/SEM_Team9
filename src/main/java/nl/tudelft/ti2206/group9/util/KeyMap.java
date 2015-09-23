package nl.tudelft.ti2206.group9.util;

import javafx.scene.input.KeyCode;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Input;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathias
 */
@SuppressWarnings("restriction")
public class KeyMap {

	/** Links KeyCodes to Actions. */
	private static Map<KeyCode, Action> keyMap
	= new HashMap<KeyCode, Action>();

	/** Stores whether keys are pressed or not. */
	private static Map<KeyCode, Boolean> pressed =
			new HashMap<KeyCode, Boolean>();

	/**
	 * At key press, the corresponding action in keyMap (if any)
	 * is executed.
	 * @param e keyEvent
	 */
	public final void keyPressed(final KeyCode e) {
		// If this key has never been touched, create entry in pressed Map.
		if (pressed.get(e) == null) {
			pressed.put(e, false);
		}

		final Action action = keyMap.get(e);
		if (action != null && !pressed.get(e)) {	// If key not pressed
			GameObservable.notify(Category.INPUT, Input.KEYBOARD, e);
			action.doAction();						// Do action
			pressed.put(e, true);					// Mark as pressed
		}
	}

	/**
	 * Does nothing.
	 * @param e keyEvent
	 */
	public void keyTyped(final KeyCode e) { }

	/**
	 * Does nothing.
	 * @param e keyEvent
	 */
	public final void keyReleased(final KeyCode e) {
		pressed.put(e, false);
	}

	/**
	 * Return an Action given a KeyCode.
	 * @param code KeyCode
	 * @return corresponding Action
	 */
	public static Action getKey(final KeyCode code) {
		return keyMap.get(code);
	}

	/**
	 * Adds a key to the keyMap.
	 * @param code KeyCode
	 * @param action Action to perform at key press
	 */
	public static void addKey(final KeyCode code, final Action action) {
		keyMap.put(code, action);
	}

	/**
	 * Remove a key from the KeyMap.
	 * @param code KeyCode
	 */
	public static void removeKey(final KeyCode code) {
		keyMap.remove(code);
	}

	/**
	 * Set the game to use the default keys.
	 */
	public static void defaultKeys() {
		KeyMap.addKey(KeyCode.UP, new MoveAction(Direction.JUMP));
		KeyMap.addKey(KeyCode.W, getKey(KeyCode.UP));

		KeyMap.addKey(KeyCode.DOWN, new MoveAction(Direction.SLIDE));
		KeyMap.addKey(KeyCode.S, getKey(KeyCode.DOWN));

		KeyMap.addKey(KeyCode.LEFT, new MoveAction(Direction.LEFT));
		KeyMap.addKey(KeyCode.A, getKey(KeyCode.LEFT));

		KeyMap.addKey(KeyCode.RIGHT, new MoveAction(Direction.RIGHT));
		KeyMap.addKey(KeyCode.D, getKey(KeyCode.RIGHT));
	}

	/**
	 * MoveAction implement Action.
	 * It's main reason of existence is getting rid of the
	 * need to implement Action over and over again.
	 */
	private static final class MoveAction implements Action {

		/** The direction. */
		private final Direction dir;

		/**
		 * Nice constructor.
		 * @param d direction
		 */
		private MoveAction(final Direction d) {
			dir = d;
		}

		/** Perform action. */
		public void doAction() {
			final Player player = State.getTrack().getPlayer();
			player.move(dir);
		}
	}
}
