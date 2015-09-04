package nl.tudelft.ti2206.group9.level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.Before;
import org.junit.Test;

public class TrackTest {
	
	public static final double DELTA = 0.0000001;
	
	private transient Track track;

	@Before
	public void setUp() throws Exception {
		track = new Track();
	}

	@Test
	public void testTrack() {
		assertEquals(1, track.getEntities().size());
		assertEquals(new Player(), track.getEntities().get(0));
	}

	@Test
	public void testMoveTrack() {
		track.addEntity(new Coin(new Point3D(2, 0, 0)));
		track.moveTrack(1.0 / 2);
		assertEquals(track.getEntities().get(0).getCenter().getX(), 0, DELTA);
		assertEquals(1 + 1.0 / 2, track.getEntities().get(1).getCenter().getX(),
				DELTA);
	}

	@Test
	public void testAddEntity() {
		track.addEntity(new Coin(Point3D.UNITX));
		assertEquals(2, track.getEntities().size());
		assertEquals(new Player(), track.getEntities().get(0));
		assertEquals(new Coin(Point3D.UNITX), track.getEntities().get(1));
	}

	@Test
	public void testRemoveEntity() {
		Coin coin = new Coin(Point3D.UNITX);
		track.addEntity(coin);
		assertEquals(2, track.getEntities().size());
		track.removeEntity(coin);
		assertEquals(1, track.getEntities().size());
		assertEquals(new Player(), track.getEntities().get(0));
	}

	@Test
	public void testGetEntities() {
		assertEquals(1, track.getEntities().size());
		assertEquals(new Player(), track.getEntities().get(0));
	}
	
	@Test
	public void testGetPlayer() {
		assertTrue(track.getPlayer() instanceof Player);
	}
	
	@Test
	public void testStep() {
		Random rand = mock(Random.class);
		final double belowCoinChance = Track.COINCHANCE - 0.01;
		final double aboveCoinChance = Track.COINCHANCE + 0.01;
		final double belowObstacleChance = Track.OBSTACLECHANCE - 0.01;
		final double aboveObstacleChance = Track.OBSTACLECHANCE + 0.01;
		final Track track = new Track(rand);
		int expectedSize = 1;
		
		when(rand.nextDouble())
				.thenReturn(belowCoinChance, // elseif, obstacle isn't created
						aboveCoinChance, belowObstacleChance,
						aboveCoinChance, aboveObstacleChance);
		track.step();
		expectedSize++;
		assertEquals(expectedSize, track.getEntities().size());
		assertTrue(track.getEntities().get(1) instanceof Coin);

		track.step();
		expectedSize++;
		assertEquals(expectedSize, track.getEntities().size());
		assertTrue(track.getEntities().get(2) instanceof Obstacle);
		
		track.step();
		assertEquals(expectedSize, track.getEntities().size());
	}

}
