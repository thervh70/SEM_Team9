package nl.tudelft.ti2206.group9.level;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateTest {

    public static final double DELTA = 0.0000001;

    @Before
    public void setUp() {
        State.reset();
        State.setCoins(0);
        State.setHighscore(0);
        assertEquals(0, State.getCoins());
        assertEquals(0, Track.getDistance(), DELTA);
        assertEquals(0, State.getScore(), DELTA);
        assertEquals(0, State.getHighscore(), DELTA);
    }

    @Test
    public void testReset() {
        State.addScore(2);
        Track.addDistance(1);
        State.reset();
        assertEquals(0, Track.getDistance(), DELTA);
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

    /**
     * This method tests whether the highscore is set correctly
     * when the score changes.
     */
    @Test
    public void testHighscore() {
        assertEquals(0, State.getHighscore());

        State.setScore(2);
        State.checkHighscore();
        assertEquals(2, State.getHighscore());

        State.setScore(1);
        State.checkHighscore();
        assertEquals(2, State.getHighscore());
    }

    @Test
    public void testSetPlayerName() {
        State.setPlayerName("Jan");
        assertEquals("Jan", State.getPlayerName());
        State.setPlayerName("Piet");
        assertEquals("Piet", State.getPlayerName());
    }

}
