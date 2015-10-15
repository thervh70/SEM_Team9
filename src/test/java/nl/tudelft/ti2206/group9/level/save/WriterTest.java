package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;

import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.skin.*;
import nl.tudelft.ti2206.group9.level.State;

import org.junit.Test;

/**
 * @author Mathias
 */
public final class WriterTest {

    /** Default folder to write JSON savefiles to. */
    private static String fileFolder =
            "src/test/resources/nl/tudelft/ti2206/group9/level/save/";

    @Test
    public void testSaveGame() {
        Style.loadSkins();

        final String playerName = "Henk";
        State.setPlayerName(playerName);
        final int coins = 45;
        State.setCoins(coins);
        final int score = 3560;
        State.setHighscore(score);
        final boolean soundEnabled = true;
        State.setSoundEnabled(soundEnabled);
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

        Writer.saveGame(fileFolder + "saveGameWriterTest.json");
        Parser.loadGame(fileFolder + "saveGameWriterTest.json");

        assertEquals(playerName, State.getPlayerName());
        assertEquals(coins, State.getCoins());
        assertEquals(score, State.getHighscore());
        assertEquals(soundEnabled, State.isSoundEnabled());
        assertEquals(andy, Style.getAndy().getSkinUnlocked());
        assertEquals(boy, Style.getBoy().getSkinUnlocked());
        assertEquals(captain, Style.getCaptain().getSkinUnlocked());
        assertEquals(iron, Style.getIronMan().getSkinUnlocked());
        assertEquals(plank, Style.getPlank().getSkinUnlocked());

    }
}
