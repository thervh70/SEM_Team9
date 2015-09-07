package nl.tudelft.ti2206.group9.util;

import java.util.HashMap;
import java.util.Map;

import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;
import javafx.scene.input.KeyCode;

/**
 * @author Mathias
 */
@SuppressWarnings("restriction")
public class KeyMap {

    /** Links KeyCodes to Actions. */
	private static Map<KeyCode, Action> keyMap = new HashMap<KeyCode, Action>();

    /**
     * At key press, the corresponding action in keyMap (if any)
     * is executed.
     * @param e keyEvent
     */
    public final void keyPressed(final KeyCode e) {
        Action action = keyMap.get(e);
        if (action != null) {
            action.doAction();
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
	public void keyReleased(final KeyCode e) { }

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

    public static void defaultKeys() {
        KeyMap.addKey(KeyCode.UP, new Action() {
            public void doAction() {
                final Player player = (Player) State.getTrack().getPlayer();
                player.move(Direction.JUMP);
            }
        });
        KeyMap.addKey(KeyCode.DOWN, new Action() {
            public void doAction() {
                final Player player = (Player) State.getTrack().getPlayer();
                player.move(Direction.SLIDE);
            }
        });
        KeyMap.addKey(KeyCode.LEFT, new Action() {
            public void doAction() {
                final Player player = (Player) State.getTrack().getPlayer();
                player.move(Direction.LEFT);
            }
        });
        KeyMap.addKey(KeyCode.RIGHT, new Action() {
            public void doAction() {
                final Player player = (Player) State.getTrack().getPlayer();
                player.move(Direction.RIGHT);
            }
        });
    }
}
