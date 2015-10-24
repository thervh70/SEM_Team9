package nl.tudelft.ti2206.group9.shop.soundtrack;

/**
 * This class can be instantiated for creating the Radioactive
 * soundtrack, which is the default soundtrack, for in the shop.
 * The Radioactive soundtrack is a starter item, so it can't be bought anymore,
 * as this shop item is already part of the player's property.
 * @author Mitchell.
 */
public class RadioactiveSoundtrack extends AbstractSoundtrack {
    /** Price of the Radioactive soundtrack in the shop. */
    private static final int PRICE = 0;
    /** Path to be used for soundtrack 'Radioactive'. */
    private static final String PATH_DEFAULT = "soundtrack_radioactive";

    /** Constructor for the 'radioactive' soundtrack. */
    public RadioactiveSoundtrack() {
        super(PRICE, "Radioactive", PATH_DEFAULT);
    }
}
