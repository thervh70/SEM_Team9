package nl.tudelft.ti2206.group9.shop;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.shop.soundtrack.AnimalsSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.DefaultSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.DuckTalesSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.MarioSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.NyanCatSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.ShakeItOffSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.Soundtrack;

import org.junit.Test;

/**
 * Tests the soundtrack shop items in general.
 * Unfortunately the Skins can't be initiated in JUnit, see below.
 * @author Mitchell
 *
 */
public class ShopItemTest {

    private static Soundtrack animals = new AnimalsSoundtrack();
    private static Soundtrack radioactive = new DefaultSoundtrack();
    private static Soundtrack duckTales = new DuckTalesSoundtrack();
    private static Soundtrack mario = new MarioSoundtrack();
    private static Soundtrack nyanCat = new NyanCatSoundtrack();
    private static Soundtrack shakeItOff = new ShakeItOffSoundtrack();
    // Skins can't be tested, as they require graphics for their texture.


    @Test
    public void testConstructorName() {
        assertEquals(animals.getItemName(), "Animals");
        assertEquals(radioactive.getItemName(), "Default");
        assertEquals(duckTales.getItemName(), "Duck Tales");
        assertEquals(mario.getItemName(), "Mario");
        assertEquals(nyanCat.getItemName(), "Nyan Cat");
        assertEquals(shakeItOff.getItemName(), "Shake It Off");
    }

    @Test
    public void testConstructorPrice() {
        assertEquals(animals.getItemPrice(), 30);
        assertEquals(radioactive.getItemPrice(), 0);
        assertEquals(duckTales.getItemPrice(), 20);
        assertEquals(mario.getItemPrice(), 100);
        assertEquals(nyanCat.getItemPrice(), 90);
        assertEquals(shakeItOff.getItemPrice(), 70);
    }

    @Test
    public void testUnlockedHashMap() {
        Soundtrack.createUnlockedSoundtrackHashmap();
        assertEquals(Soundtrack.getUnlockedSoundtrack("Animals"), false);
        assertEquals(Soundtrack.getUnlockedSoundtrack("Default"), true);
        assertEquals(Soundtrack.getUnlockedSoundtrack("Duck Tales"), false);
        assertEquals(Soundtrack.getUnlockedSoundtrack("Mario"), false);
        assertEquals(Soundtrack.getUnlockedSoundtrack("Nyan Cat"), false);
        assertEquals(Soundtrack.getUnlockedSoundtrack("Shake It Off"), false);
    }

}
