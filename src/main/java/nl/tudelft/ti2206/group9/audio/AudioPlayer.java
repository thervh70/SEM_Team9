package nl.tudelft.ti2206.group9.audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Creates an AudioPlayer which you can initialize, start and stop.
 * @author Mitchell
 *
 */
public class AudioPlayer {

	private static Mixer mixer;
	private static Clip clip;
	
	/**
	 * 
	 * @param myMixer given mixer
	 * @param myClip given clip
	 */
	public AudioPlayer(final Mixer myMixer, final Clip myClip) {
		mixer = myMixer;
		clip = myClip;
	}
	
	/**
	 * Start the initialised soundtrack.
	 */
	public void play() {
		clip.start();
	}
	
	/**
	 * Stops the initialised soundtrack.
	 */
	public void stop() {
		clip.stop();
	}
	
	/**
	 * Gets the audio file and prepares it for streaming.
	 * @param path leads to the soundtrack.
	 */
	public void initialiseTune(final String path) {
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		
		try { 
			clip = (Clip) mixer.getLine(dataInfo); 
			} catch (LineUnavailableException lue) { 
				lue.printStackTrace(); 
			}
		
		try	{
			URL soundUrl = AudioPlayer.class.getResource(path);
			AudioInputStream audioStream = 
						AudioSystem.getAudioInputStream(soundUrl);
			clip.open(audioStream);
		} catch (LineUnavailableException lue) {
			lue.printStackTrace(); 
		} catch (UnsupportedAudioFileException uafe) {
			uafe.printStackTrace(); 
		} catch (IOException ioe) { 
			ioe.printStackTrace(); 
		}
	}
	
	/**
	 * Return the current clip.
	 * @return current clip.
	 */
	public Clip getClip() {
		return clip;
	}

	/**
	 * Return the current clip.
	 * @return current clip.
	 */
	public Clip setClip() {
		return clip;
	}
	
	/**
	 * @param clip the clip to set
	 */
	public void setClip(final Clip clip) {
		AudioPlayer.clip = clip;
	}
	
	/**
	 * Return the current mixer.
	 * @return current mixer.
	 */
	public Mixer getMixer() {
		return mixer;
	}

	/**
	 * @param mixer the mixer to set
	 */
	public void setMixer(final Mixer mixer) {
		AudioPlayer.mixer = mixer;
	}

	
}
