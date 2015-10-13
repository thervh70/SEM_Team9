package nl.tudelft.ti2206.group9.level.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import nl.tudelft.ti2206.group9.level.CollisionHandler;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.StateTest;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.Before;
import org.junit.Test;

public class CollisionTest {

    private Player player;
    private Coin coin;
    private AbstractObstacle obstacle;
    private CollisionHandler collisionHandler;

    @Before
    public void setUp() throws Exception {
        State.resetAll();
        player = new Player();
        coin = new Coin(Point3D.ZERO);
        obstacle = new Log(Point3D.ZERO, Point3D.UNITCUBE);
        collisionHandler = State.getTrack().getCollisions();
    }

    @Test
    public void testCollisionPlayerCoin() {
        final double oldscore = State.getScore();
        final int oldcoins = State.getCoins();
        collisionHandler.collide(player, coin);
        assertEquals(oldscore + Coin.VALUE, State.getScore(), StateTest.DELTA);
        assertEquals(oldcoins + 1, State.getCoins());
    }

    @Test
    public void testCollisionCoinCoin() {
        final double oldscore = State.getScore();
        final int oldcoins = State.getCoins();
        collisionHandler.collide(new Coin(Point3D.ZERO), coin);
        assertEquals(oldscore, State.getScore(), StateTest.DELTA);
        assertEquals(oldcoins, State.getCoins());
    }

    @Test
    public void testCollisionPlayerObstacle() {
        collisionHandler.collide(player, obstacle);
        assertFalse(player.isAlive());
    }

}
