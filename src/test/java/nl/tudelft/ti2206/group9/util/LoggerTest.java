package nl.tudelft.ti2206.group9.util;

import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.GameUpdate;
import nl.tudelft.ti2206.group9.util.GameObserver.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * LoggerTest implements GameObserver to inherit all the enums that are used
 * for the events.
 * @author Maarten
 */
public class LoggerTest {

    private static final String TESTLOG = "src/test/java/nl/tudelft/ti2206/"
            + "group9/util/test.log";

    private Logger logger; //NOPMD loggers should be static+final outside tests

    @Before
    public void setUp() {
        logger = new Logger(TESTLOG);
    }

    @Test
    public void testWriteToFile() {
        try {
            final GameUpdate testUpdate =
                    new GameUpdate(Category.PLAYER, Player.JUMP);
            final String testUpdateString = "[PLAYER] Jumping.\n";
            final int updateIndex = Logger.FORMAT.length() - 1;

            logger.update(null, testUpdate);
            logger.writeToFile();
            String log = new String(Files.readAllBytes(Paths.get(TESTLOG)),
                    StandardCharsets.UTF_8).substring(updateIndex);
            assertEquals(testUpdateString, log);

            // append = false, thus a new file should be created
            logger.update(null, testUpdate);
            logger.writeToFile(false);
            log = new String(Files.readAllBytes(Paths.get(TESTLOG)),
                    StandardCharsets.UTF_8).substring(updateIndex);
            assertEquals(testUpdateString, log);
        } catch (IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }
    }

}
