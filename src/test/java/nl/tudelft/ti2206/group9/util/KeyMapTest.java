package nl.tudelft.ti2206.group9.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import javafx.scene.input.KeyCode;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 */
public class KeyMapTest {

    private Action action;
    private KeyMap keyMap;

    @Before
    public void setUp() {
        action = mock(Action.class);
        keyMap = new KeyMap();
    }

    @Test
    public void addKeyTest() {
        assertEquals(null, keyMap.getKey(KeyCode.UP));
        keyMap.addKey(KeyCode.UP, action);
        assertEquals(action, keyMap.getKey(KeyCode.UP));
        keyMap.removeKey(KeyCode.UP);
    }

    @Test
    public void removeKeyTest() {
        addKeyTest();
        keyMap.removeKey(KeyCode.UP);
        assertEquals(null, keyMap.getKey(KeyCode.UP));
    }

    @Test
    public void pressKey42Test() {
        keyMap.addKey(KeyCode.UP, action);
        pressKey(KeyCode.UP);
        releaseKey(KeyCode.UP);
        verify(action).doAction();
        keyMap.removeKey(KeyCode.UP);
    }

    @Test
    public void pressKeyNullTest() {
        keyMap.addKey(KeyCode.UP, action);
        keyMap.removeKey(KeyCode.UP);
        pressKey(KeyCode.UP);
        releaseKey(KeyCode.UP);
        verify(action, never()).doAction();
    }

    @Test
    public void pressReleasePressTest() {
        keyMap.addKey(KeyCode.UP, action);
        pressKey(KeyCode.UP);
        releaseKey(KeyCode.UP);
        pressKey(KeyCode.UP);
        releaseKey(KeyCode.UP);
        verify(action, times(2)).doAction();
        keyMap.removeKey(KeyCode.UP);
    }

    @Test
    public void pressPressReleaseTest() {
        keyMap.addKey(KeyCode.UP, action);
        pressKey(KeyCode.UP);
        pressKey(KeyCode.UP);
        releaseKey(KeyCode.UP);
        verify(action, times(1)).doAction();
        keyMap.removeKey(KeyCode.UP);
    }

    private void pressKey(final KeyCode code) {
        keyMap.keyPressed(code);
    }

    private void releaseKey(final KeyCode code) {
        keyMap.keyReleased(code);
    }
}
