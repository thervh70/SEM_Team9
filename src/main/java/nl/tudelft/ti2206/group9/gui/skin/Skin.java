package nl.tudelft.ti2206.group9.gui.skin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.PhongMaterial;
import nl.tudelft.ti2206.group9.gui.Style;

import java.util.HashMap;

/**
 * @author Maikel on 08/10/2015.
 */
@SuppressWarnings("restriction")
public class Skin {
    /**
     * IRON MAN skin for player.
     */
    private static IronManSkin iron;

    /**
     * NOOB skin for player, this is the starting skin.
     */
    private static NoobSkin noob;

    /**
     * CAPTAIN skin for the player.
     */
    private static CaptainSkin captain;

    /**
     * PLANK skin for the player.
     */
    private static PlankSkin plank;

    /**
     * BOY skin for the player.
     */
    private static BoySkin boy;

    /**
     * ANDY skin for the player.
     */
    private static AndySkin andy;
    /**
     * The price of this skin in the shop.
     */
    private final int skinPrice;

    /**
     * The name to display in the shop.
     */
    private final String skinName;

    /**
     * The real material used by this skin.
     */
    private final PhongMaterial skinMaterial;

    /**
     * HashMap used to store which skins are unlocked.
     */
    private static HashMap<String, Boolean> unlockedMap = new HashMap<>();

    /**
     * Constructor for the skin.
     * It calls the Style.loadPlayerTexture
     * to load the image into the PhongMaterial
     * with the name "texture_[texture_name].png".
     *
     * @param price       Price of this skin in shop.
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
     * Creating the HashMap for the skin's unlocked values.
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
     * Get the unlocked value for a skin.
     * @param skinName Name of skin.
     * @return boolean unlocked or not.
     */
    public static boolean getUnlocked(final String skinName) {
        return unlockedMap.get(skinName);
    }

    /**
     * Set the unlocked value for a skin.
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

    /**
     * Simple getter for IronManSkin.
     *
     * @return Skin
     */
    public static IronManSkin getIronMan() {
        return iron;
    }

    /**
     * Simple getter for NoobSkin.
     *
     * @return Skin
     */
    public static NoobSkin getNoob() {
        return noob;
    }

    /**
     * Simple getter for CaptainSkin.
     *
     * @return Skin
     */
    public static CaptainSkin getCaptain() {
        return captain;
    }

    /**
     * Simple getter for PlankSkin.
     *
     * @return Skin
     */
    public static PlankSkin getPlank() {
        return plank;
    }

    /**
     * Simple getter for BoySkin.
     *
     * @return Skin
     */
    public static BoySkin getBoy() {
        return boy;
    }

    /**
     * Simple getter for AndySkin.
     *
     * @return Skin
     */
    public static AndySkin getAndy() {
        return andy;
    }

}
