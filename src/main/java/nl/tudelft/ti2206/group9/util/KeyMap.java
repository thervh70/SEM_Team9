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
    	
    	Action action = keyMap.get(e);
    	if (action != null) {			// If action is defined
    		if (!pressed.get(e)) {		// If key is not already pressed
    			action.doAction();		// Do action
    			pressed.put(e, true);	// Mark as pressed
    			System.out.println("Key Pressed: " + e.toString());
    		}
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
	public void keyReleased(final KeyCode e) {
        pressed.put(e, false);
		System.out.println("Key Release: " + e.toString());
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
    
    private static final class MoveAction implements Action {
    	private Direction dir;
    	
    	private MoveAction(Direction d) {
    		dir = d;
    	}
    	
    	/** Perform action. */
        public void doAction() {
            final Player player = (Player) State.getTrack().getPlayer();
            player.move(dir);
        }
    }
    
}
