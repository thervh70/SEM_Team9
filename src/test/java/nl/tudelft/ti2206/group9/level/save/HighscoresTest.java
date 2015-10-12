package nl.tudelft.ti2206.group9.level.save;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.gamejolt.highscore.Highscore;

public class HighscoresTest {

    @Before
    public void setUp() throws InterruptedException {
        Highscores.login("TravisCI", "dce59e", () -> { },
                () -> fail("Could not login with TravisCI!"));
    }

    @Test
    public final void testLogout() {
        Highscores.logout();
        assertFalse(Highscores.add(0));
    }

    @Test
    public final void testAdd() {
        assertTrue(Highscores.add(1));
    }

    @Test
    public final void testGet() {
        final Highscore test = Highscores.get(1).get(0);
        assertNotEquals(null, test.getUser());
    }

}
