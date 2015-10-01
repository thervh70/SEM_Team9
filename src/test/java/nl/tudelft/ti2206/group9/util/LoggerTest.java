package nl.tudelft.ti2206.group9.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Player;

import org.junit.Before;
import org.junit.Test;

/**
 * LoggerTest implements GameObserver to inherit all the enums that are used
 * for the events.
 * @author Maarten
 */
public class LoggerTest {

	private static final String TESTLOG = "test.log";

	private Logger logger; //NOPMD loggers should be static+final outside tests

	@Before
	public void setUp() {
		logger = new Logger(TESTLOG);
	}

	@Test
	public void testWriteToFile() throws IOException {
		logger.gameUpdate(Category.PLAYER, Player.JUMP);
		logger.writeToFile();
		String log = new String(Files.readAllBytes(Paths.get(TESTLOG)),
				StandardCharsets.UTF_8).substring(Logger.FORMAT.length() - 1);
		assertEquals("[PLAYER] Jumping.\n", log);

		// append = false, thus a new file should be created
		logger.gameUpdate(Category.PLAYER, Player.JUMP);
		logger.writeToFile(false);
		log = new String(Files.readAllBytes(Paths.get(TESTLOG)),
				StandardCharsets.UTF_8).substring(Logger.FORMAT.length() - 1);
		assertEquals("[PLAYER] Jumping.\n", log);
	}

	@Test
	public void testIOException() throws IOException {
		GameObservable.addObserver(logger);
		final String fakePath = "no\\folder\\exists\\test.log";
		new Logger(fakePath); // will catch IOException

		logger.writeToFile(false);
		final String expected =
				"[ERROR ] Exception while reading or writing files!\n"
				+ "    in Logger.writeToOutput(String, boolean)\n"
				+ "    Message: " + fakePath;
		String log = new String(Files.readAllBytes(Paths.get(TESTLOG)),
				StandardCharsets.UTF_8);
		System.out.println(log);
		log = log.substring(Logger.FORMAT.length() - 1,
						expected.length() + Logger.FORMAT.length() - 1);
		assertEquals(expected, log);

		GameObservable.deleteObserver(logger);
	}

}
