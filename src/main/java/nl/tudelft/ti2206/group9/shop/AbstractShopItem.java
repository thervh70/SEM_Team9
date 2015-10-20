package nl.tudelft.ti2206.group9.shop;

/**
 * Abstract class ShopItem which is the basis for all shop items.
 * Every shop item must be able to get a name and a price from.
 * @author Mitchell
 */

public abstract class AbstractShopItem {

    /**
     * Gets the price (in coins) of a shop item.
     * @return the price (int) of the item.
     */
    public abstract int getItemPrice();

    /**
     * Gets the name of a specific shop item.
     * @return the name (string) of the item.
     */
    public abstract String getItemName();

}
