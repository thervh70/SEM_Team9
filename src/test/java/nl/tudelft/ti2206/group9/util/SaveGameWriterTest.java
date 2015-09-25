package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public final class SaveGameWriterTest {

    /** Default folder to write JSON savefiles to. */
	private static String fileFolder =
	        "src/main/resources/nl/tudelft/ti2206/group9/util/";

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

        SaveGameWriter.saveGame(fileFolder + "saveGameWriterTest.json");
        SaveGameParser.loadGame(fileFolder + "saveGameWriterTest.json");

        assertEquals(playerName, State.getPlayerName());
        assertEquals(coins, State.getCoins());
        assertEquals(score, State.getHighscore());
        assertEquals(soundEnabled, State.isSoundEnabled());
    }
}
