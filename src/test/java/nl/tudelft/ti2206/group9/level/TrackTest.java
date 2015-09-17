package nl.tudelft.ti2206.group9.level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
		track.addEntity(new Coin(new Point3D(0, 0, 2)));
		track.moveTrack(1.0 / 2);
		assertEquals(track.getEntities().get(0).getCenter().getZ(), 0, DELTA);
		assertEquals(1 + 1.0 / 2, track.getEntities().get(1).getCenter().getZ(),
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
		try {
			track.getPlayer();
		} catch (ClassCastException e) {
			fail("Player could not be retrieved from the entities list.");
		}
	}

	@Test
	public void testContainsCenter() {
		Coin coin = new Coin(new Point3D(1, 0, 0));
		assertFalse(track.containsCenter(coin.getCenter()));
		track.addEntity(coin);
		assertTrue(track.containsCenter(coin.getCenter()));
	}
	
	@Test
	public void testStepCoinZigZag() {
		Random rand = mock(Random.class);
		final double belowCoinZigZagChance = Track.COIN_ZIGZAG_CHANCE - 0.01;
		final double aboveObstacleChance = Track.OBSTACLE_CHANCE + 0.01;
		final Track track = new Track(rand);
		final int length = 5;

		when(rand.nextDouble()).thenReturn(belowCoinZigZagChance, 
				aboveObstacleChance);
		when(rand.nextInt(10)).thenReturn(length);

		assertEquals(1, track.getEntities().size()); //player

		track.step();
		assertEquals(13, track.getEntities().size()); //player + 12 coins
		for (int i = 1; i < 13; i++) {
			assertTrue(track.getEntities().get(i) instanceof Coin);
		}
	}

	@Test
	public void testAddDistance() {
		Track.setDistance(0);
		Track.addDistance(2.0);
		Track.addDistance(1.0);
		assertEquals(2.0 + 1.0, Track.getDistance(), DELTA);
	}

	@Test
	public void testSetDistance() {
		Track.setDistance(2);
		assertEquals(2, Track.getDistance(), DELTA);
		Track.setDistance(1);
		assertEquals(1, Track.getDistance(), DELTA);
	}

	@Test
	public void testStepCoinLane() {
		Random rand = mock(Random.class);
		final double belowCoinLaneChance = Track.COIN_ZIGZAG_CHANCE 
				+ Track.COIN_LANE_CHANCE - 0.01;
		final double aboveObstacleChance = Track.OBSTACLE_CHANCE + 0.01;
		final Track track = new Track(rand);
		final int length = 5;

		when(rand.nextDouble()).thenReturn(belowCoinLaneChance, 
				aboveObstacleChance);
		when(rand.nextInt(10)).thenReturn(length);

		assertEquals(1, track.getEntities().size());

		track.step();
		assertEquals(11, track.getEntities().size());
		for (int i = 1; i < 11; i++) {
			assertTrue(track.getEntities().get(i) instanceof Coin);
		}

		double oldCoinLeft = track.getCoinrunleft();
		double unitsPerTick = Track.getUnitsPerTick();
		track.step();
		double newCoinLeft = track.getCoinrunleft();
		assertEquals(newCoinLeft, oldCoinLeft 
				- unitsPerTick / Track.COIN_DISTANCE, DELTA);
	}

	@Test
	public void testSingleObstacle() {
		Random rand = mock(Random.class);
		final double aboveCoinLaneChance = Track.COIN_ZIGZAG_CHANCE 
				+ Track.COIN_LANE_CHANCE + 0.01;
		final double belowObstacleChance = Track.OBSTACLE_CHANCE - 0.01;
		final Track track = new Track(rand);

		when(rand.nextDouble()).thenReturn(aboveCoinLaneChance, 
				belowObstacleChance);

		assertEquals(1, track.getEntities().size());

		track.step();
		assertEquals(2, track.getEntities().size());
		assertTrue(track.getEntities().get(1) instanceof Obstacle);
	}

	@Test
	public void testSingleObstacleWithObstruction() {
		Random rand = mock(Random.class);
		final double aboveCoinLaneChance = Track.COIN_ZIGZAG_CHANCE 
				+ Track.COIN_LANE_CHANCE + 0.01;
		final double belowObstacleChance = Track.OBSTACLE_CHANCE - 0.01;

		when(rand.nextInt(Track.WIDTH)).thenReturn(1);
		when(rand.nextDouble()).thenReturn(aboveCoinLaneChance, 
				belowObstacleChance);
		final Track track = new Track(rand);
		
		track.addEntity(new Coin(new Point3D(0, 1, Track.LENGTH)));
		assertEquals(2, track.getEntities().size());

		track.step();
		assertEquals(2 + 1, track.getEntities().size());
		assertTrue(track.getEntities().get(2) instanceof Obstacle);
		assertEquals(1, track.getEntities().get(2).getCenter().getX(), DELTA);
	}
}
