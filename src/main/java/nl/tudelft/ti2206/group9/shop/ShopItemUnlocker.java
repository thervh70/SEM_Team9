package nl.tudelft.ti2206.group9.shop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is responsible for maintaining a Map containing
 * all data about which shop items are unlocked and which aren't.
 * The Map is kept separated from the Shop Items so that
 * the Map can be altered during the tests which involve unlocking
 * shop items. (If also the textures are loaded during the tests,
 * (graphics) errors will occur.)
 *
 * @author Mitchell
 */
public final class ShopItemUnlocker {


    /**
     * Map used to store which shop items are unlocked.
     */
    private static Map<String, Boolean> unlockedShopItemsMap =
            new ConcurrentHashMap<>();

    /**
     * Private constructor.
     * This so that this class can't be instantiated,
     * as it is a util class full of static methods.
     */
    private ShopItemUnlocker() { }

    /**
     * Creates a Map for all shop items that are unlocked.
     * This method can be used at the starting of the application.
     */
    public static void createUnlockedShopItemsMap() {
        createUnlockedSkinMap();
        createUnlockedSoundtrackMap();
    }

    /**
     * Alters the Map for the skins that are unlocked.
     * This Map is created for keeping the textures separated
     * from the constructor. Else the textures have to be loaded
     * every time the shop is started. During the tests as well.
     */
    private static void createUnlockedSkinMap() {
        unlockedShopItemsMap.put("Andy", false);
        unlockedShopItemsMap.put("B-man", false);
        unlockedShopItemsMap.put("Captain", false);
        unlockedShopItemsMap.put("Iron Man", false);
        unlockedShopItemsMap.put("Noob", true);
        unlockedShopItemsMap.put("Plank", false);
        unlockedShopItemsMap.put("Erik", false);
        unlockedShopItemsMap.put("Guido", false);
        unlockedShopItemsMap.put("Rini", false);
        unlockedShopItemsMap.put("Arie", false);
        unlockedShopItemsMap.put("Alberto", false);

    }

    /**
     * Alters the Map for the soundtrack's unlocked values.
     * Only the default soundtrack is unlocked by default.
     */
    public static void createUnlockedSoundtrackMap() {
        unlockedShopItemsMap.put("Animals", false);
        unlockedShopItemsMap.put("Radioactive", true);
        unlockedShopItemsMap.put("Duck Tales", false);
        unlockedShopItemsMap.put("Mario", false);
        unlockedShopItemsMap.put("Nyan Cat", false);
        unlockedShopItemsMap.put("Shake It Off", false);
    }

    /**
     * Gets the unlocked value of shop item, based on the name.
     *
     * @param name the Name of the shop item.
     * @return boolean unlocked or not.
     */
    public static boolean getUnlockedShopItem(final String name) {
        return unlockedShopItemsMap.get(name);
    }

    /**
     * Sets the unlocked value for a shop item of the shop.
     *
     * @param name     the Name of the shop item to change the value for.
     * @param unlocked new unlocked value.
     */
    public static void setUnlockedShopItem(final String name,
                                           final boolean unlocked) {
        unlockedShopItemsMap.replace(name, unlocked);
    }

    /** Reset all items to being locked. */
    public static void reset() {
        for (final String key : unlockedShopItemsMap.keySet()) {
            unlockedShopItemsMap.put(key, false);
        }
        unlockedShopItemsMap.put("Noob", true);
        unlockedShopItemsMap.put("Radioactive", true);
    }

}
