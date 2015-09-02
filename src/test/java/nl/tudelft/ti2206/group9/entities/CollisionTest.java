package nl.tudelft.ti2206.group9.entities;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.Before;
import org.junit.Test;

public class CollisionTest {

	private Player player;
	private Coin coin;
	
	@Before
	public void setUp() throws Exception {
		player = new Player();
		coin = new Coin(Point3D.ZERO);
	}

	@Test
	public void testCollisionPlayerCoin() {
		int oldscore = State.getScore();
		int oldcoins = State.getCoins();
		player.collision(coin);
		assertEquals(oldscore + Coin.VALUE, State.getScore());
		assertEquals(oldcoins + 1, State.getCoins());
	}

	@Test
	public void testCollisionCoinPlayer() {
		int oldscore = State.getScore();
		int oldcoins = State.getCoins();
		coin.collision(player);
		assertEquals(oldscore, State.getScore());
		assertEquals(oldcoins, State.getCoins());
		// selfDestruct is not tested because this involves threads and is
		// very complicated with timing. If anybody knows how, go ahead!
	}

}
