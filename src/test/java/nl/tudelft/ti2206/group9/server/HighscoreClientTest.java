package nl.tudelft.ti2206.group9.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import nl.tudelft.ti2206.group9.server.HighscoreClient.QueryCallback;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HighscoreClientTest {

    private static final Object LOCK = new Object();
    private HighscoreClient client;
    private String actualResponse; // NOPMD - field cannot be local field
    private final QueryCallback callback = response -> {
        actualResponse = response;
        resumeTest();
    };

    @BeforeClass
    public static void setUpBeforeClass() throws InterruptedException {
        new Thread(() -> {
            try {
                HighscoreServer.main("");
            } catch (Exception e) { // NOPMD - we want to catch EVERYTHING :D
                fail("Server has thrown an exception:\n" + e.getMessage());
            }
        }, "ServerThread").start();
        /* If we don't sleep, the client will try to connect before the *
         * server is set up.                                            */
        Thread.sleep(2 + 2 + 2);
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
    public final void test() throws InterruptedException {
        final int[] scores = {42, 21, 84, 63};
        int i = -1;
        client.add("Kees", scores[++i], callback);
        haltTestUntilServerResponds();
        assertEquals("SUCCESS", actualResponse); // NOPMD - 4 * SUCCESS

        client.add("Piet", scores[++i], callback);
        haltTestUntilServerResponds();
        assertEquals("SUCCESS", actualResponse);

        client.add("Jaap", scores[++i], callback);
        haltTestUntilServerResponds();
        assertEquals("SUCCESS", actualResponse);

        client.add("Piet", scores[++i], callback);
        haltTestUntilServerResponds();
        assertEquals("SUCCESS", actualResponse);

        client.get(scores.length + 1, callback);
        haltTestUntilServerResponds();
        assertEquals("Highscore[Jaap, 84]\nHighscore[Piet, 63]\n"
                + "Highscore[Kees, 42]\n\n", actualResponse);
    }

    /**
     * Halts the test until resumed in callback.
     * @throws InterruptedException when test gets killed.
     */
    private void haltTestUntilServerResponds() throws InterruptedException {
        synchronized (LOCK) {
            LOCK.wait();
        }
    }

    /** To be called in callback; resumes the test. */
    private void resumeTest() {
        synchronized (LOCK) {
            LOCK.notifyAll();
        }
    }

}
