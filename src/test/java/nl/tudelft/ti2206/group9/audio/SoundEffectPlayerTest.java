package nl.tudelft.ti2206.group9.audio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * SoundEffectPlayerTest tests the SoundEffectPlayer class.
 * @author Mitchell
 */
public class SoundEffectPlayerTest {

    @Test
    public void testStop() {
        final String testPath = "nl/tudelft/ti2206/group9/audio/soundtrack.mp3";
        final SoundEffectPlayer test = new SoundEffectPlayer(testPath);
        test.play();
        test.stop();
        assertFalse(test.isRunning());
    }

    @Test
    public void testSetPath() {
        final String testPath = "nl/tudelft/ti2206/group9/audio/soundtrack.mp3";
        final SoundEffectPlayer test = new SoundEffectPlayer(testPath);
        final String testDifferentPath =
                "nl/tudelft/ti2206/group9/audio/coin.wav";
        test.setPath(testDifferentPath);
        assertTrue(testDifferentPath.equals(test.getPath()));
    }

}
