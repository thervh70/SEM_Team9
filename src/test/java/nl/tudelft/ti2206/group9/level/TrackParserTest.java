package nl.tudelft.ti2206.group9.level;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class TrackParserTest {

    TrackParser parser;
    char[][] map = {
                    {'.', '.', '.'},
                    {'c', 'c', 'c'}
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
        assertEquals(3, part.getEntities().size());
    }

    @Test(expected=NullPointerException.class)
    public void testParseNullCharMap() {
        char[][] map = null;
        part = parser.parseTrackPart(map);
    }
}
