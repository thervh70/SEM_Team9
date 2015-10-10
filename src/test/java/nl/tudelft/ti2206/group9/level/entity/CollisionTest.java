package nl.tudelft.ti2206.group9.level.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.StateTest;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.Before;
import org.junit.Test;

public class CollisionTest {

	private Player player;
	private Coin coin;
	private AbstractObstacle obstacle;

	@Before
	public void setUp() throws Exception {
		player = new Player();
		coin = new Coin(Point3D.ZERO);
		obstacle = new Log(Point3D.ZERO, Point3D.UNITCUBE);
		State.resetAll();
	}

	@Test
	public void testCollisionPlayerCoin() {
		final double oldscore = State.getScore();
		final int oldcoins = State.getCoins();
		player.collision(coin);
		assertEquals(oldscore, State.getScore(), StateTest.DELTA);
		assertEquals(oldcoins, State.getCoins());
	}

	@Test
	public void testCollisionCoinCoin() {
		final double oldscore = State.getScore();
		final int oldcoins = State.getCoins();
		coin.collision(new Coin(Point3D.ZERO));
		assertEquals(oldscore, State.getScore(), StateTest.DELTA);
		assertEquals(oldcoins, State.getCoins());
	}

	@Test
	public void testCollisionCoinPlayer() {
		final double oldscore = State.getScore();
		final int oldcoins = State.getCoins();
		coin.collision(player);
		assertEquals(oldscore + Coin.VALUE, State.getScore(), StateTest.DELTA);
		assertEquals(oldcoins + 1, State.getCoins());
		// selfDestruct is not tested because this involves threads and is
		// very complicated with timing. If anybody knows how, go ahead!
	}

	@Test
	public void testCollisionPlayerObstacle() {
		player.collision(obstacle);
		assertFalse(player.isAlive());
	}

}
