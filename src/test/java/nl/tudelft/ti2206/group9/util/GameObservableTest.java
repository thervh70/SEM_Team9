package nl.tudelft.ti2206.group9.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

/**
 * Implements GameObserver to also test whether the arguments are passed
 * correctly, instead of only verifying whether the methods have been called.
 * @author Maarten
 */
public class GameObservableTest implements GameObserver {

	private GameObserver go1, go2;

	/** Mock observers. */
	@Before
	public void setUp() throws Exception {
		go1 = mock(GameObserver.class);
		go2 = mock(GameObserver.class);
	}

	/** Test whether observers are called when there is an update. */
	@Test
	public void testNotifyObservers() {
		GameObservable.addObserver(this);
		GameObservable.addObserver(go1);
		GameObservable.addObserver(go2);
		GameObservable.deleteObserver(go2);
		GameObservable.notify(Category.GAME, Game.STARTED, 0);
		verify(go1).gameUpdate(Category.GAME, Game.STARTED, new Integer[]{0});
		verify(go2, never()).gameUpdate(Category.GAME, Game.STARTED,
				new Integer[]{0});
		
		// Clean up test
		GameObservable.deleteObserver(go1);
		GameObservable.deleteObserver(this);
	}

	@Override
	public void gameUpdate(Category cat, Specific spec, Object[] optionalArgs) {
		assertEquals(Category.GAME, cat);
		assertEquals(Game.STARTED, spec);
		assertArrayEquals(new Integer[]{0}, optionalArgs);
	}

}
