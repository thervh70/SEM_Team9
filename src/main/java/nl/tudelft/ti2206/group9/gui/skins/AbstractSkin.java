package nl.tudelft.ti2206.group9.gui.skins;

import javafx.scene.paint.PhongMaterial;
import nl.tudelft.ti2206.group9.gui.MainMenuScene;

/**
 * @author Maikel on 08/10/2015.
 */
public abstract class AbstractSkin {


    private static  int skinPrice;

    private static String skinName;

    private static PhongMaterial skinMaterial;

    public AbstractSkin(int price, String name, PhongMaterial material) {
        skinPrice = price;
        skinName = name;
        skinMaterial = material;
    }

    public static int getSkinPrice() {
        return skinPrice;
    }

    public static String getSkinName() {
        return skinName;
    }

    public static PhongMaterial getSkinMaterial() {
        return skinMaterial;
    }
}