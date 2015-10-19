package nl.tudelft.ti2206.group9.audio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * SoundtrackPlayerTest tests the SoundtrackPlayer class.
 *
 * @author Mitchell
 */
public class SoundtrackPlayerTest {

    /**
     * Path to a test audio file.
     */
    private static final String TEST_PATH =
            "nl/tudelft/ti2206/group9/audio/soundtrack.mp3";

    @Test
    public void testStop() {
        final SoundtrackPlayer test = new SoundtrackPlayer(TEST_PATH);
        test.play();
        test.stop();
        assertFalse(test.isRunning());
    }

    @Test
    public void testPause() {
        final SoundtrackPlayer test = new SoundtrackPlayer(TEST_PATH);
        test.play();
        test.pause();
        assertFalse(test.isRunning());
    }

    @Test
    public void testSetPath() {
        final SoundtrackPlayer test = new SoundtrackPlayer(TEST_PATH);
        final String testDifferentPath =
                "nl/tudelft/ti2206/group9/audio/coin.wav";
        test.setPath(testDifferentPath);
        assertTrue(testDifferentPath.equals(test.getPath()));
    }

}
