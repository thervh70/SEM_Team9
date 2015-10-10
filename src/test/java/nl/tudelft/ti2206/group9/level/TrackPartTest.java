package nl.tudelft.ti2206.group9.level;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import nl.tudelft.ti2206.group9.level.TrackPart.Node;
import nl.tudelft.ti2206.group9.level.entity.Coin;
import nl.tudelft.ti2206.group9.level.entity.Log;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 */
public class TrackPartTest {

    private TrackPart part;

    private Node coin;
    private Node log; //NOPMD - confuses Log with Logger

    @Before
    public void setUp() {
        part = new TrackPart();
        coin = new Node(Coin.class, Point3D.ZERO);
        log = new Node(Log.class, Point3D.UNITX);
    }

    @Test
    public void testGetEntities() {
        part.addEntity(coin);
        part.addEntity(log);
        final List<Node> actual = part.getEntities();
        final List<Node> expected = new LinkedList<Node>();
        expected.add(coin);
        expected.add(log);

        assertEquals(2, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void addEntity() {
        assertEquals(0, part.getEntities().size());

        part.addEntity(coin);

        assertEquals(1, part.getEntities().size());
        assertEquals(coin, part.getEntities().get(0));
    }

    @Test
    public void removeEntity() {
        part.addEntity(coin);

        assertEquals(1, part.getEntities().size());
        assertEquals(coin, part.getEntities().get(0));

        part.removeEntity(coin);

        assertEquals(0, part.getEntities().size());
    }

    @Test
    public void testGetLength() {
        part.setLength(2);
        assertEquals(2, part.getLength());

        part.setLength(-1);
        assertEquals(-1, part.getLength());
    }
}
