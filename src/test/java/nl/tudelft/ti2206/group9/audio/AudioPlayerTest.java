package nl.tudelft.ti2206.group9.audio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;

import org.junit.Test;

/**
 * AudioPlayerTest tests the AudioPlayer class.
 * @author Mitchell
 *
 */
public class AudioPlayerTest {

	private static Mixer testMixer;
	private static Clip testClip;
	private AudioPlayer test = new AudioPlayer(testMixer, testClip);

	private static Clip otherClip;
	private static Mixer otherMixer;
	
	@Test
	public void testPlay() {
		test.initialiseTune("sounds/soundtrack.aiff");
		test.play();
		// The result is false, because JUnit directly stops the soundtrack.
		assertFalse(test.getClip().isRunning());
		test.stop();
	}

	@Test
	public void testStop() {
		test.initialiseTune("sounds/soundtrack.aiff");
		test.play();
		test.stop();
		assertFalse(test.getClip().isRunning());
	}
	
	@Test
	public void testSetClip() {
		test.setClip(otherClip);
		assertEquals(otherClip, test.getClip());
	}

	@Test
	public void testSetMixer() {
		test.setMixer(otherMixer);
		assertEquals(otherMixer, test.getMixer());
	}
	
}
