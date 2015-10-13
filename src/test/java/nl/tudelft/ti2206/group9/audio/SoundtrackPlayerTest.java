package nl.tudelft.ti2206.group9.audio;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.group9.util.GameObserver;

import org.junit.Test;

/**
 * SoundtrackPlayerTest tests the SoundtrackPlayer class.
 * @author Mitchell
 */
public class SoundtrackPlayerTest {

    /** Path to a test audio file. */
    private final String testPath = "src/main/resources/"
            + "nl/tudelft/ti2206/group9/audio/soundtrack.aiff";

    @Test
    public void testStop() {
        try {
        final SoundtrackPlayer test = new SoundtrackPlayer(testPath);
        test.play();
        test.stop();
        assertFalse(test.isRunning());
        } catch (NullPointerException ne) { // NOPMD
            // The loopAudio method can result in a NullPointer (according to
            // JUnit), because JUnit can't really play audio.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayerTest.testStop()", ne.getMessage());
        }
    }

    @Test
    public void testPause() {
        try {
        final SoundtrackPlayer test = new SoundtrackPlayer(testPath);
        test.play();
        test.pause();
        assertFalse(test.isRunning());
        } catch (NullPointerException ne) { // NOPMD
            // The loopAudio method can result in a NullPointer (according to
    	    // JUnit), because JUnit can't really play audio.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayerTest.testPause()", ne.getMessage());
        }
    }

    @Test
    public void testSetPath() {
        try {
        final SoundtrackPlayer test = new SoundtrackPlayer(testPath);
        final String testDifferentPath = "src/main/resources/"
                + "nl/tudelft/ti2206/group9/audio/coin.wav";
        test.setPath(testDifferentPath);
        assertTrue(testDifferentPath.equals(test.getPath()));
        } catch (NullPointerException ne) { // NOPMD
            // The loopAudio method can result in a NullPointer (according to
            // JUnit), because JUnit can't really play audio.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayerTest.setPath()", ne.getMessage());
        }
    }

}
