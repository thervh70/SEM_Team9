package nl.tudelft.ti2206.group9.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HighscoreClientTest {

    private HighscoreClient client;

    @BeforeClass
    public static void setUpBeforeClass() {
        new Thread(() -> {
            try {
                HighscoreServer.main("");
            } catch (Exception e) { // NOPMD - we want to catch EVERYTHING :D
                fail("Server has thrown an exception:\n" + e.getMessage());
            }
        }, "ServerThread").start();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        HighscoreServer.quit();
    }

    @Before
    public void setUp() {
        client = new HighscoreClient();
    }

    @After
    public void tearDown() {
        client.disconnect();
    }

    @Test
    public final void test() {
        client.query("get user", response -> assertEquals("Kees", response));
    }

}
