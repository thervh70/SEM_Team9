package nl.tudelft.ti2206.group9.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 */
public class Base64WriterTest {

    private Base64Writer writer;
    private static final String PATH =
            "nl/tudelft/ti2206/group9/util/saveGameWriterTest.ses";

    @Before
    public void setUp() {
        try {
            // The Resource class does not (yet) support output streams,
            // which means the src/test/resources is needed here.
            writer = new Base64Writer(new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("src/test/resources/" + PATH), "UTF-8"
                    )));
        } catch (UnsupportedEncodingException e) {
            fail("The Writers encountered an unsupported encoding");
        } catch (FileNotFoundException e) {
            fail("The file src/test/resources/" + PATH + " could not be found");
        }
    }

    @After
    public void tearDown() {
        try {
            writer.close();
        } catch (IOException e) {
            fail("Base64Writer could not be closed");
        }
    }

    @Test
    public void test() {
        try {
            final String input = "{\"boolean\": true, \"int\": 165}";
            writer.writeString(input);
            writer.flush();
            writer.close();
            final InputStream stream = Resource.getStream(PATH);
            final Base64Reader reader = new Base64Reader(stream);
            final String actualOut = reader.readString();
            reader.close();
            assertEquals(input, actualOut);
        } catch (IOException e) {
            fail("An IOException occured during the Base64WriterTest");
        }
    }
}
