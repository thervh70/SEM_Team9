package nl.tudelft.ti2206.group9.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathias
 */
public class KeyMap implements KeyListener {

    /** Links KeyCodes (Integers) to Actions. */
    private static Map<Integer, Action> keyMap =
    		new HashMap<Integer, Action>();

    /**
     * At key press, the corresponding action in keyMap (if any)
     * is executed.
     * @param e keyEvent
     */
    public void keyPressed(KeyEvent e) {
        Action action = keyMap.get(e.getKeyCode());
        if (action != null) {
            action.doAction();
        }
    }

    /**
     * Does nothing.
     * @param e keyEvent
     */
	public void keyTyped(KeyEvent e) { }

    /**
     * Does nothing.
     * @param e keyEvent
     */
	public void keyReleased(KeyEvent e) { }

    /**
     * Adds a key to the keyMap.
     * @param keyValue KeyCode
     * @param action Action to perform at key press
     */
    public static void addKey(int keyValue, Action action) {
        keyMap.put(keyValue, action);
    }
}
