package nl.tudelft.ti2206.group9.level;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StateTest {

	public static final double DELTA = 0.0000001;

	@Before
	public void setUp() throws Exception {
		State.resetAll();
		assertEquals(0, State.getCoins());
		assertEquals(0, State.getDistance(), DELTA);
		assertEquals(0, State.getScore(), DELTA);
	}

	@Test
	public void testReset() {
		State.addScore(2);
		Track.addDistance(1);
		State.reset();
		assertEquals(0, State.getDistance(), DELTA);
		assertEquals(0, State.getScore(), DELTA);
	}

	@Test
	public void testAddScore() {
		State.addScore(2);
		State.addScore(1);
		assertEquals(2 + 1, State.getScore(), DELTA);
	}

	@Test
	public void testAddCoins() {
		State.addCoins(2);
		State.addCoins(1);
		assertEquals(2 + 1, State.getCoins());
	}
	
	@Test
	public void testModulo_Distance() {
		Track.setDistance(2);
		assertEquals(0, State.modulo(State.getDistance()));
		Track.setDistance(State.MOD + 1);
		assertEquals(State.MOD, State.modulo(State.getDistance()));
	}

	@Test
	public void testModulo_Score() {
		State.setScore(2.0);
		assertEquals(0, State.modulo(State.getDistance()));
		State.setScore(State.MOD + 1.0);
		assertEquals(State.MOD, State.modulo(State.getScore()));
	}
	
	@Test
	public void testGetDistance() {
		Track.setDistance(2);
		assertEquals(2, State.getDistance(), DELTA);
		Track.setDistance(1);
		assertEquals(1, State.getDistance(), DELTA);
	}

	@Test
	public void testSetScore() {
		State.setScore(2);
		assertEquals(2, State.getScore(), DELTA);
		State.setScore(1);
		assertEquals(1, State.getScore(), DELTA);
	}

	@Test
	public void testSetCoins() {
		State.setCoins(2);
		assertEquals(2, State.getCoins());
		State.setCoins(1);
		assertEquals(1, State.getCoins());
	}
	
	@Test
	public void testGetHighScore() {
		assertEquals(0, State.getHighscore());
		
		State.setScore(2);
		State.checkHighscore();
		assertEquals(2, State.getHighscore());
		
		State.setScore(1);
		State.checkHighscore();
		assertEquals(2, State.getHighscore());
	}

}
