package nl.tudelft.ti2206.group9.audio;

/**
 * Abstract class AudioPlayer which is the basis for all AudioPlayers.
 * Every AudioPlayer must be able to play, stop and initialize.
 * Furthermore, every AudioPlayer contains some standard getters and a setter.
 * @author Mitchell
 */
public abstract class AbstractAudioPlayer {

    /**
     * Gets the audio file and prepares it for streaming.
     * @param source leads to the audio file.
     */
    protected abstract void initializeTune(final String source);

    /**
     * Starts the initialized tune.
     * If the AudioPlayer has to be checked whether it is running,
     * it is checked. If not, then it's not.
     */
    public abstract void play();

    /**
     * Stops the initialized soundtrack if
     * the AudioPlayer is actually running and if the sound is enabled.
     */
    public abstract void stop();

    /**
     * Checks if the current audioClip is running.
     * @return boolean true if running, false if not.
     */
    public abstract boolean isRunning();

    /**
     * Returns the path of the AudioPlayer.
     * @return path leads to the soundtrack.
     */
    public abstract String getPath();

    /**
     * Sets the path of the AudioPlayer and reinitializes it.
     * @param location new path to a file.
     */
    public abstract void setPath(final String location);

}
