package nl.tudelft.ti2206.group9.shop.soundtrack;

/**
 * This class can be instantiated for creating a Mario soundtrack
 * for in the shop.
 * @author Mitchell.
 */
public class MarioSoundtrack extends AbstractSoundtrack {
    /** Price of the Mario soundtrack in the shop. */
    private static final int PRICE = 100;
    /** Path to be used for the 'Mario' soundtrack. */
    private static final String PATH_MARIO = "soundtrack_Mario";

    /** Constructor for the Mario soundtrack. */
    public MarioSoundtrack() {
        super(PRICE, "Mario", PATH_MARIO);
    }
}
