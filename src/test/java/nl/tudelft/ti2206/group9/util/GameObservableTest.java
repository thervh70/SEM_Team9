package nl.tudelft.ti2206.group9.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Observable;

import org.junit.Before;
import org.junit.Test;

/**
 * Implements GameObserver to also test whether the arguments are passed
 * correctly, instead of only verifying whether the methods have been called.
 * @author Maarten
 */
public class GameObservableTest implements GameObserver {

    /** GameObservers used for testing. */
    private GameObserver go1, go2;

    private final GameObservable obs = GameObservable.OBSERVABLE;

    private static final GameUpdate TEST_UPDATE =
            new GameUpdate(Category.GAME, Game.STARTED, 0);

    /** Mock observers. */
    @Before
    public void setUp() {
        go1 = mock(GameObserver.class);
        go2 = mock(GameObserver.class);
    }

    /** Test whether observers are called when there is an update. */
    @Test
    public void testNotifyObservers() {
        obs.addObserver(this);
        obs.addObserver(go1);
        obs.addObserver(go2);
        obs.deleteObserver(go2);
        obs.notify(TEST_UPDATE);
        verify(go1).update(obs, TEST_UPDATE);
        verify(go2, never()).update(obs, TEST_UPDATE);

        // Clean up test
        obs.deleteObserver(go1);
        obs.deleteObserver(this);
    }

    @SuppressWarnings("deprecation")
    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedException() {
        obs.notifyObservers();
    }

    @SuppressWarnings("deprecation")
    @Test(expected = ClassCastException.class)
    public void testClassCastException() {
        obs.notifyObservers(0);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDeprecatedNotifyObservers() {
        obs.addObserver(go1);
        obs.notifyObservers(TEST_UPDATE);
        verify(go1).update(obs, TEST_UPDATE);

        // Clean up test
        obs.deleteObserver(go1);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        final GameUpdate update = (GameUpdate) arg;
        assertEquals(Category.GAME, update.getCat());
        assertEquals(Game.STARTED, update.getSpec());
        assertArrayEquals(new Integer[]{0}, update.getArgs());
    }

}
