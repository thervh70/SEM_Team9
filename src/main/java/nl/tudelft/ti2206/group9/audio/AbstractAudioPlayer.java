package nl.tudelft.ti2206.group9.audio;

/**
 * Abstract class AudioPlayer which is the basis for all AudioPlayers.
 * Every AudioPlayer must be able to play, stop and initialize.
 * Furthermore, every AudioPlayer contains some standard getters and a setter.
 *
 * @author Mitchell
 */
public abstract class AbstractAudioPlayer {

    /**
     * Gets the audio file and prepares it for streaming.
     *
     * @param source leads to the audio file.
     */
    protected abstract void initializeAudio(final String source);

    /**
     * Starts playing the initialized audio.
     * The sound should be enabled for this.
     */
    public abstract void play();

    /**
     * Stops the initialized audio.
     * The sound should be enabled for this.
     */
    public abstract void stop();

    /**
     * Checks if the current AudioPlayer is running.
     *
     * @return boolean true if running, false if not.
     */
    public abstract boolean isRunning();

    /**
     * Returns the path of the AudioPlayer.
     *
     * @return path leads to the audio.
     */
    public abstract String getPath();

    /**
     * Sets the path of the AudioPlayer and reinitializes it.
     *
     * @param location new path to a file.
     */
    public abstract void setPath(final String location);

}
