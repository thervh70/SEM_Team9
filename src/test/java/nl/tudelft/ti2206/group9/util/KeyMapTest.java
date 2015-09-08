package nl.tudelft.ti2206.group9.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import javafx.scene.input.KeyCode;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 */
@SuppressWarnings("restriction")
public class KeyMapTest {

    private Action action;
    private KeyMap keyMapObject;

    @Before
    public void setUp() {
        action = mock(Action.class);
        keyMapObject = new KeyMap();
    }

    @Test
    public void addKeyTest() {
        assertEquals(null, KeyMap.getKey(KeyCode.UP));
        KeyMap.addKey(KeyCode.UP, action);
        assertEquals(action, KeyMap.getKey(KeyCode.UP));
    }

    @Test
    public void removeKeyTest() {
        addKeyTest();
        KeyMap.removeKey(KeyCode.UP);
        assertEquals(null, KeyMap.getKey(KeyCode.UP));
    }

    @Test
    public void pressKey42Test() {
        KeyMap.addKey(KeyCode.UP, action);
        pressKey(KeyCode.UP);
        verify(action).doAction();
    }

    @Test
    public void pressKeyNullTest() {
        KeyMap.addKey(KeyCode.UP, null);
        pressKey(KeyCode.UP);
        verify(action, never()).doAction();
    }

    public void pressKey(KeyCode code) {
        keyMapObject.keyPressed(code);
    }
}
