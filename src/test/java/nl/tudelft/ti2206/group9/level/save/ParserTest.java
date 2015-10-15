package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;

import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.skin.Skin;
import nl.tudelft.ti2206.group9.level.State;

import org.junit.Test;

/**
 * @author Mathias
 */
public class ParserTest {

    /** Default folder to read savefiles from. */
    private static String fileFolder =
            "src/test/resources/nl/tudelft/ti2206/group9/level/save/";

    @Test
    public void testParser() {
        Skin.createUnlockedHashmap();
        Parser.loadGame(fileFolder + "saveGameParserTest.json");

        final int expectedCoins = 42;
        final int expectedHigh = 3560;
        final boolean iron = true;
        Skin.setUnlocked("Iron Man", iron);
        final boolean plank = true;
        Skin.setUnlocked("Plank", plank);

        assertEquals("Mathias", State.getPlayerName());
        assertEquals(expectedCoins, State.getCoins());
        assertEquals(expectedHigh, State.getHighscore());
        assertEquals(true, State.isSoundEnabled());
        assertEquals(false, Skin.getUnlocked("Andy"));
        assertEquals(true, Skin.getUnlocked("Noob"));
        assertEquals(iron, Skin.getUnlocked("Iron Man"));
        assertEquals(plank, Skin.getUnlocked("Plank"));
    }
}
