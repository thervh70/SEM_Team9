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
        Skin.createUnlockedHashmap();

        final String playerName = "Henk";
        State.setPlayerName(playerName);
        final int coins = 45;
        State.setCoins(coins);
        final int score = 3560;
        State.setHighscore(score);
        final boolean soundEnabled = true;
        State.setSoundEnabled(soundEnabled);
        final boolean iron = true;
        Skin.setUnlocked("Iron Man", iron);
        final boolean plank = true;
        Skin.setUnlocked("Plank", plank);

        Writer.saveGame(fileFolder + "saveGameWriterTest.json");
        Parser.loadGame(fileFolder + "saveGameWriterTest.json");

        assertEquals(playerName, State.getPlayerName());
        assertEquals(coins, State.getCoins());
        assertEquals(score, State.getHighscore());
        assertEquals(soundEnabled, State.isSoundEnabled());
        assertEquals(false, Skin.getUnlocked("Andy"));
        assertEquals(true, Skin.getUnlocked("Noob"));
        assertEquals(iron, Skin.getUnlocked("Iron Man"));
        assertEquals(plank, Skin.getUnlocked("Plank"));

    }
}
