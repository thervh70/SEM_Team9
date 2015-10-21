package nl.tudelft.ti2206.group9.shop.soundtrack;

/**
 * This class can be instantiated for creating a Duck Tales
 * soundtrack for in the shop.
 * @author Mitchell.
 */
public class DuckTalesSoundtrack extends Soundtrack {
    /** Price of the Duck Tales soundtrack in the shop. */
    private static final int PRICE = 20;
    /** Path to be used for soundtrack 'Duck Tales'. */
    private static final String PATH_DUCKTALES = "soundtrack_DuckTales";

    /** Constructor for the Duck Tales soundtrack. */
    public DuckTalesSoundtrack() {
        super(PRICE, "Duck Tales", PATH_DUCKTALES);
    }
}
