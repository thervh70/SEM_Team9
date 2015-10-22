package nl.tudelft.ti2206.group9.level.save;

import nl.tudelft.ti2206.group9.util.Base64Reader;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class Base64ReaderTest {

    InputStream stream;
    Base64Reader reader;
    String path = "src/test/resources/" +
            "nl/tudelft/ti2206/group9/level/save/saveGameParserTest.ses";

    String expectedResult = "{\"settings\":{\"soundtracksettings\":" +
            "{\"soundtrackEnabled\":true},\"soundEffectssettings\":" +
            "{\"soundEffectsEnabled\":true}},\"coins\":28,\"highscore\":" +
            "{\"score\":3092},\"iron\":false,\"playername\":" +
            "\"saveGameParserTest\",\"captain\":false,\"boy\":" +
            "false,\"plank\":false,\"andy\":true}";

    @Before
    public void setUp() {
        final URL pathURL;
        try {
            pathURL = new File(path).toURI().toURL();
            stream = pathURL.openStream();
            reader = new Base64Reader(
                    new BufferedReader(
                            new InputStreamReader(stream, "UTF-8")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadString() {
        String result = reader.readString();
        assertEquals(expectedResult, result);
    }

}
