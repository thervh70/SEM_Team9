package nl.tudelft.ti2206.group9.server;

import static nl.tudelft.ti2206.group9.server.HighscoreDatabase.query;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HighscoreDatabaseTest {

    @Before
    public final void setUp() {
        HighscoreDatabase.reset();
    }

    @Test
    public final void testGoodWeather() {
        final int[] scores = {42, 21, 84, 63};
        final String[] names = {"Kees", "Piet", "Jaap", "Piet"};

        for (int i = 0; i < names.length; i++) {
            assertEquals("SUCCESS", query("add " + names[i] + " " + scores[i]));
        }
        assertEquals("", query("get global -1"));
        assertEquals("", query("get user Kees -1"));

        assertEquals("Highscore[Jaap, 84]\nHighscore[Piet, 63]\n"
                + "Highscore[Kees, 42]", query("get global 5"));

        assertEquals("Highscore[Jaap, 84]\nHighscore[Piet, 63]",
                query("get global 2"));

        assertEquals("Highscore[Piet, 63]",
                query("get user Piet 1"));
        assertEquals("Highscore[Piet, 63]\nHighscore[Piet, 21]",
                query("get user Piet 2"));
        assertEquals("Highscore[Piet, 63]\nHighscore[Piet, 21]",
                query("get user Piet 3"));
    }

    @Test
    public void testBadWeather() {
        assertEquals("USAGE add|get <args>", query(""));
        assertEquals("USAGE add|get <args>", query("doNothingOrSomething"));

        assertEquals("USAGE get global|user <args>", query("get"));
        assertEquals("USAGE get global|user <args>", query("get nothing"));
        assertEquals("USAGE get global <amount:int>", query("get global"));
        assertEquals("USAGE get user <name:string> <amount:int>",
                query("get user"));
        assertEquals("USAGE get user Kees <amount:int>",
                query("get user Kees"));

        assertEquals("USAGE add <name:string> <score:int>", query("add"));
        assertEquals("USAGE add Kees <score:int>", query("add Kees"));
    }

}
