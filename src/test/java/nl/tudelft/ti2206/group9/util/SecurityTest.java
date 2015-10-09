package nl.tudelft.ti2206.group9.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class SecurityTest {

    @Test
    public void testEncypt() {
        assertEquals("YWJj", Writer.encrypt("abc"));
        assertEquals("MTIz", Writer.encrypt("123"));
        assertEquals("e31bXTosLg==", Writer.encrypt("{}[]:,."));
        assertEquals("Ig==", Writer.encrypt("\""));
    }

    @Test
    public void testDecrypt() {
        assertEquals("abc", Parser.decrypt("YWJj"));
        assertEquals("123", Parser.decrypt("MTIz"));
        assertEquals("{}[]:,.", Parser.decrypt("e31bXTosLg=="));
        assertEquals("\"", Parser.decrypt("Ig=="));
    }

    @Test
    public void testFullJSON() {
        String JSON = "{\"test\":0}";
        assertEquals("eyJ0ZXN0IjowfQ==", Writer.encrypt(JSON));
    }
}
