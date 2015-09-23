package nl.tudelft.ti2206.group9.audio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javafx.scene.media.MediaException;

import org.junit.Before;
import org.junit.Test;

/**
 * AudioPlayerTest tests the AudioPlayer class.
 * @author Mitchell
 */
@SuppressWarnings("restriction")
public class AudioPlayerTest {

	private static String testPath;
	private AudioPlayer test;

	@Before
	public void setUp() throws Exception {
		testPath = "src/main/resources/" 
				+ "nl/tudelft/ti2206/group9/audio/soundtrack.aiff";
		test = new AudioPlayer(testPath);
	}

	@Test
	public void testPlay() throws MediaException {
		test.play();
		assertTrue(test.isRunning());
	}

	@Test
	public void testStop() throws MediaException {
		test.play();
		test.stop();
		assertFalse(test.isRunning());
	}

	@Test
	public void testSetPath() {
		String testDifferentPath = "src/main/resources/" 
				+ "nl/tudelft/ti2206/group9/audio/test.aiff";
		test.setPath(testDifferentPath);
		assertTrue(testDifferentPath.equals(test.getPath()));
	}

}
