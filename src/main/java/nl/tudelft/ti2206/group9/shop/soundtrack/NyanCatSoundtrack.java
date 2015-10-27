package nl.tudelft.ti2206.group9.shop.soundtrack;

/**
 * This class can be instantiated for creating a Nyan Cat soundtrack
 * for in the shop.
 * @author Mitchell.
 */
public class NyanCatSoundtrack extends AbstractSoundtrack {
    /** Price of the Nyan Cat soundtrack in the shop. */
    private static final int PRICE = 100;
    /** Path to be used for the 'Nyan Cat' soundtrack. */
    private static final String PATH_NYANCAT = "soundtrack_NyanCat";

    /** Constructor for the Nyan Cat soundtrack. */
    public NyanCatSoundtrack() {
        super(PRICE, "Nyan Cat", PATH_NYANCAT);
    }
}
