package nl.tudelft.ti2206.group9.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.group9.util.Direction;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 */
public class PlayerTest {

    private static final double DELTA = 0.00000001;

    private Player player;

    @Before
    public void setUp() {
        player = new Player();
        player.respawn();
    }
    
    @Test
    public void respawnTest() {
    	player.die();
    	assertFalse(player.isAlive());
    	player.respawn();
    	assertTrue(player.isAlive());
    }

    @Test
    public void moveLeftTest() {
        assertEquals(0, player.getMoveLane(), DELTA);
        player.move(Direction.LEFT);
        assertEquals(-1, player.getMoveLane(), DELTA);
    }

    @Test
    public void moveRightTest() {
        assertEquals(0, player.getMoveLane(), DELTA);
        player.move(Direction.RIGHT);
        assertEquals(1, player.getMoveLane(), DELTA);
    }

    @Test
    public void moveLeftOffTrackTest() {
        moveLeftTest();
        player.move(Direction.LEFT);
        assertEquals(-1, player.getMoveLane(), DELTA);
    }

    @Test
    public void moveRightOffTrackTest() {
        moveRightTest();
        player.move(Direction.RIGHT);
        assertEquals(1, player.getMoveLane(), DELTA);
    }

    @Test
    public void moveJumpTest() {
        assertEquals(Player.HEIGHT / 2, player.getCenter().getY(), DELTA);
        player.move(Direction.JUMP);
        player.step();
        assertEquals(Player.HEIGHT / 2 + Player.JUMPSPEED - Player.GRAVITY, 
        		player.getCenter().getY(), DELTA);
    }

    @Test
    public void moveDoubleJumpTest() {
        assertEquals(Player.HEIGHT / 2, player.getCenter().getY(), DELTA);
        player.move(Direction.JUMP);
        player.step();
        player.move(Direction.JUMP);
        player.step();
        assertEquals(Player.HEIGHT / 2 			// Original height
        		+ 2 * Player.JUMPSPEED 			// Two steps to get higher
        		- (1 + 2) * Player.GRAVITY, 	// Two steps with gravity (1+2)
        		player.getCenter().getY(), DELTA);
    }

    @Test
    public void moveJumpEndTest() {
        assertEquals(Player.HEIGHT / 2, player.getCenter().getY(), DELTA);
        player.move(Direction.JUMP);
        player.step();
        player.getCenter().addY(-1);
        player.step();
        assertEquals(Player.HEIGHT / 2, player.getCenter().getY(), DELTA);
    }
    
    /** Slide does nothing for now. */
    @Test
    public void moveSlideTest() {
    	Point3D before = new Point3D(player.getCenter());
    	player.move(Direction.SLIDE);
    	assertEquals(before, player.getCenter());
    }
    
}
