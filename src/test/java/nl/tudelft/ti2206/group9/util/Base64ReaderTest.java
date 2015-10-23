package nl.tudelft.ti2206.group9.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 */
public class Base64ReaderTest {

    private Base64Reader reader;
    private static final String PATH =
            "nl/tudelft/ti2206/group9/util/saveGameParserTest.ses";

    private final String expectedResult =
            "{\"settings\":{\"soundtracksettings\":"
            + "{\"soundtrackEnabled\":true},\"soundEffectssettings\":"
            + "{\"soundEffectsEnabled\":true}},\"coins\":28,\"highscore\":"
            + "{\"score\":3092},\"iron\":false,\"playername\":"
            + "\"saveGameParserTest\",\"captain\":false,\"boy\":"
            + "false,\"plank\":false,\"andy\":true}";

    @Before
    public void setUp() {
        try {
            final InputStream stream = Resource.getStream(PATH);
            reader = new Base64Reader(
                    new BufferedReader(
                            new InputStreamReader(stream, "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            fail("The Encoding was not supported by the Base64Reader!");
        }
    }

    @After
    public void tearDown() {
        try {
            reader.close();
        } catch (IOException e) {
            fail("Base64Reader could not be closed");
        }
    }

    @Test
    public void testReadString() {
        final String result = reader.readString();
        assertEquals(expectedResult, result);
    }

}
