package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.shop.skin.Skin;

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
        Skin.createUnlockedSkinHashmap();

        final String playerName = "Henk";
        State.setPlayerName(playerName);
        final int coins = 45;
        State.setCoins(coins);
        final int score = 3560;
        State.setHighscore(score);

        Skin.setUnlockedSkin("Iron Man", true);
        Skin.setUnlockedSkin("Plank", true);
        State.setSoundtrackEnabled(false);
        State.setSoundEffectsEnabled(false);

        Writer.saveGame(fileFolder + "saveGameWriterTest.ses");
        Parser.loadGame(fileFolder + "saveGameWriterTest.ses");

        assertEquals(playerName, State.getPlayerName());
        assertEquals(coins, State.getCoins());
        assertEquals(score, State.getHighscore());

        assertEquals(false, Skin.getUnlockedSkin("Andy"));
        assertEquals(true, Skin.getUnlockedSkin("Noob"));
        assertEquals(true, Skin.getUnlockedSkin("Iron Man"));
        assertEquals(true, Skin.getUnlockedSkin("Plank"));
        assertEquals(false, State.isSoundtrackEnabled());
        assertEquals(false, State.isSoundEffectsEnabled());

    }
}
