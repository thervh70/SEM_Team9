package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.util.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class PlayerTest {

    private static double DELTA = 0.00000001;

    private Player player;

    @Before
    public void setUp() {
        player = new Player();
    }

    @Test
    public void moveLeftTest() {
        assertEquals(1, player.getCenter().getY(), DELTA);
        player.move(Direction.LEFT);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { }
        assertEquals(0, player.getCenter().getY(), DELTA);
    }

    @Test
    public void moveRightTest() {
        assertEquals(1, player.getCenter().getY(), DELTA);
        player.move(Direction.RIGHT);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { }
        assertEquals(2, player.getCenter().getY(), DELTA);
    }

    @Test
    public void moveLeftOffTrackTest() {
        moveLeftTest();
        player.move(Direction.LEFT);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { }
        assertEquals(0, player.getCenter().getY(), DELTA);
    }

    @Test
    public void moveRightOffTrackTest() {
        moveRightTest();
        player.move(Direction.RIGHT);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { }
        assertEquals(2, player.getCenter().getY(), DELTA);
    }
}
