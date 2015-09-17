package nl.tudelft.ti2206.group9.audio;

import java.io.File;

import javafx.scene.media.AudioClip;

/**
 * Creates an AudioPlayer which you can initialise, start and stop.
 * @author Mitchell
 *
 */
public class AudioPlayer {

	private static AudioClip audioPlayer;
	private static String path;
	
	/**
	 * Creates an AudioPlayer with as input a specific path.
	 * @param soundPath given path
	 */
	public AudioPlayer(final String soundPath) {
		this.path = soundPath;
		initialiseTune(path);
	}

	/**
	 * Gets the audio file and prepares it for streaming.
	 * @param path leads to the soundtrack.
	 */
	public final void initialiseTune(final String path) {
		audioPlayer = new AudioClip(new File(path).toURI().toString());
	}
	
	/**
	 * Starts the initialised soundtrack.
	 */
	public final synchronized void play() {
		synchronized(this) {
			audioPlayer.play();
		}
	}
	
	/**
	 * Stops the initialised soundtrack.
	 */
	public final synchronized void stop() {
		synchronized(this) {
			audioPlayer.stop();			
		}
	}
	
	/**
	 * Checks if the current audioPlayer is running.
	 * @return boolean true if running, false if not.
	 */
	public final boolean isRunning() {
		return (audioPlayer.isPlaying());
	}
	
	/**
	 * Returns the path of the AudioPlayer.
	 * @return path leads to the soundtrack.
	 */
	public final String getPath() {
		return path;
	}

	/**
	 * Sets the path of the AudioPlayer and reinitializes it.
	 * @param location new path to a file.
	 */
	public final void setPath(final String location) {
		AudioPlayer.path = location;
		initialiseTune(path);
	}

}
