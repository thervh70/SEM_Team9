package nl.tudelft.ti2206.group9.audio;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;

/**
 * Creates an AudioPlayer which you can initialize, start and stop.
 * @author Mitchell
 *
 */
@SuppressWarnings("restriction")
public class AudioPlayer {

	private AudioClip audioPlayer;
	private String path;

	/**
	 * Creates an AudioPlayer with as input a specific path.
	 * @param soundPath given path
	 */
	public AudioPlayer(final String soundPath) {
		path = soundPath;
		initializeTune(path);
	}

	/**
	 * Gets the audio file and prepares it for streaming.
	 * @param path leads to the soundtrack.
	 */
	private void initializeTune(final String path) {
		try {
			audioPlayer = new AudioClip(new File(path).toURI().toURL()
					.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (MediaException me) {
			me.printStackTrace();
		}
	}

	/**
	 * Starts the initialized soundtrack.
	 */
	public final synchronized void play() {
		synchronized (this) {
			try {
				audioPlayer.play();
			} catch (MediaException me) {
				me.printStackTrace();
			}
		}
	}

	/**
	 * Stops the initialized soundtrack.
	 */
	public final synchronized void stop() {
		synchronized (this) {
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
		path = location;
		initializeTune(path);
	}

}
