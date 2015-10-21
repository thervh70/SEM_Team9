package nl.tudelft.ti2206.group9.shop;

/**
 * Interface ShopItem which is the basis for all shop items.
 * Every shop item must be able to get a name and a price from.
 * @author Mitchell
 */

public interface ShopItem {

    /**
     * Gets the price (in coins) of a specific shop item.
     * @return the price (int) of the item.
     */
    int getItemPrice();

    /**
     * Gets the name of a specific shop item.
     * @return the name (string) of the item.
     */
    String getItemName();

}
