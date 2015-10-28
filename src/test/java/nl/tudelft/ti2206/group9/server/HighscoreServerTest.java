package nl.tudelft.ti2206.group9.server;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class HighscoreServerTest {

    private static final long SERVER_SETUP_TIME = 200;

    @Rule
    public final TextFromStandardInputStream systemInMock =
            TextFromStandardInputStream.emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    private void setUpServer() {
        new Thread(() -> {
            try {
                HighscoreServer.main("");
            } catch (Exception e) { // NOPMD - need to catch EVERYTHING :D
                fail("Server has thrown an exception:\n" + e.getMessage());
            }
        }, "ServerThread").start();
        try {
            Thread.sleep(SERVER_SETUP_TIME);
        } catch (InterruptedException e) {
            fail("Test setup failed while waiting for the server to setup");
        }
    }

    @After
    public void tearDown() {
        HighscoreServer.quit();
        final Logger logger = Logger.getLogger(HighscoreServer.class.getName());
        logger.removeHandler(
                logger.getHandlers()[0]);
        try {
            Thread.sleep(SERVER_SETUP_TIME);
        } catch (InterruptedException e) {
            fail("Test setup failed while waiting for the server to quit");
        }
    }

    @Test
    public final void testStopServer() {
        systemInMock.provideLines("q");
        setUpServer();
        assertTrue(systemOutRule.getLogWithNormalizedLineSeparator()
                .endsWith("Server has been stopped.\n"));
    }

    @Test
    public final void testTwoServers() {
        setUpServer();
        setUpServer();
        assertTrue(systemOutRule.getLogWithNormalizedLineSeparator()
                .contains("Could not listen on port 42042, exiting!"));
        HighscoreServer.quit();
        assertTrue(systemOutRule.getLogWithNormalizedLineSeparator()
                .endsWith("Server has been stopped.\n"));
    }

}
