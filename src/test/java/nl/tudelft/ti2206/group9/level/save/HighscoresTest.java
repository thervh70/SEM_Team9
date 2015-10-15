package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.level.save.Highscores.Highscore;

import org.junit.Test;

public class HighscoresTest {

    @Test
    public final void testHighscoreParse() {
        final Highscore h = new Highscore("Kees", 2);
        assertEquals(h, Highscore.parse(h.toString()));
        assertEquals(null, Highscore.parse("Highscore[, 42]"));
        assertEquals(null, Highscore.parse("Highscore[Kees, ]"));
        assertEquals(null, Highscore.parse("Highscore[, ]"));
    }

}
