package nl.tudelft.ti2206.group9.audio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.sound.sampled.Clip;
import javax.sound.sampled.Mixer;

import org.junit.Before;
import org.junit.Test;

/**
 * This testclass tests the AudioPlayer class.
 * @author Mitchell
 *
 */
public class AudioPlayerTest {

	private Mixer testMixer;
	private Clip testClip;
	private AudioPlayer test;

	private Clip otherClip;
	private Mixer otherMixer;
	
    //@Before
    public void setUp() {
    	AudioPlayer test = new AudioPlayer(testMixer, testClip);
		test.initialiseTune("8bit.aiff");
    }
	
	/**
	 * 
	 */
	//@Test
	public void testPlay() {
		test.play();
	//	assertTrue(test.getClip().isRunning());
		test.stop();
	}

	//@Test
	public void testStop() {
		test.play();
		test.stop();
	//	assertFalse(test.getClip().isRunning());
	}
	
	//@Test
	public void testSetClip() {
		test.setClip(otherClip);
	//	assertEquals(otherClip, test.getClip());
	}

	//@Test
	public void testSetMixer() {
		test.setMixer(otherMixer);
	//	assertEquals(otherMixer, test.getMixer());
	}

}
