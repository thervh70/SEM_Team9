package nl.tudelft.ti2206.group9.audio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.tudelft.ti2206.group9.audio.AudioPlayer;

import org.junit.Before;
import org.junit.Test;

/**
 * AudioPlayerTest tests the AudioPlayer class.
 * @author Mitchell
 */
public class AudioPlayerTest {

	private static String testPath;
	private AudioPlayer test;
	
	@Before
	public void setUp() throws Exception {
		testPath = "src/main/java/nl/tudelft/ti2206/group9/audio/sounds/soundtrack.aiff";
		test = new AudioPlayer(testPath);
	}
	
	@Test
	public void testPlay() {
		test.play();
		assertTrue(test.isRunning());
	}
	
	@Test
	public void testStop() {
		test.play();
		test.stop();
		assertFalse(test.isRunning());
	}

	@Test
	public void testSetPath() {
		String testDifferentPath = "test123";
		test.setPath(testDifferentPath);
		assertTrue(testDifferentPath.equals(test.getPath()));
	}

}