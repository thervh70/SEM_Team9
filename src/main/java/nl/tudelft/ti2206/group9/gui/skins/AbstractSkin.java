package nl.tudelft.ti2206.group9.gui.skins;

/**
 * @author Maikel on 08/10/2015.
 */
public abstract class AbstractSkin {


    private static  int skinPrice;

    private static String skinName;

    private static String skinPath;

    public void AbstractSkin(int price, String name, String path) {
        skinPrice = price;
        skinName = name;
        skinPath = path;
    }

    public static int getSkinPrice() {
        return skinPrice;
    }

    public static String getSkinName() {
        return skinName;
    }

    public static String getSkinPath() {
        return skinPath;
    }
}