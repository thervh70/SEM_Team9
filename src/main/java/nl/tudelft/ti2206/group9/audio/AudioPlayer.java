package nl.tudelft.ti2206.group9.audio;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;
import nl.tudelft.ti2206.group9.level.State;

/**
 * Creates an AudioPlayer which you can initialize, start and stop.
 * @author Mitchell
 *
 */
@SuppressWarnings("restriction")
public class AudioPlayer {

	private AudioClip audioClip;
	private String path;

	/**
	 * Creates an AudioPlayer with as input a specific path.
	 * @param soundPath given path
	 */
	public AudioPlayer(final String soundPath) {
		path = soundPath;
		if (State.isSoundEnabled()) {
			initializeTune(path);
		}
	}

	/**
	 * Gets the audio file and prepares it for streaming.
	 * @param path leads to the soundtrack.
	 */
	private void initializeTune(final String path) {
		try {
			audioClip = new AudioClip(new File(path).toURI().toURL()
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
				if (State.isSoundEnabled()) {
					if (audioClip == null) {
						initializeTune(path);
					}
					audioClip.play();
				}
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
			try {
				if (State.isSoundEnabled()) {
					if (audioClip == null) {
						initializeTune(path);
					}
					audioClip.stop();
				}
			} catch (MediaException me) {
				me.printStackTrace();
			}
		}
	}

	/**
	 * Checks if the current audioClip is running.
	 * @return boolean true if running, false if not.
	 */
	public final boolean isRunning() {
		if (audioClip == null) {
			return false;
		} else {
			return audioClip.isPlaying();
		}
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
		if (State.isSoundEnabled()) {
			initializeTune(path);
		}
	}

}
