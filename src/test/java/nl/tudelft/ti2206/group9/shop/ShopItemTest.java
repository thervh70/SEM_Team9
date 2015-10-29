package nl.tudelft.ti2206.group9.shop;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.shop.soundtrack.AnimalsSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.RadioactiveSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.DuckTalesSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.MarioSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.NyanCatSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.ShakeItOffSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.AbstractSoundtrack;

import org.junit.Test;

/**
 * Tests the soundtrack shop items in general.
 * Unfortunately the Skins can't be initiated in JUnit, see below.
 * @author Mitchell
 *
 */
public class ShopItemTest {

    // Soundtrack items
    private static AbstractSoundtrack animals = new AnimalsSoundtrack();
    private static AbstractSoundtrack radioactive = new RadioactiveSoundtrack();
    private static AbstractSoundtrack duckTales = new DuckTalesSoundtrack();
    private static AbstractSoundtrack mario = new MarioSoundtrack();
    private static AbstractSoundtrack nyanCat = new NyanCatSoundtrack();
    private static AbstractSoundtrack shakeItOff = new ShakeItOffSoundtrack();
    // Soundtrack prices
    private static final int ANIMALS_PRICE = 250;
    private static final int RADIOACTIVE_PRICE = 0;
    private static final int DUCKTALES_PRICE = 200;
    private static final int MARIO_PRICE = 50;
    private static final int NYANCAT_PRICE = 100;
    private static final int SHAKEITOFF_PRICE = 150;
    // Skins can't be tested, as they require graphics for their texture.

    @Test
    public void testConstructorName() {
        assertEquals(animals.getItemName(), "Animals");
        assertEquals(radioactive.getItemName(), "Radioactive");
        assertEquals(duckTales.getItemName(), "Duck Tales");
        assertEquals(mario.getItemName(), "Mario");
        assertEquals(nyanCat.getItemName(), "Nyan Cat");
        assertEquals(shakeItOff.getItemName(), "Shake It Off");
    }

    @Test
    public void testConstructorPrice() {
        assertEquals(animals.getItemPrice(), ANIMALS_PRICE);
        assertEquals(radioactive.getItemPrice(), RADIOACTIVE_PRICE);
        assertEquals(duckTales.getItemPrice(), DUCKTALES_PRICE);
        assertEquals(mario.getItemPrice(), MARIO_PRICE);
        assertEquals(nyanCat.getItemPrice(), NYANCAT_PRICE);
        assertEquals(shakeItOff.getItemPrice(), SHAKEITOFF_PRICE);
    }

    @Test
    public void testUnlockedHashMap() {
        ShopItemUnlocker.createUnlockedShopItemsMap();
        assertEquals(ShopItemUnlocker.getUnlockedShopItem("Animals"), false);
        assertEquals(ShopItemUnlocker.getUnlockedShopItem("Radioactive"), true);
        assertEquals(ShopItemUnlocker.getUnlockedShopItem("Duck Tales"), false);
        assertEquals(ShopItemUnlocker.getUnlockedShopItem("Mario"), false);
        assertEquals(ShopItemUnlocker.getUnlockedShopItem("Nyan Cat"), false);
        assertEquals(ShopItemUnlocker.getUnlockedShopItem(
                "Shake It Off"), false);
    }

}
