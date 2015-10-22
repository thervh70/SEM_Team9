package nl.tudelft.ti2206.group9.gui.skin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.PhongMaterial;
import nl.tudelft.ti2206.group9.gui.Style;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Maikel on 08/10/2015.
 */
@SuppressWarnings("restriction")
public class Skin {
    /**
     * IRON MAN currentSkin for player.
     */
    private static IronManSkin iron;

    /**
     * NOOB currentSkin for player, this is the starting currentSkin.
     */
    private static NoobSkin noob;

    /**
     * CAPTAIN currentSkin for the player.
     */
    private static CaptainSkin captain;

    /**
     * PLANK currentSkin for the player.
     */
    private static PlankSkin plank;

    /**
     * BOY currentSkin for the player.
     */
    private static BoySkin boy;

    /**
     * ANDY currentSkin for the player.
     */
    private static AndySkin andy;
    /** Skin to be used. */
    private static Skin currentSkin;
    /**
     * The price of this currentSkin in the shop.
     */
    private final int skinPrice;

    /**
     * The name to display in the shop.
     */
    private final String skinName;

    /**
     * The real material used by this currentSkin.
     */
    private final PhongMaterial skinMaterial;

    /**
     * HashMap used to store which skins are unlocked.
     */
    private static Map<String, Boolean> unlockedMap = new ConcurrentHashMap<>();

    /**
     * Constructor for the currentSkin.
     * It calls the Style.loadPlayerTexture
     * to load the image into the PhongMaterial
     * with the name "texture_[texture_name].png".
     *
     * @param price       Price of this currentSkin in shop.
     * @param name        Name to display.
     * @param textureName Name of texture.
     */
    public Skin(final int price,
                final String name, final String textureName) {
        skinPrice = price;
        skinName = name;
        skinMaterial = Style.loadPlayerTexture(textureName);
    }

    /**
     * Getter for the current currentSkin.
     * @return The currentSkin.
     */
    public static Skin getCurrentSkin() {
        return currentSkin;
    }

    /**
     * If new skins are bought and applied it can be done via this setter.
     * @param newSkin The new currentSkin.
     */
    public static void setCurrentSkin(final Skin newSkin) {
        currentSkin = newSkin;
    }

    /**
     * Getter for the price.
     *
     * @return price
     */
    public int getSkinPrice() {
        return skinPrice;
    }

    /**
     * Getter for the name.
     *
     * @return name
     */
    public String getSkinName() {
        return skinName;
    }

    /**
     * Getter for the PhongMaterial.
     *
     * @return material
     */
    public PhongMaterial getSkinMaterial() {
        return skinMaterial;
    }

    /**
     * Creating the HashMap for the currentSkin's unlocked values.
     */
    public static void createUnlockedHashmap() {
        unlockedMap.put("Andy", false);
        unlockedMap.put("B-man", false);
        unlockedMap.put("Captain", false);
        unlockedMap.put("Iron Man", false);
        unlockedMap.put("Noob", true);
        unlockedMap.put("Plank", false);
    }

    /**
     * Get the unlocked value for a currentSkin.
     * @param skinName Name of currentSkin.
     * @return boolean unlocked or not.
     */
    public static boolean getUnlocked(final String skinName) {
        return unlockedMap.get(skinName);
    }

    /**
     * Set the unlocked value for a currentSkin.
     *
     * @param skinName Skin to change value for.
     * @param unlocked new unlocked value.
     */
    public static void setUnlocked(final String skinName,
                                   final boolean unlocked) {
        unlockedMap.replace(skinName, unlocked);
    }

    /**
     * Method that creates all the skins.
     */
    public static void loadSkins() {
        iron = new IronManSkin();
        captain = new CaptainSkin();
        andy = new AndySkin();
        noob = new NoobSkin();
        boy = new BoySkin();
        plank = new PlankSkin();
    }


    /**
     * Method that creates all the skins.
     *
     * @return ObservableList<Skin> Returns list with skins.
     */
    public static ObservableList<Skin> loadSkinsToList() {
        final ObservableList<Skin> list = FXCollections.observableArrayList();
        list.addAll(noob, andy, boy, iron, captain, plank);
        return list;
    }

}
