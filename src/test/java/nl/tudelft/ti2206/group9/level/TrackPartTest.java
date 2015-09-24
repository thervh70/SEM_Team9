package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Log;
import nl.tudelft.ti2206.group9.util.Point3D;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class TrackPartTest {

    private TrackPart part;

    private Coin coin;
    private Log log; //NOPMD - confuses Log with Logger

    @Before
    public void setUp() {
        part = new TrackPart();
        coin = new Coin(new Point3D(0, 0, 0));
        log = new Log(new Point3D(1, 0, 0));
    }

    @Test
    public void testGetEntities() {
        part.addEntity(coin);
        part.addEntity(log);
        final List<AbstractEntity> actual = part.getEntities();
        final List<AbstractEntity> expected = new LinkedList<AbstractEntity>();
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
