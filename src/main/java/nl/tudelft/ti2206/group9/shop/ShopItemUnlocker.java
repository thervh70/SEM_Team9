package nl.tudelft.ti2206.group9.shop;

import java.util.HashMap;

/**
 * This class is responsible for maintaining a HashMap containing
 * all data about which shop items are unlocked and which aren't.
 * The HashMap is kept separated from the Shop Items so that
 * the HashMap can be altered during the tests which involve unlocking
 * shop items. (If also the textures are loaded during the tests,
 * (graphics) errors will occur.)
 *
 * @author Mitchell
 */
public final class ShopItemUnlocker {

    /**
     * HashMap used to store which shop items are unlocked.
     */
    private static HashMap<String, Boolean> unlockedShopItemsMap =
            new HashMap<>();

    /**
     * Private constructor.
     * This so that this class can't be instantiated,
     * as it is a util class full of static methods.
     */
    private ShopItemUnlocker() {
    }

    /**
     * Creates a HashMap for all shop items that are unlocked.
     * This method can be used at the starting of the application.
     */
    public static void createUnlockedShopItemsHashMap() {
        createUnlockedSkinHashMap();
        createUnlockedSoundtrackHashMap();
    }

    /**
     * Alters a HashMap for the skins that are unlocked.
     * This HashMap is created for keeping the textures separated
     * from the constructor. Else the textures have to be loaded
     * every time the shop is started. During the tests as well.
     */
    private static void createUnlockedSkinHashMap() {
        unlockedShopItemsMap.put("Andy", false);
        unlockedShopItemsMap.put("B-man", false);
        unlockedShopItemsMap.put("Captain", false);
        unlockedShopItemsMap.put("Iron Man", false);
        unlockedShopItemsMap.put("Noob", true);
        unlockedShopItemsMap.put("Plank", false);
    }

    /**
     * Alters the HashMap for the soundtrack's unlocked values.
     * Only the default soundtrack is unlocked by default.
     */
    public static void createUnlockedSoundtrackHashMap() {
        unlockedShopItemsMap.put("Animals", false);
        unlockedShopItemsMap.put("Default", true);
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

}
