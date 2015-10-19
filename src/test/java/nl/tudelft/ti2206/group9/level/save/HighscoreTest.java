package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter.Highscore;

import org.junit.Test;

public class HighscoreTest {

    @Test
    public final void testHighscore() {
        final Highscore h = new Highscore("Marie", 2);
        assertEquals("Marie", h.getUser());
        assertEquals(2, h.getScore());
    }

    @Test
    public final void testParse() {
        final Highscore h = new Highscore("Kees", 2);
        assertEquals(h, Highscore.parse(h.toString()));
        assertEquals(null, Highscore.parse("Highscore[, 42]"));
        assertEquals(null, Highscore.parse("Highscore[Kees, ]"));
        assertEquals(null, Highscore.parse("Highscore[, ]"));
        assertEquals(null, Highscore.parse("Highscore["));
    }

    @Test
    public final void testEquals() {
        final Highscore h = new Highscore("Piet", 2);
        assertTrue(h.equals(h));
        assertFalse(h.equals(null)); //NOPMD - intended equals(null)
        assertFalse(h.equals(""));
        assertFalse(h.equals(new Highscore("Jan", 2)));
        assertFalse(h.equals(new Highscore("Piet", 0)));
        assertFalse(h.equals(new Highscore(null, 2)));
        assertFalse(new Highscore(null, 2).equals(h));
        assertTrue(new Highscore(null, 2).equals(new Highscore(null, 2)));
        assertTrue(h.equals(new Highscore("Piet", 2)));
    }

    @Test
    public final void testHashCode() {
        final Highscore h1 = new Highscore("Jaap", 2);
        final Highscore h2 = new Highscore(null, 2);
        final int expected1 = 2301893;
        final int expected2 = 1023;
        assertEquals(expected1, h1.hashCode());
        assertEquals(expected2, h2.hashCode());
    }

}
