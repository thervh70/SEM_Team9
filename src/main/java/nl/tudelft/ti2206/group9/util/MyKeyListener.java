package nl.tudelft.ti2206.group9.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mathias
 */
public class MyKeyListener implements KeyListener{


    /** KeyMapping, linking KeyPresses (Integers) to Actions */
    private static Map<Integer, Action> keyMapping = new HashMap<Integer, Action>();

    public void keyPressed(KeyEvent e) {
        Action action = keyMapping.get(e.getKeyCode());
        if(action != null) {
            action.doAction();
        }
    }

    /**
     * KeyTyped does nothing
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) { }

    /**
     * KeyReleased does nothing
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) { }

    /**
     * Adds a key to the keymapping
     * @param KeyValue
     * @param action
     */
    public static void addKey(int KeyValue, Action action) {
        keyMapping.put(KeyValue, action);
    }
}
