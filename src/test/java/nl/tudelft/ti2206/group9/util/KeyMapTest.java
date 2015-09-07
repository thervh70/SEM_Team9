package nl.tudelft.ti2206.group9.util;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Mathias
 */
public class KeyMapTest {

    private Action action;
    private KeyEvent key;
    private JPanel screen;
    private KeyMap keyMapObject;

    public static final int MAGIC = 42;

    @Before
    public void setUp() {
        action = mock(Action.class);
        keyMapObject = new KeyMap();
        screen = new JPanel();
        screen.addKeyListener(keyMapObject);
    }

    @Test
    public void addKeyTest() {
        assertEquals(null, KeyMap.getKey(MAGIC));
        KeyMap.addKey(MAGIC, action);
        assertEquals(action, KeyMap.getKey(MAGIC));
    }

    @Test
    public void removeKeyTest() {
        addKeyTest();
        KeyMap.removeKey(MAGIC);
        assertEquals(null, KeyMap.getKey(MAGIC));
    }

    @Test
    public void pressKey42Test() {
        KeyMap.addKey(MAGIC, action);
        pressKey(MAGIC);
        verify(action).doAction();
    }

    @Test
    public void pressKeyNullTest() {
        KeyMap.addKey(MAGIC, null);
        pressKey(MAGIC);
        verify(action, never()).doAction();
    }

    public void pressKey(int keyCode) {
        key = new KeyEvent(screen, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode,'Z');
        screen.getKeyListeners()[0].keyPressed(key);
    }
}
