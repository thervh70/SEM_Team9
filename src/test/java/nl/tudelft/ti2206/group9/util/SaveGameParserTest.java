package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class SaveGameParserTest {

    private static final double DELTA = 0.0000001;

    @Test
    public void testParser() {
        SaveGameParser.loadGame("firstSaveGame.json");
        assertEquals(State.getPlayerName(), "Mathias");
        assertEquals(State.getCoins(), 42);
        assertEquals(State.getHighscore(), 2560);
        assertEquals(State.isSoundEnabled(), true);
    }
}
