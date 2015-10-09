package nl.tudelft.ti2206.group9.gui.skins;

import javafx.scene.paint.PhongMaterial;
import nl.tudelft.ti2206.group9.gui.Style;

/**
 * @author Maikel on 08/10/2015.
 */
@SuppressWarnings("restriction")
public abstract class AbstractSkin {

    /** The price of this skin in the shop. */
    private final int skinPrice;

    /** The name to display in the shop. */
    private final String skinName;

    /** The real material used by this skin. */
    private final PhongMaterial skinMaterial;

    /**
     * Constructor for the skin.
     * It calls the Style.loadPlayerTexture
     * to load the image into the PhongMaterial.
     * @param price Price of this skin in shop.
     * @param name Name to display.
     * @param path Location of this skin in the resources folder
     */
    public AbstractSkin(final int price,
                        final String name, final String path) {
        skinPrice = price;
        skinName = name;
        skinMaterial = Style.loadPlayerTexture(path);
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
}