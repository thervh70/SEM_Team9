package nl.tudelft.ti2206.group9.level;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StateTest {

	@Before
	public void setUp() throws Exception {
		State.resetAll();
		assertEquals(0, State.getCoins());
		assertEquals(0, State.getTrack().getDistance());
		assertEquals(0, State.getScore());
	}

	@Test
	public void testReset() {
		State.addScore(2);
		State.getTrack().addDistance(1);
		State.reset();
		assertEquals(0, State.getTrack().getDistance());
		assertEquals(0, State.getScore());
	}

	@Test
	public void testAddScore() {
		State.addScore(2);
		State.addScore(1);
		assertEquals(2 + 1, State.getScore());
	}

	@Test
	public void testAddCoins() {
		State.addCoins(2);
		State.addCoins(1);
		assertEquals(2 + 1, State.getCoins());
	}

	@Test
	public void testAddDistance() {
		State.getTrack().addDistance(2);
		State.getTrack().addDistance(1);
		assertEquals(2 + 1, State.getTrack().getDistance());
	}

	@Test
	public void testSetScore() {
		State.setScore(2);
		assertEquals(2, State.getScore());
		State.setScore(1);
		assertEquals(1, State.getScore());
	}

	@Test
	public void testSetCoins() {
		State.setCoins(2);
		assertEquals(2, State.getCoins());
		State.setCoins(1);
		assertEquals(1, State.getCoins());
	}

	@Test
	public void testSetDistance() {
		State.getTrack().setDistance(2);
		assertEquals(2, State.getTrack().getDistance());
		State.getTrack().setDistance(1);
		assertEquals(1, State.getTrack().getDistance());
	}

}
