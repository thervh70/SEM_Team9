package nl.tudelft.ti2206.group9.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter.ResultCallback;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HighscoreServerIntegrationTest {

    private static final Object LOCK = new Object();
    private static final long TEST_TIMEOUT = 5000;
    private static final long SERVER_SETUP_WAIT = 500;
    private boolean actualResponse;
    private boolean hasResponded; // NOPMD - this cannot be local variable
    private final ResultCallback callback = success -> {
        hasResponded = true;
        actualResponse = success;
        synchronized (LOCK) {
            LOCK.notifyAll();   // Resume test
        }
    };

    @BeforeClass
    public static void setUpBeforeClass() {
        HighscoreDatabase.reset();
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

    @Test
    public final void test1Connect() {
        assertFalse(HighscoreClientAdapter.connect("www.kees.kaas"));
        assertTrue(HighscoreClientAdapter.connect("localhost"));
    }

    @Test
    public final void test2Add() {
        HighscoreClientAdapter.add("Kees", 1, callback); //NOPMD - Kees multiple
        haltTestUntilServerResponds("add Kees 1");
        assertTrue(actualResponse);

        HighscoreClientAdapter.add("Kees", 2, callback);
        haltTestUntilServerResponds("add Kees 2");
        assertTrue(actualResponse);

        HighscoreClientAdapter.add("", 2, callback);
        haltTestUntilServerResponds("add <noString> 2");
        assertFalse(actualResponse);
    }

    @Test
    public final void test3GetGlobal() {
        List<Highscore> list = HighscoreClientAdapter.getGlobal(2, callback);
        haltTestUntilServerResponds("get global 2");
        assertTrue(actualResponse);
        assertEquals(1, list.size());

        list = HighscoreClientAdapter.getGlobal(-1, callback);
        haltTestUntilServerResponds("get global -1");
        assertFalse(actualResponse);
        assertEquals(0, list.size());
    }

    @Test
    public final void test4GetUser() {
        List<Highscore> list;

        list = HighscoreClientAdapter.getUser("Kees", 2, callback);
        haltTestUntilServerResponds("get user Kees 2");
        assertTrue(actualResponse);
        assertEquals(2, list.size());

        list = HighscoreClientAdapter.getUser("", 2, callback);
        haltTestUntilServerResponds("get user <noString> 2");
        assertFalse(actualResponse);
        assertEquals(0, list.size());
    }

    @Test
    public final void test5Disconnect() {
        HighscoreClientAdapter.disconnect();
        try { // Wait until server has disconnected
            Thread.sleep(SERVER_SETUP_WAIT);
        } catch (InterruptedException e) {
            fail("Test has been interrupted while waiting for disconnect.");
        }
        HighscoreClientAdapter.add("Kees", 1, callback);
        assertFalse(actualResponse);
    }

    /**
     * Halts the test until resumed in callback.
     */
    private void haltTestUntilServerResponds(final String failMessage) {
        hasResponded = false;
        try {
            synchronized (LOCK) {
                LOCK.wait(TEST_TIMEOUT);
            }
        } catch (InterruptedException e) {
            fail("InterruptedException thrown: " + e.getMessage());
        }
        if (!hasResponded) {
            fail("Server in UnitTest took too long to respond: " + failMessage);
        }
    }

}
