package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.StateTest;
import nl.tudelft.ti2206.group9.shop.ShopItemUnlocker;

import org.junit.Test;

/**
 * @author Mathias and Mitchell
 */
public final class WriterTest {

    /** Default folder to write JSON savefiles to. */
    private static String fileFolder =
            "src/test/resources/nl/tudelft/ti2206/group9/level/save/";

    private static final String NAME = "Henk";
    private static final int COINS = 45;
    private static final int SCORE = 3560;
    private static final double SOUNDTRACK_VOLUME = 0.7;
    private static final double SOUNDEFFECT_VOLUME = 0.3;

    @Test
    public void testSaveGame() {
        initState();

        Writer.saveGame(fileFolder + "saveGameWriterTest.ses");
        Parser.loadGame(fileFolder + "saveGameWriterTest.ses");

        assertState();
    }

    private void initState() {
        ShopItemUnlocker.createUnlockedShopItemsMap();

        State.setPlayerName(NAME);
        State.setCoins(COINS);
        State.setHighscore(SCORE);
        State.setSoundtrackVolume(SOUNDTRACK_VOLUME);
        State.setSoundEffectVolume(SOUNDEFFECT_VOLUME);

        ShopItemUnlocker.setUnlockedShopItem("Iron Man", true);
        ShopItemUnlocker.setUnlockedShopItem("Plank", true);
        ShopItemUnlocker.setUnlockedShopItem("Mario", true);
        State.setSoundtrackEnabled(false);
        State.setSoundEffectsEnabled(false);
    }

    private void assertState() {
        assertEquals(NAME, State.getPlayerName());
        assertEquals(COINS, State.getCoins());
        assertEquals(SCORE, State.getHighscore());
        assertEquals(SOUNDTRACK_VOLUME, State.getSoundtrackVolume(),
                StateTest.DELTA);
        assertEquals(SOUNDEFFECT_VOLUME, State.getSoundEffectVolume(),
                StateTest.DELTA);

        assertEquals(false, ShopItemUnlocker.getUnlockedShopItem("Andy"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Noob"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Iron Man"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Plank"));

        assertEquals(false, ShopItemUnlocker.getUnlockedShopItem("Animals"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Radioactive"));
        assertEquals(false, ShopItemUnlocker.getUnlockedShopItem("Duck Tales"));
        assertEquals(true, ShopItemUnlocker.getUnlockedShopItem("Mario"));
        assertEquals(false, ShopItemUnlocker.getUnlockedShopItem("Nyan Cat"));
        assertEquals(false, ShopItemUnlocker.
                getUnlockedShopItem("Shake It Off"));

        assertEquals(false, State.isSoundtrackEnabled());
        assertEquals(false, State.isSoundEffectsEnabled());
    }

}
