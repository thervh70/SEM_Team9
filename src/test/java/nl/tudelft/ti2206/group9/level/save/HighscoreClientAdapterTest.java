package nl.tudelft.ti2206.group9.level.save;

import nl.tudelft.ti2206.group9.server.Highscore;
import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter;
import nl.tudelft.ti2206.group9.server.HighscoreClientAdapter.ResultCallback;
import nl.tudelft.ti2206.group9.server.HighscoreClientTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HighscoreClientAdapterTest {

    private static final Object LOCK = new Object();
    private boolean actualResponse; // NOPMD - field cannot be local field
    private final ResultCallback callback = success -> {
        actualResponse = success;
        synchronized (LOCK) {
            LOCK.notifyAll();   // Resume test
        }
    };

    @BeforeClass
    public static void setUpBeforeClass() {
        try {
            HighscoreClientTest.setUpBeforeClass();
        } catch (InterruptedException e) {
            fail();
        }
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
        haltTestUntilServerResponds();
        assertTrue(actualResponse);

        HighscoreClientAdapter.add("", 2, callback);
        haltTestUntilServerResponds();
        assertFalse(actualResponse);
    }

    @Test
    public final void testGetGlobal() {
        List<Highscore> list = HighscoreClientAdapter.getGlobal(2, callback);
        haltTestUntilServerResponds();
        assertTrue(actualResponse);
        assertEquals(2, list.size());

        list = HighscoreClientAdapter.getGlobal(-1, callback);
        haltTestUntilServerResponds();
        assertFalse(actualResponse);
        assertEquals(0, list.size());
    }

    @Test
    public final void testGetUser() {
        List<Highscore> list;

        list = HighscoreClientAdapter.getUser("Kees", 2, callback);
        haltTestUntilServerResponds();
        assertTrue(actualResponse);
        assertEquals(2, list.size());

        list = HighscoreClientAdapter.getUser("", 2, callback);
        haltTestUntilServerResponds();
        assertFalse(actualResponse);
        assertEquals(0, list.size());
    }

    /**
     * Halts the test until resumed in callback.
     */
    private void haltTestUntilServerResponds() {
        try {
            synchronized (LOCK) {
                LOCK.wait();
            }
        } catch (InterruptedException e) {
            fail();
        }
    }

}
