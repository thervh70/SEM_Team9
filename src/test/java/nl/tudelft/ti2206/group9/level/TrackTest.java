package nl.tudelft.ti2206.group9.level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
		assertTrue(track.getPlayer() instanceof Player);
	}
	
	@Test
	public void testStepCoinZigZag() {
		Random rand = mock(Random.class);
		final double belowCoinZigZagChance = Track.COINZIGZAGCHANCE - 0.01;
		final Track track = new Track(rand);
		final int length = 5;
		
		when(rand.nextDouble()).thenReturn(belowCoinZigZagChance);
		when(rand.nextInt(10)).thenReturn(length);

		assertEquals(1, track.getEntities().size()); //player

		track.step();
		assertEquals(13, track.getEntities().size()); //player + 12 coins
		for (int i = 1; i <13; i++) {
			assertTrue(track.getEntities().get(i) instanceof Coin);
		}
	}

	@Test
	public void testStepCoinLane() {
		Random rand = mock(Random.class);
		final double belowCoinLaneChance = Track.COINZIGZAGCHANCE + Track.COINLANECHANCE - 0.01;
		final Track track = new Track(rand);
		final int length = 5;

		when(rand.nextDouble()).thenReturn(belowCoinLaneChance);
		when(rand.nextInt(10)).thenReturn(length);

		assertEquals(1, track.getEntities().size());

		track.step();
		assertEquals(11, track.getEntities().size());
		for (int i = 1; i < 11; i++) {
			assertTrue(track.getEntities().get(i) instanceof Coin);
		}

		double oldCoinLeft = track.getCoinrunleft();
		track.step();
		double newCoinLeft = track.getCoinrunleft();
		assertEquals(newCoinLeft, oldCoinLeft - Track.UNITS_PER_TICK, DELTA);
	}
}
