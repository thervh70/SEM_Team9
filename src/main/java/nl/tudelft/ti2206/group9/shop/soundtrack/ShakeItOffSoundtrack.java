package nl.tudelft.ti2206.group9.shop.soundtrack;

/**
 * This class can be instantiated for creating a Shake It Off
 * soundtrack (8 bit) for in the shop.
 * @author Mitchell.
 */
public class ShakeItOffSoundtrack extends AbstractSoundtrack {
    /** Price of the Shake It Off soundtrack in the shop. */
    private static final int PRICE = 70;
    /** Path to be used for the 'Shake It Off' soundtrack. */
    private static final String PATH_SHAKEITOFF = "soundtrack_ShakeItOff";

    /** Constructor for the Shake It Off soundtrack. */
    public ShakeItOffSoundtrack() {
        super(PRICE, "Shake It Off", PATH_SHAKEITOFF);
    }
}
