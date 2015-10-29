package nl.tudelft.ti2206.group9.shop.skin;

import javafx.scene.paint.PhongMaterial;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.shop.ShopItem;

/**
 * The abstract shop item class for skins, based on the requirements from the
 * ShopItem interface. Besides, every skin item has it's own texture.
 *
 * @author Maikel and Mitchell.
 */
public abstract class AbstractSkin implements ShopItem {

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
     * Constructor for the skin.
     * It calls the Style.loadPlayerTexture
     * to load the image into the PhongMaterial
     * with the name "texture_[texture_name].png".
     *
     * @param price       Price of this skin in shop.
     * @param name        Name to display.
     * @param textureName Name of texture.
     */
    public AbstractSkin(final int price,
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
     *
     * @return material
     */
    public PhongMaterial getSkinMaterial() {
        return skinMaterial;
    }

}
