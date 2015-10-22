package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.shop.ShopItemUnlocker;

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
        ShopItemUnlocker.createUnlockedShopItemsHashMap();

        final String playerName = "Henk";
        State.setPlayerName(playerName);
        final int coins = 45;
        State.setCoins(coins);
        final int score = 3560;
        State.setHighscore(score);

        ShopItemUnlocker.setUnlockedShopItem("Iron Man", true);
        ShopItemUnlocker.setUnlockedShopItem("Plank", true);
        ShopItemUnlocker.setUnlockedShopItem("Mario", true);
        State.setSoundtrackEnabled(false);
        State.setSoundEffectsEnabled(false);

        Writer.saveGame(fileFolder + "saveGameWriterTest.ses");
        Parser.loadGame(fileFolder + "saveGameWriterTest.ses");

        assertEquals(playerName, State.getPlayerName());
        assertEquals(coins, State.getCoins());
        assertEquals(score, State.getHighscore());

        assertEquals(false, ShopItemUnlocker.getUnlockedShopItem("Andy"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Noob"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Iron Man"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Plank"));

        assertEquals(false, ShopItemUnlocker.getUnlockedShopItem("Animals"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Default"));
        assertEquals(false, ShopItemUnlocker.getUnlockedShopItem("Duck Tales"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Mario"));
        assertEquals(false, ShopItemUnlocker.getUnlockedShopItem("Nyan Cat"));
        assertEquals(false, ShopItemUnlocker.
                getUnlockedShopItem("Shake It Off"));

        assertEquals(false, State.isSoundtrackEnabled());
        assertEquals(false, State.isSoundEffectsEnabled());

    }
}
