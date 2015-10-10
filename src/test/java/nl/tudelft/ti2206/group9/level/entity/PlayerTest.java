package nl.tudelft.ti2206.group9.level.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.group9.util.Direction;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 */
public class PlayerTest {

    private static final double DELTA = 0.00000001;
    private static final double HACCEL = 0.02;
    private static final double SLOW = 5;

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
        player.step();
        assertEquals(-HACCEL, player.getCenter().getX(), DELTA);
        player.step();
        assertEquals(-(1 + 2) * HACCEL, player.getCenter().getX(), DELTA);
        player.getCenter().setX(-1 + HACCEL);
        player.step();
        assertEquals(-1 + HACCEL - HACCEL / SLOW, player.getCenter().getX(),
                DELTA);
    }

    @Test
    public void moveRightTest() {
        assertEquals(0, player.getMoveLane(), DELTA);
        player.move(Direction.RIGHT);
        assertEquals(1, player.getMoveLane(), DELTA);
        player.step();
        assertEquals(HACCEL, player.getCenter().getX(), DELTA);
        player.step();
        assertEquals((1 + 2) * HACCEL, player.getCenter().getX(), DELTA);
        player.getCenter().setX(1 - HACCEL);
        player.step();
        assertEquals(1 - HACCEL + HACCEL / SLOW, player.getCenter().getX(),
                DELTA);
    }

    @Test
    public void moveLeftOffTrackTest() {
        player.move(Direction.LEFT);
        assertEquals(-1, player.getMoveLane(), DELTA);
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
        assertEquals(Player.HEIGHT / 2 + Player.JUMP_SPEED - Player.GRAVITY,
                player.getCenter().getY(), DELTA);
    }

    @Test
    public void moveDoubleJumpTest() {
        assertEquals(Player.HEIGHT / 2, player.getCenter().getY(), DELTA);
        player.move(Direction.JUMP);
        player.step();
        assertEquals(Player.HEIGHT / 2 + Player.JUMP_SPEED - Player.GRAVITY,
                player.getCenter().getY(), DELTA);
        player.move(Direction.JUMP);
        player.step();
        assertEquals(Player.HEIGHT / 2             // Original height
                + 2 * Player.JUMP_SPEED             // Two steps to get higher
                - (1 + 2) * Player.GRAVITY,     // Two steps with gravity (1+2)
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

    @Test
    public void moveSlideTest() {
        final double initSlideSpeed = -1 * 2 * (Player.HEIGHT
                - Player.SLIDE_MIN_HEIGHT) / (Player.SLIDE_LENGTH / 2);
        assertEquals(Player.HEIGHT, player.getSize().getY(), DELTA);
        player.move(Direction.SLIDE);
        player.step();
        assertEquals(Player.HEIGHT + initSlideSpeed, player.getSize().getY(),
                DELTA);
        player.getSize().addY(2);
        player.step();
        player.step();
        player.step();
        assertEquals(Player.HEIGHT, player.getSize().getY(), DELTA);
    }

    @Test
    public void moveDoubleSlideTest() {
        final double initSlideSpeed = -1 * 2 * (Player.HEIGHT
                - Player.SLIDE_MIN_HEIGHT) / (Player.SLIDE_LENGTH / 2);
        final double slideAccel = 2 * (Player.HEIGHT - Player.SLIDE_MIN_HEIGHT)
                / (Player.SLIDE_LENGTH / 2) / (Player.SLIDE_LENGTH / 2);
        assertEquals(Player.HEIGHT, player.getSize().getY(), DELTA);
        player.move(Direction.SLIDE);
        player.step();
        assertEquals(Player.HEIGHT + initSlideSpeed, player.getSize().getY(),
                DELTA);
        player.move(Direction.SLIDE);
        player.step();
        assertEquals(Player.HEIGHT + 2 * initSlideSpeed + slideAccel,
                player.getSize().getY(), DELTA);
    }

    @Test
    public void slideJumpTest() {
        player.move(Direction.SLIDE);
        player.move(Direction.JUMP);
        player.step();
        assertEquals(player.getSize().getY() / 2, player.getCenter().getY(),
                DELTA);
    }

    @Test
    public void jumpSlideTest() {
        player.move(Direction.JUMP);
        player.move(Direction.SLIDE);
        player.step();
        assertEquals(Player.HEIGHT, player.getSize().getY(), DELTA);
    }

}
