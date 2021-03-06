package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.level.entity.Coin;
import nl.tudelft.ti2206.group9.level.entity.Fence;
import nl.tudelft.ti2206.group9.level.entity.Log;
import nl.tudelft.ti2206.group9.level.entity.Pillar;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class TrackParserTest {

    private TrackParser parser;
    private final char[][] map = {
                    {'.', 'l', 'f'},
                    {'c', 'c', 'p'},
                    {'l', 'p', '.'}
                    };
    private TrackPart part;

    @Before
    public void setUp() {
        parser = new TrackParser();
    }

    @Test
    public void testParseCharMap() {
        part = parser.parseTrackPart(map);
        final int expectedLength = 3,
                expectedEntities = 7;
        assertEquals(expectedLength, part.getLength());
        assertEquals(expectedEntities, part.getEntities().size());

        final Class<?>[] types = new Class<?>[] {
                Log.class, Fence.class, Coin.class, Coin.class,
                Pillar.class, Log.class, Pillar.class
        };
        for (int i = 0; i < expectedEntities; i++) {
            assertEquals(types[i].getName(),
                    part.getEntities().get(i).getType().getName());
        }

    }

    @Test(expected = NullPointerException.class)
    public void testParseNullCharMap() {
        final char[][] nullMap = null;
        part = parser.parseTrackPart(nullMap);
    }

    @Test(expected = NullPointerException.class)
    public void testExceptionCheckFormat() {
        parser.checkFormat(null);
    }

    @Test
    public void testCheckFormat() {
        final List<String> list = new LinkedList<>();
        list.add("alpha");
        list.add("beta");
        parser.checkFormat(list);
    }

    @Test
    public void testExceptionParseTrackPart() {
        final String nullString = "fail.txt";
        parser.parseTrackPart(nullString);
    }
}
