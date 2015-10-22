package nl.tudelft.ti2206.group9.level.save;

import nl.tudelft.ti2206.group9.util.Base64Reader;
import nl.tudelft.ti2206.group9.util.Base64Writer;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class Base64WriterTest {

    Base64Writer writer;
    Base64Reader reader;
    String path = "src/test/resources" +
            "/nl/tudelft/ti2206/group9/level/save/saveGameWriterTest.ses";
    String input = "{\"boolean\": true, \"int\": 165}";

    @Before
    public void setUp() {
        try {
            writer = new Base64Writer(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(path), "UTF-8"
                            )));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        try {
            writer.writeString(input);
            writer.flush();
            InputStream stream = new File(path).toURI().toURL().openStream();
            reader = new Base64Reader(
                    new BufferedReader(
                            new InputStreamReader(stream, "UTF-8"
                            )));
            String expectedOut = reader.readString();
            assertEquals(expectedOut, input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
