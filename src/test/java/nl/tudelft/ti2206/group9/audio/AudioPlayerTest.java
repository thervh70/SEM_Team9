package nl.tudelft.ti2206.group9.audio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javafx.scene.media.MediaException;

import org.junit.Test;

/**
 * AudioPlayerTest tests the AudioPlayer class.
 * @author Mitchell
 */
@SuppressWarnings("restriction")
public class AudioPlayerTest {

	@Test
	public void testStop() throws MediaException {
		final String testPath = "src/main/resources/"
				+ "nl/tudelft/ti2206/group9/audio/soundtrack.aiff";
		final AudioPlayer test = new AudioPlayer(testPath);
		test.play(false);
		test.stop();
		assertFalse(test.isRunning());
	}

	@Test
	public void testSetPath() {
		final String testPath = "src/main/resources/"
				+ "nl/tudelft/ti2206/group9/audio/soundtrack.aiff";
		final AudioPlayer test = new AudioPlayer(testPath);
		final String testDifferentPath = "src/main/resources/"
				+ "nl/tudelft/ti2206/group9/audio/test.aiff";
		test.setPath(testDifferentPath);
		assertTrue(testDifferentPath.equals(test.getPath()));
	}

}
