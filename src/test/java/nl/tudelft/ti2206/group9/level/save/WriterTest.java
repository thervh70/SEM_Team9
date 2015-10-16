package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.gui.skin.Skin;
import nl.tudelft.ti2206.group9.level.State;

import org.junit.Test;

/**
 * @author Mathias and Mitchell
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

        Skin.setUnlocked("Iron Man", true);
        Skin.setUnlocked("Plank", true);
        State.setSoundtrackEnabled(false);
        State.setSoundEffectsEnabled(false);

        Writer.saveGame(fileFolder + "saveGameWriterTest.ses");
        Parser.loadGame(fileFolder + "saveGameWriterTest.ses");

        assertEquals(playerName, State.getPlayerName());
        assertEquals(coins, State.getCoins());
        assertEquals(score, State.getHighscore());

        assertEquals(false, Skin.getUnlocked("Andy"));
        assertEquals(true, Skin.getUnlocked("Noob"));
        assertEquals(true, Skin.getUnlocked("Iron Man"));
        assertEquals(true, Skin.getUnlocked("Plank"));
        assertEquals(false, State.isSoundtrackEnabled());
        assertEquals(false, State.isSoundEffectsEnabled());

    }
}
