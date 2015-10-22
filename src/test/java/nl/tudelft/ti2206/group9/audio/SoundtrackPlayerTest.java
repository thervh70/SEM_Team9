package nl.tudelft.ti2206.group9.audio;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testExceptionInitialize() {
        final String testPath = "fileNotFound.aiff";
        final SoundtrackPlayer test = new SoundtrackPlayer(testPath);
        test.initializeAudio(testPath);
        test.play();
        assertFalse(test.isRunning());
    }

}
