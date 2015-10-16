package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import nl.tudelft.ti2206.group9.level.save.Highscores.Highscore;
import nl.tudelft.ti2206.group9.level.save.Highscores.ResultCallback;
import nl.tudelft.ti2206.group9.server.HighscoreClientTest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HighscoresTest {

    private static final Object LOCK = new Object();
    private boolean actualResponse; // NOPMD - field cannot be local field
    private final ResultCallback callback = success -> {
        actualResponse = success;
        synchronized (LOCK) {
            LOCK.notifyAll();   // Resume test
        }
    };

    @BeforeClass
    public static void setUpBeforeClass() throws InterruptedException {
        HighscoreClientTest.setUpBeforeClass();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        HighscoreClientTest.tearDownAfterClass();
    }

    @Test
    public final void testConnect() {
        assertFalse(Highscores.connect("www.kees.kaas"));
        assertTrue(Highscores.connect("localhost"));
    }

    @Test
    public final void testAdd() throws InterruptedException {
        Highscores.add("Kees", 2, callback);
        haltTestUntilServerResponds();
        assertTrue(actualResponse);

        Highscores.add("", 2, callback);
        haltTestUntilServerResponds();
        assertFalse(actualResponse);
    }

    @Test
    public final void testGetGlobal() throws InterruptedException {
        List<Highscore> list = Highscores.getGlobal(2, callback);
        haltTestUntilServerResponds();
        assertTrue(actualResponse);
        assertEquals(2, list.size());

        list = Highscores.getGlobal(-1, callback);
        haltTestUntilServerResponds();
        assertFalse(actualResponse);
        assertEquals(0, list.size());
    }

    @Test
    public final void testGetUser() throws InterruptedException {
        List<Highscore> list = Highscores.getUser("Kees", 2, callback);
        haltTestUntilServerResponds();
        assertTrue(actualResponse);
        assertEquals(2, list.size());

        list = Highscores.getUser("", 2, callback);
        haltTestUntilServerResponds();
        assertFalse(actualResponse);
        assertEquals(0, list.size());
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

}
