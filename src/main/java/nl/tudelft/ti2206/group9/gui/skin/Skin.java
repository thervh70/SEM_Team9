package nl.tudelft.ti2206.group9.gui.skin;

import javafx.scene.paint.PhongMaterial;
import nl.tudelft.ti2206.group9.gui.Style;

/**
 * @author Maikel on 08/10/2015.
 */
@SuppressWarnings("restriction")
public class Skin {

    /** The price of this skin in the shop. */
    private final int skinPrice;

    /** The name to display in the shop. */
    private final String skinName;

    /** The real material used by this skin. */
    private final PhongMaterial skinMaterial;

    /** Boolean value for if skin is unlocked. */
    private boolean skinUnlocked = false;

    /**
     * Constructor for the skin.
     * It calls the Style.loadPlayerTexture
     * to load the image into the PhongMaterial
     * with the name "texture_[texture_name].png".
     * @param price Price of this skin in shop.
     * @param name Name to display.
     * @param textureName Name of texture.
     * @param unlocked Skin unlocked?
     */
    public Skin(final int price,
                final String name, final String textureName,
                final boolean unlocked) {
        skinPrice = price;
        skinName = name;
        skinMaterial = Style.loadPlayerTexture(textureName);
        skinUnlocked = unlocked;
    }

    /**
     * Getter for the price.
     * @return price
     */
    public int getSkinPrice() {
        return skinPrice;
    }

    /**
     * Getter for the name.
     * @return name
     */
    public String getSkinName() {
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
     * Getter for skinUnlocked.
     * @return boolean
     */
    public boolean getSkinUnlocked() {
        return skinUnlocked;
    }

    /** Setting the value for unlocked.
     * @param unlocked unlocked?
     */
    public void setSkinUnlocked(final boolean unlocked) {
        skinUnlocked = unlocked;
    }

    /**
     * Buy method.
     */
    public void buySkin() {
        skinUnlocked = !skinUnlocked;
    }
}
