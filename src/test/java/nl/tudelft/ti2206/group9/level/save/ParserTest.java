package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.level.State;

import org.junit.Test;

/**
 * @author Mathias and Mitchell
 */
public class ParserTest {

    /** Default folder to read savefiles from. */
    private static String fileFolder =
            "src/test/resources/nl/tudelft/ti2206/group9/level/save/";

    @Test
    public void testParser() {
        Parser.loadGame(fileFolder + "saveGameParserTest.ses");

        final int expectedCoins = 42;
        final int expectedHigh = 2560;
        assertEquals("Mathias", State.getPlayerName());
        assertEquals(expectedCoins, State.getCoins());
        assertEquals(expectedHigh, State.getHighscore());
        assertEquals(true, State.isSoundtrackEnabled());
        assertEquals(true, State.isSoundEffectsEnabled());
    }
}
