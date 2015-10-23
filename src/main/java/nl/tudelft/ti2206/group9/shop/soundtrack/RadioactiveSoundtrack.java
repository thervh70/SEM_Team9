package nl.tudelft.ti2206.group9.shop.soundtrack;

/**
 * This class can be instantiated for creating the default
 * soundtrack for in the shop.
 * The default soundtrack is a starter item, so it can't be bought anymore,
 * as this shop item is already part of the player's property.
 * @author Mitchell.
 */
public class RadioactiveSoundtrack extends AbstractSoundtrack {
    /** Price of the default soundtrack in the shop. */
    private static final int PRICE = 0;
    /** Path to be used for soundtrack 'Default'. */
    private static final String PATH_DEFAULT = "soundtrack_Default";

    /** Constructor for the default soundtrack. */
    public RadioactiveSoundtrack() {
        super(PRICE, "Radioactive", PATH_DEFAULT);
    }
}
