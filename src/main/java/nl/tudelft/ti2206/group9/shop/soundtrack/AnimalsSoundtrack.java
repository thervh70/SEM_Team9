package nl.tudelft.ti2206.group9.shop.soundtrack;

/**
 * This is class can be instantiated for creating an Animals
 * soundtrack for in the shop.
 * @author Mitchell.
 */
public class AnimalsSoundtrack extends AbstractSoundtrack {
    /** Price of the Animals soundtrack in shop. */
    private static final int PRICE = 250;
    /** Path to be used for soundtrack 'Animals'. */
    private static final String PATH_ANIMALS = "soundtrack_animals";

    /** Constructor for the animals soundtrack. */
    public AnimalsSoundtrack() {
        super(PRICE, "Animals", PATH_ANIMALS);
    }
}
