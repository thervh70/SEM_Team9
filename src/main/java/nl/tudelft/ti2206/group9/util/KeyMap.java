package nl.tudelft.ti2206.group9.util;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.input.KeyCode;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Input;

/**
 * @author Mathias
 */
public class KeyMap {

    /** Links KeyCodes to Actions. */
    private final Map<KeyCode, Action> actionMap =
            new ConcurrentHashMap<KeyCode, Action>();

    /** Stores whether keys are pressed or not. */
    private final Map<KeyCode, Boolean> pressed =
            new ConcurrentHashMap<KeyCode, Boolean>();

    /**
     * At key press, the corresponding action in actionMap (if any)
     * is executed.
     * @param e keyEvent
     */
    public final void keyPressed(final KeyCode e) {
        // If this key has never been touched, create entry in pressed Map.
        if (pressed.get(e) == null) {
            pressed.put(e, false);
        }

        final Action action = actionMap.get(e);
        if (action != null && !pressed.get(e)) {    // If key not pressed
            OBSERVABLE.notify(Category.INPUT, Input.KEYBOARD, e);
            pressed.put(e, true);                   // Mark as pressed
            action.doAction();                      // Do action
        }
    }

    /**
     * Does nothing.
     * @param e keyEvent
     */
    public void keyTyped(final KeyCode e) { } //NOPMD

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
    public Action getKey(final KeyCode code) {
        return actionMap.get(code);
    }

    /**
     * Adds a key to the actionMap.
     * @param code KeyCode
     * @param action Action to perform at key press
     */
    public void addKey(final KeyCode code, final Action action) {
        actionMap.put(code, action);
    }

    /**
     * Remove a key from the KeyMap.
     * @param code KeyCode
     */
    public void removeKey(final KeyCode code) {
        actionMap.remove(code);
    }

    /** Releases all keys in the KeyMap. */
    public void releaseAll() {
        for (final KeyCode kc : pressed.keySet()) {
            pressed.put(kc, false);
        }
    }

}
