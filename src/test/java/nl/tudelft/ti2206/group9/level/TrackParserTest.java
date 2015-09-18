package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Fence;
import nl.tudelft.ti2206.group9.entities.Log;
import nl.tudelft.ti2206.group9.entities.Pillar;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Mathias
 */
public class TrackParserTest {

    TrackParser parser;
    char[][] map = {
                    {'.', 'l', 'f'},
                    {'c', 'c', 'p'},
                    {'l', 'p', '.'}
                    };
    TrackPart part;

    @Before
    public void setUp() {
        parser = new TrackParser();
    }

    @Test
    public void testParseCharMap() {
        part = parser.parseTrackPart(map);
        assertEquals(3, part.getLength());
        assertEquals(7, part.getEntities().size());

        assertTrue(part.getEntities().get(0) instanceof Log);
        assertTrue(part.getEntities().get(1) instanceof Fence);
        assertTrue(part.getEntities().get(2) instanceof Coin);
        assertTrue(part.getEntities().get(3) instanceof Coin);
        assertTrue(part.getEntities().get(4) instanceof Pillar);
        assertTrue(part.getEntities().get(5) instanceof Log);
        assertTrue(part.getEntities().get(6) instanceof Pillar);

    }

    @Test(expected=NullPointerException.class)
    public void testParseNullCharMap() {
        char[][] map = null;
        part = parser.parseTrackPart(map);
    }
}
