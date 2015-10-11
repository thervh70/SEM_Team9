package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.level.State;

import org.junit.Test;

/**
 * @author Mathias
 */
public final class WriterTest {

    /** Default folder to write JSON savefiles to. */
    private static String fileFolder =
            "src/test/resources/nl/tudelft/ti2206/group9/util/";

    @Test
    public void testSaveGame() {
        final String playerName = "Henk";
        State.setPlayerName(playerName);
        final int coins = 45;
        State.setCoins(coins);
        final int score = 34567;
        State.setHighscore(score);
        final boolean soundEnabled = false;
        State.setSoundEnabled(soundEnabled);

        Writer.saveGame(fileFolder + "saveGameWriterTest.json");
        Parser.loadGame(fileFolder + "saveGameWriterTest.json");

        assertEquals(playerName, State.getPlayerName());
        assertEquals(coins, State.getCoins());
        assertEquals(score, State.getHighscore());
        assertEquals(soundEnabled, State.isSoundEnabled());
    }
}
