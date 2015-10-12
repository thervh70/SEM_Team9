package nl.tudelft.ti2206.group9.audio;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

/**
 * Creates an AudioPlayer which you can initialize, start and stop.
 * @author Mitchell
 */
@SuppressWarnings("restriction")
public class AudioPlayer {

    /** Minimum rate of the AudioPlayer. */
    private static final double LOWER = 0.125;
    /** Maximum rate of the AudioPlayer. */
    private static final double UPPER = 8.0;

    /** The AudioClip of an AudioPlayer. */
    private AudioClip audioClip;
    /** Path of the AudioClip. */
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
     * @param source leads to the audio file.
     */
    private void initializeTune(final String source) {
        try {
            audioClip = new AudioClip(new File(source).toURI().toURL()
                    .toString());
        } catch (MalformedURLException mue) {
            OBSERVABLE.notify(Category.ERROR, Error.MALFORMEDURLEXCEPTION,
                    "AudioPlayer.initializeTune(String)", mue.getMessage());
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "AudioPlayer.initializeTune(String)", me.getMessage());
        }
    }

    /**
     * Starts the initialized tune.
     * If the AudioPlayer has to be checked whether it is running,
     * it is checked. If not, then it's not.
     * @param checkStopped indicates whether to check the audioPlayer.
     */
    public final void play(final boolean checkStopped) {
        try {
            if (State.isSoundEnabled()) {
                if (audioClip == null) {
                    initializeTune(path);
                }
                if (checkStopped) {
                    if (!this.isRunning()) {
                        audioClip.play();
                    }
                } else {
                    audioClip.play();
                }
            }
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "AudioPlayer.play()", me.getMessage());
        }
    }

    /**
     * Stops the initialized soundtrack if
     * the AudioPlayer is actually running and if the sound is enabled.
     */
    public final void stop() {
        try {
            if (State.isSoundEnabled()) {
                if (audioClip == null) {
                    initializeTune(path);
                }
                audioClip.stop();
            }
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "AudioPlayer.stop()", me.getMessage());
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

    /**
     * Increases the speed of the music played by the AudioPlayer.
     * @param rate rate to be set to.
     * It is recommended to keep this rate between 0.125 and 8.0.
     */
    public final void setSpeed(final double rate) {
        if (State.isSoundEnabled() & rate >= LOWER & rate <= UPPER) {
            audioClip.setRate(rate);
        }
    }

    /**
     * Gets the current speed to which the AudioPlayer is set.
     * (For testing purposes.)
     * @return double rate current rate of the AudioPlayer.
     */
    public final double getSpeed() {
        if (State.isSoundEnabled()) {
            return audioClip.getRate();
        }
        return 1.0;
    }

    /**
     * Resets the speed of the music played by the AudioPlayer.
     */
    public final void resetSpeed() {
        if (State.isSoundEnabled()) {
            audioClip.setRate(1.0);
        }
    }

}
