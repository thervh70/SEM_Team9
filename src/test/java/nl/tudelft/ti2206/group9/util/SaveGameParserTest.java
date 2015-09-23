package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.level.State;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class SaveGameParserTest {

    private static final double DELTA = 0.0000001;

    private SaveGameParser parser;

    @Before
    public void setUp() {
        parser = new SaveGameParser();
    }

    @Test
    public void testParser() {
        parser.loadGame("src/main/resources/nl/tudelft/ti2206/group9/util/firstSaveGame.json");
        assertEquals(State.getPlayerName(), "Mathias");
        assertEquals(State.getCoins(), 42);
        assertEquals(State.getScore(), 2560, DELTA);
        assertEquals(State.isSoundEnabled(), true);
    }
}
