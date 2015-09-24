package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public final class SaveGameWriterTest {

    public final static double DELTA = 0.0000001;


    @Test
    public void testSaveGame() {
        String playerName = "Henk";
        State.setPlayerName(playerName);
        int coins = 45;
        State.setCoins(coins);
        double score = 34567;
        State.setScore(score);
        boolean soundEnabled = false;
        State.setSoundEnabled(soundEnabled);

        SaveGameWriter.saveGame("testWriteRead.json");
        SaveGameParser.loadGame("testWriteRead.json");

        assertEquals(playerName, State.getPlayerName());
        assertEquals(coins, State.getCoins());
        assertEquals(score, State.getScore(), DELTA);
        assertEquals(soundEnabled, State.isSoundEnabled());
    }
}
