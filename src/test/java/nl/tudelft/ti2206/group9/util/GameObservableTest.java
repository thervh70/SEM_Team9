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

	private final GameObservable obs = new GameObservable();

	/** Mock observers. */
	@Before
	public void setUp() {
		go1 = mock(GameObserver.class);
		go2 = mock(GameObserver.class);
	}

	/** Test whether observers are called when there is an update. */
	@Test
	public void testNotifyObservers() {
		final GameUpdate testUpdate =
				new GameUpdate(Category.GAME, Game.STARTED, 0);

		obs.addObserver(this);
		obs.addObserver(go1);
		obs.addObserver(go2);
		obs.deleteObserver(go2);
		obs.notify(testUpdate);
		verify(go1).update(obs, testUpdate);
		verify(go2, never()).update(obs, testUpdate);

		// Clean up test
		obs.deleteObserver(go1);
		obs.deleteObserver(this);
	}

	@Override
	public void update(final Observable o, final Object arg) {
		final GameUpdate update = (GameUpdate) arg;
		assertEquals(Category.GAME, update.getCat());
		assertEquals(Game.STARTED, update.getSpec());
		assertArrayEquals(new Integer[]{0}, update.getArgs());
	}

}
