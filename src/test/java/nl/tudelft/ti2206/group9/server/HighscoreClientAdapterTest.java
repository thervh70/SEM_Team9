package nl.tudelft.ti2206.group9.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter.ResultCallback;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HighscoreClientAdapterTest {

    private static final Object LOCK = new Object();
    private static final long TEST_TIMEOUT = 5000;
    private boolean actualResponse;
    private boolean hasResponded;
    private final ResultCallback callback = success -> {
        hasResponded = true;
        actualResponse = success;
        synchronized (LOCK) {
            LOCK.notifyAll();   // Resume test
        }
    };

    @BeforeClass
    public static void setUpBeforeClass() {
        HighscoreClientTest.setUpBeforeClass();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        HighscoreClientTest.tearDownAfterClass();
    }

    @Test
    public final void testConnect() {
        assertFalse(HighscoreClientAdapter.connect("www.kees.kaas"));
        assertTrue(HighscoreClientAdapter.connect("localhost"));
    }

    @Test
    public final void testAdd() {
        HighscoreClientAdapter.add("Kees", 2, callback);
        haltTestUntilServerResponds("add Kees 2");
        assertTrue(actualResponse);

        HighscoreClientAdapter.add("", 2, callback);
        haltTestUntilServerResponds("add <noString> 2");
        assertFalse(actualResponse);
    }

    @Test
    public final void testGetGlobal() {
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
    public final void testGetUser() {
        List<Highscore> list;

        list = HighscoreClientAdapter.getUser("Kees", 2, callback);
        haltTestUntilServerResponds("get user Kees 2");
        assertTrue(actualResponse);
        assertEquals(1, list.size());

        list = HighscoreClientAdapter.getUser("", 2, callback);
        haltTestUntilServerResponds("get user <noString> 2");
        assertFalse(actualResponse);
        assertEquals(0, list.size());
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
