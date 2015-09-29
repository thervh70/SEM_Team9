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
	public void testPlay() throws MediaException {
		String testPath = "src/main/resources/"
				+ "nl/tudelft/ti2206/group9/audio/soundtrack.aiff";
		AudioPlayer test = new AudioPlayer(testPath);
		test.play();
		// Sounds don't play in JUnit.
		assertFalse(test.isRunning());
	}

	@Test
	public void testStop() throws MediaException {
		String testPath = "src/main/resources/"
				+ "nl/tudelft/ti2206/group9/audio/soundtrack.aiff";
		AudioPlayer test = new AudioPlayer(testPath);
		test.play();
		test.stop();
		assertFalse(test.isRunning());
	}

	@Test
	public void testSetPath() {
		String testPath = "src/main/resources/"
				+ "nl/tudelft/ti2206/group9/audio/soundtrack.aiff";
		AudioPlayer test = new AudioPlayer(testPath);
		String testDifferentPath = "src/main/resources/"
				+ "nl/tudelft/ti2206/group9/audio/test.aiff";
		test.setPath(testDifferentPath);
		assertTrue(testDifferentPath.equals(test.getPath()));
	}

}
