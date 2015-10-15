package nl.tudelft.ti2206.group9.audio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        final SoundtrackPlayer test = new SoundtrackPlayer(testPath);
        test.play();
        test.stop();
        assertFalse(test.isRunning());
    }

    @Test
    public void testPause() {
        final SoundtrackPlayer test = new SoundtrackPlayer(testPath);
        test.play();
        test.pause();
        assertFalse(test.isRunning());
    }

    @Test
    public void testSetPath() {
        final SoundtrackPlayer test = new SoundtrackPlayer(testPath);
        final String testDifferentPath = "src/main/resources/"
                + "nl/tudelft/ti2206/group9/audio/coin.wav";
        test.setPath(testDifferentPath);
        assertTrue(testDifferentPath.equals(test.getPath()));
    }

}
