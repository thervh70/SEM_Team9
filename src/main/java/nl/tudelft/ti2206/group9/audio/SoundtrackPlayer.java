package nl.tudelft.ti2206.group9.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;
import nl.tudelft.ti2206.group9.util.Resource;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

/**
 * Creates a SoundTrackPlayer which you can initialize, start, pause and stop.
 * Besides, you can loop and increase the speed of the soundtrack.
 * The purpose of a SoundtrackPlayer is to play only soundtracks.
 *
 * @author Mitchell
 */
@SuppressWarnings("restriction")
public class SoundtrackPlayer extends AbstractAudioPlayer {

    /**
     * Minimum rate of the SoundtrackPlayer.
     */
    private static final double LOWER = 0.125;
    /**
     * Maximum rate of the SoundtrackPlayer.
     */
    private static final double UPPER = 8.0;
    /**
     * Constant for making the SoundtrackPlayer loop forever.
     */
    private static final int INDEFINITE = -1;
    /**
     * Constant for the default volume level of a soundtrack.
     */
    private static final double DEFAULT_VOLUMELEVEL = 0.5;

    /**
     * The MediaPlayer of a SoundtrackPlayer.
     */
    private MediaPlayer mediaPlayer;
    /**
     * Path of the MediaPlayer.
     */
    private String path;

    /**
     * Creates a SoundtrackPlayer with as input a specific path.
     *
     * @param soundPath given path to the soundtrack.
     */
    public SoundtrackPlayer(final String soundPath) {
        super();
        path = soundPath;
        if (State.isSoundtrackEnabled()) {
            initializeAudio(path);
        }
        // A soundtrackPlayer must always loop.
        this.loopAudio();
        this.setVolume(DEFAULT_VOLUMELEVEL);
    }

    @Override
    protected void initializeAudio(final String source) {
        final String uri = Resource.getURIString(source);
        if (uri == null) {
            return;
        }
        try {
            mediaPlayer = new MediaPlayer(new Media(uri));
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "SoundtrackPlayer.initializeTune(String)",
                    me.getMessage());
        }
    }

    @Override
    public final void play() {
        try {
            if (State.isSoundtrackEnabled()) {
                if (mediaPlayer == null) {
                    initializeAudio(path);
                }
                mediaPlayer.play();
            }
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "SoundtrackPlayer.play()", me.getMessage());
        } catch (NullPointerException ne) { // NOPMD
            // This try-catch block is just here for testing.
            // The play method can result in a NullPointer (according to
            // JUnit), because JUnit can't really play audio neither can Travis.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayer.play()", ne.getMessage());
        }
    }

    @Override
    public final void stop() {
        try {
            if (State.isSoundtrackEnabled()) {
                if (mediaPlayer == null) {
                    initializeAudio(path);
                }
                mediaPlayer.stop();
            }
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "SoundtrackPlayer.stop()", me.getMessage());
        } catch (NullPointerException ne) { // NOPMD
            // This try-catch block is just here for testing.
            // The stop method can result in a NullPointer (according to
            // JUnit), because JUnit can't really play audio neither can Travis.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayer.stop()", ne.getMessage());
        }
    }

    /**
     * Pauses the SoundtrackPlayer,
     * if the sound is enabled.
     */
    public final void pause() {
        try {
            if (State.isSoundtrackEnabled()) {
                if (mediaPlayer == null) {
                    initializeAudio(path);
                }
                mediaPlayer.pause();
            }
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "SoundtrackPlayer.pause()", me.getMessage());
        } catch (NullPointerException ne) { // NOPMD
            // This try-catch block is just here for testing.
            // The pause method can result in a NullPointer (according to
            // JUnit), because JUnit can't really play audio neither can Travis.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayer.play()", ne.getMessage());
        }
    }

    @Override
    public final boolean isRunning() {
        if (mediaPlayer == null) {
            return false;
        } else {
            return mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
        }
    }

    /**
     * Increases the speed of the soundtrack played by the SoundtrackPlayer.
     *
     * @param rate rate to be set to.
     *             It is recommended to keep this rate between 0.125 and 8.0.
     */
    public final void setSpeed(final double rate) {
        if (State.isSoundtrackEnabled() & rate >= LOWER & rate <= UPPER) {
            mediaPlayer.setRate(rate);
        }
    }

    /**
     * Gets the current speed to which the SoundtrackPlayer is set.
     * (For testing purposes.)
     *
     * @return double rate current rate of the SoundtrackPlayer.
     */
    public final double getSpeed() {
        if (State.isSoundtrackEnabled()) {
            return mediaPlayer.getRate();
        }
        return 1.0;
    }

    /**
     * Resets the speed of the soundtrack played by the SoundtrackPlayer,
     * if the soundtrack is enabled.
     */
    public final void resetSpeed() {
        try {
            if (State.isSoundtrackEnabled()) {
                mediaPlayer.setRate(1.0);
            }
        } catch (NullPointerException ne) { // NOPMD
            // This try-catch block is just here for testing.
            // The resetSpeed method can result in a NullPointer (according to
            // JUnit), because JUnit can't really play audio neither can Travis.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayer.resetSpeed()", ne.getMessage());
        }
    }

    /**
     * Loops the SoundtrackPlayer.
     */
    private void loopAudio() {
        try {
            mediaPlayer.setCycleCount(INDEFINITE);
        } catch (NullPointerException ne) { // NOPMD
            // This try-catch block is just here for testing.
            // The loopAudio method can result in a NullPointer (according to
            // JUnit), because JUnit can't really play audio neither can Travis.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayer.loopAudio()", ne.getMessage());
        }
    }

    @Override
    public final String getPath() {
        return path;
    }

    @Override
    public final void setPath(final String location) {
        path = location;
        if (State.isSoundtrackEnabled()) {
            initializeAudio(path);
        }
    }

    @Override
    public void setVolume(final double volumeLevel) {
        try {
        mediaPlayer.setVolume(volumeLevel);
        } catch (NullPointerException ne) { // NOPMD
            // This try-catch block is just here for testing.
            // The setVolume method can result in a NullPointer (according to
            // JUnit), because JUnit can't really play audio neither can Travis.
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.NULLPOINTEREXCEPTION,
                    "SoundtrackPlayer.setVolume()", ne.getMessage());
        }
    }

}
