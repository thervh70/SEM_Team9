package nl.tudelft.ti2206.group9.shop.skin;

import java.util.HashMap;

import javafx.scene.paint.PhongMaterial;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.shop.ShopItem;

/**
 * The shop item class for skins, based on the requirements from the
 * ShopItem interface. Besides, every skin item has it's own texture.
 * Furthermore, a map of all unlocked skins is maintained.
 * @author Maikel and Mitchell.
 */
@SuppressWarnings("restriction") // SuppresWarnings, because
// PhongMaterials are needed for the textures.
public class Skin implements ShopItem {

    /** IRON MAN skin for player. */
    private static IronManSkin iron;

    /** NOOB skin for player, this is the starting skin. */
    private static NoobSkin noob;

    /** CAPTAIN skin for the player. */
    private static CaptainSkin captain;

    /** PLANK skin for the player. */
    private static PlankSkin plank;

    /** BOY skin for the player. */
    private static BoySkin boy;

    /** ANDY skin for the player. */
    private static AndySkin andy;

    /** The price of this skin in the shop. */
    private final int skinPrice;

    /** The name to display in the shop. */
    private final String skinName;

    /** The real material used by this skin. */
    private final PhongMaterial skinMaterial;

    /** HashMap used to store which skins are unlocked. */
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
        super();
        skinPrice = price;
        skinName = name;
        skinMaterial = Style.loadPlayerTexture(textureName);
    }

    @Override
    public int getItemPrice() {
        return skinPrice;
    }

    @Override
    public String getItemName() {
        return skinName;
    }

    /**
     * Getter for the PhongMaterial.
     * @return material
     */
    public PhongMaterial getSkinMaterial() {
        return skinMaterial;
    }

    /**
     * Creates a HashMap for the skins that are unlocked.
     * This HashMap is created for keeping the textures separated
     * from the constructor. Else the textures have to be loaded
     * every time the shop is started. During the tests as well.
     */
    public static void createUnlockedSkinHashmap() {
        unlockedMap.put("Andy", false);
        unlockedMap.put("B-man", false);
        unlockedMap.put("Captain", false);
        unlockedMap.put("Iron Man", false);
        unlockedMap.put("Noob", true);
        unlockedMap.put("Plank", false);
    }

    /**
     * Gets the unlocked value of skin, based on the name.
     * @param name the Name of the skin.
     * @return boolean unlocked or not.
     */
    public static boolean getUnlockedSkin(final String name) {
        return unlockedMap.get(name);
    }

    /**
     * Sets the unlocked value for a skin of the shop.
     * @param name the Name of the skin to change the value for.
     * @param unlocked new unlocked value.
     */
    public static void setUnlockedSkin(final String name,
            final boolean unlocked) {
        unlockedMap.replace(name, unlocked);
    }

    /**
     * Simple getter for IronManSkin.
     * @return Skin of Iron Man.
     */
    public static IronManSkin getIronManSkin() {
        return iron;
    }

    /**
     * Simple setter for IronManSkin.
     * @param skin a given IronManSkin to set.
     */
    public static void setIronManSkin(final IronManSkin skin) {
        iron = skin;
    }

    /**
     * Simple getter for NoobSkin.
     * @return Skin of Noob.
     */
    public static NoobSkin getNoobSkin() {
        return noob;
    }

    /**
     * Simple setter for NoobSkin.
     * @param skin a given NoobSkin to set.
     */
    public static void setNoobSkin(final NoobSkin skin) {
        noob = skin;
    }

    /**
     * Simple getter for CaptainSkin.
     * @return Skin of Captain.
     */
    public static CaptainSkin getCaptainSkin() {
        return captain;
    }

    /**
     * Simple setter for CaptainSkin.
     * @param skin a given CaptainSkin to set.
     */
    public static void setCaptainSkin(final CaptainSkin skin) {
        captain = skin;
    }

    /**
     * Simple getter for PlankSkin.
     * @return Skin of Plank.
     */
    public static PlankSkin getPlankSkin() {
        return plank;
    }

    /**
     * Simple setter for PlankSkin.
     * @param skin a given PlankSkin to set.
     */
    public static void setPlankSkin(final PlankSkin skin) {
        plank = skin;
    }

    /**
     * Simple getter for BoySkin.
     * @return Skin of Boy.
     */
    public static BoySkin getBoySkin() {
        return boy;
    }

    /**
     * Simple setter for BoySkin.
     * @param skin a given BoySkin to set.
     */
    public static void setBoySkin(final BoySkin skin) {
        boy = skin;
    }

    /**
     * Simple getter for AndySkin.
     * @return Skin of Andy.
     */
    public static AndySkin getAndySkin() {
        return andy;
    }

    /**
     * Simple setter for AndySkin.
     * @param skin a given AndySkin to set.
     */
    public static void setAndySkin(final AndySkin skin) {
        andy = skin;
    }

}
