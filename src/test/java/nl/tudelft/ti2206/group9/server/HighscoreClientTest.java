package nl.tudelft.ti2206.group9.server;

import nl.tudelft.ti2206.group9.server.HighscoreClient.QueryCallback;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HighscoreClientTest {

    private static final int SERVER_SETUP_WAIT = 500;
    private static final Object LOCK = new Object();
    private static final long TEST_TIMEOUT = 5000;
    private HighscoreClient client;
    private String actualResponse;

    private final QueryCallback callback = response -> {
        actualResponse = response;
        synchronized (LOCK) {
            LOCK.notifyAll();   // Resume test
        }
    };

    @BeforeClass
    public static void setUpBeforeClass() {
        try {
            new Thread(() -> {
                try {
                    HighscoreServer.main("");
                } catch (Exception e) { // NOPMD - need to catch EVERYTHING :D
                    fail("Server has thrown an exception:\n" + e.getMessage());
                }
            }, "ServerThread").start();
            /* If we don't sleep, the client will try to connect before the *
             * server is set up.                                            */
            Thread.sleep(SERVER_SETUP_WAIT);
        } catch (InterruptedException e) {
            fail("InterruptedException thrown: " + e.getMessage());
        }
    }

    @AfterClass
    public static void tearDownAfterClass() {
        HighscoreServer.quit();
    }

    @Before
    public void setUp() {
        client = new HighscoreClient("localhost");
    }

    @After
    public void tearDown() {
        client.disconnect();
    }

    @Test
    public final void testGoodWeather() {
        final int[] scores = {42, 21, 84, 63};
        final String[] names = {"Kees", "Piet", "Jaap", "Piet"};

        sendScoresToServer(scores, names);
        getListsNegativeAmounts(names);
        getListsPositiveAmounts(names);
    }

    private void getListsNegativeAmounts(final String... names) {
        client.getGlobal(-1, callback);
        haltTestUntilServerResponds("get global -1");
        assertEquals("", actualResponse);

        client.getUser(names[0], -1, callback);
        haltTestUntilServerResponds("get Kees -1");
        assertEquals("", actualResponse);
    }

    private void getListsPositiveAmounts(final String... names) {
        final int listSize = 5;
        client.getGlobal(listSize, callback);
        haltTestUntilServerResponds("get global 5");
        assertEquals("Highscore[Jaap, 84]\nHighscore[Piet, 63]\n"
                + "Highscore[Kees, 42]", actualResponse);

        client.getGlobal(2, callback);
        haltTestUntilServerResponds("get global 2");
        assertEquals("Highscore[Jaap, 84]\nHighscore[Piet, 63]",
                actualResponse);

        client.getUser(names[1], 1, callback);
        haltTestUntilServerResponds("get user Piet 1");
        assertEquals("Highscore[Piet, 63]",
                actualResponse);

        client.getUser(names[1], 2, callback);
        haltTestUntilServerResponds("get user Piet 2");
        assertEquals("Highscore[Piet, 63]\nHighscore[Piet, 21]",
                actualResponse);

        client.getUser(names[1], 2 + 1, callback);
        haltTestUntilServerResponds("get user Piet 3");
        assertEquals("Highscore[Piet, 63]\nHighscore[Piet, 21]",
                actualResponse);
    }

    private void sendScoresToServer(final int[] scores, final String... names) {
        for (int i = 0; i < names.length; i++) {
            client.add(names[i], scores[i], callback);
            haltTestUntilServerResponds("add " + names[i] + " " + scores[i]);
            assertEquals("SUCCESS", actualResponse);
        }
    }

    @Test
    public void testBadWeather() {
        testIllegalQuery();
        testIllegalGetQuery();
        testIllegalAddQuery();
    }

    private void testIllegalQuery() {
        client.query("", 1, callback);
        haltTestUntilServerResponds("<empty query>");
        assertEquals("USAGE add|get <args>", actualResponse);

        client.query("doNothingOrSomething", 1, callback);
        haltTestUntilServerResponds("doNothingOrSomething");
        assertEquals("USAGE add|get <args>", actualResponse);
    }

    private void testIllegalGetQuery() {
        client.query("get", 1, callback);
        haltTestUntilServerResponds("get");
        assertEquals("USAGE get global|user <args>", actualResponse);

        client.query("get nothing", 1, callback);
        haltTestUntilServerResponds("get nothing");
        assertEquals("USAGE get global|user <args>", actualResponse);

        client.query("get global", 1, callback);
        haltTestUntilServerResponds("get global");
        assertEquals("USAGE get global <amount:int>", actualResponse);

        client.query("get user", 1, callback);
        haltTestUntilServerResponds("get user");
        assertEquals("USAGE get user <name:string> <amount:int>",
                actualResponse);

        client.query("get user Kees", 1, callback);
        haltTestUntilServerResponds("get user Kees");
        assertEquals("USAGE get user Kees <amount:int>",
                actualResponse);
    }

    private void testIllegalAddQuery() {
        client.query("add", 1, callback);
        haltTestUntilServerResponds("add");
        assertEquals("USAGE add <name:string> <score:int>", actualResponse);

        client.query("add Kees", 1, callback);
        haltTestUntilServerResponds("add Kees");
        assertEquals("USAGE add Kees <score:int>", actualResponse);
    }

    @Test
    public void testDisconnect() {
        try {
            client.disconnect();
            while (client.isConnected()) {
                Thread.sleep(2); // Do nothing until disconnected
            }

            client.getGlobal(1, callback);
            // halt(); not needed, because connected = false.
            assertEquals("DISCONNECTED", actualResponse);
        } catch (InterruptedException e) {
            fail("InterruptedException thrown: " + e.getMessage());
        }
    }

    /**
     * Halts the test until resumed in callback.
     */
    private void haltTestUntilServerResponds(final String failMessage) {
        actualResponse = null;
        try {
            synchronized (LOCK) {
                LOCK.wait(TEST_TIMEOUT);
            }
        } catch (InterruptedException e) {
            fail("InterruptedException thrown: " + e.getMessage());
        }
        if (actualResponse == null) {
            fail("Server in UnitTest took too long to respond: " + failMessage);
        }
    }

}
