package nl.tudelft.ti2206.group9.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class SecurityTest {

    @Test
    public void testEncypt() {
        assertEquals("YWJj", Security.encrypt("abc"));
        assertEquals("MTIz", Security.encrypt("123"));
        assertEquals("e31bXTosLg==", Security.encrypt("{}[]:,."));
        assertEquals("Ig==", Security.encrypt("\""));
    }

    @Test
    public void testDecrypt() {
        assertEquals("abc", Security.decrypt("YWJj"));
        assertEquals("123", Security.decrypt("MTIz"));
        assertEquals("{}[]:,.", Security.decrypt("e31bXTosLg=="));
        assertEquals("\"", Security.decrypt("Ig=="));
    }
}
