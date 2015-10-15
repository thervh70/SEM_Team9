package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;

import nl.tudelft.ti2206.group9.gui.Style;
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
        Style.loadSkins();
        Parser.loadGame(fileFolder + "saveGameParserTest.json");

        final int expectedCoins = 42;
        final int expectedHigh = 3560;
        final boolean andy = false;
        Style.getAndy().setSkinUnlocked(andy);
        final boolean boy = false;
        Style.getBoy().setSkinUnlocked(boy);
        final boolean captain = false;
        Style.getCaptain().setSkinUnlocked(captain);
        final boolean iron = true;
        Style.getIronMan().setSkinUnlocked(iron);
        final boolean plank = true;
        Style.getPlank().setSkinUnlocked(plank);

        assertEquals("Mathias", State.getPlayerName());
        assertEquals(expectedCoins, State.getCoins());
        assertEquals(expectedHigh, State.getHighscore());
        assertEquals(true, State.isSoundEnabled());
        assertEquals(andy, Style.getAndy().getSkinUnlocked());
        assertEquals(boy, Style.getBoy().getSkinUnlocked());
        assertEquals(captain, Style.getCaptain().getSkinUnlocked());
        assertEquals(iron, Style.getIronMan().getSkinUnlocked());
        assertEquals(plank, Style.getPlank().getSkinUnlocked());
    }
}
