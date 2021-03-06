package nl.tudelft.ti2206.group9.audio;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;
import nl.tudelft.ti2206.group9.util.Resource;

/**
 * Creates a SoundEffectPlayer which you can initialize, start and stop.
 * The purpose of a SoundEffectPlayer is to play only sound effects.
 *
 * @author Mitchell
 */
public class SoundEffectPlayer extends AbstractAudioPlayer {

    /**
     * The AudioClip of a SoundEffectPlayer.
     */
    private AudioClip audioClip;
    /**
     * Path of the AudioClip.
     */
    private String path;

    /**
     * Creates a SoundEffectPlayer with as input a specific path.
     *
     * @param soundPath given path to the sound effect.
     */
    public SoundEffectPlayer(final String soundPath) {
        super();
        path = soundPath;
        if (State.isSoundEffectsEnabled()) {
            initializeAudio(path);
        }
        this.setVolume(State.getSoundEffectVolume());
    }

    @Override
    protected final void initializeAudio(final String source) {
        final String uri = Resource.getURIString(source);
        if (uri == null) {
            return;
        }
        try {
            audioClip = new AudioClip(uri);
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "SoundEffectPlayer.initializeAudio(String)",
                   me.getMessage());
        }
    }

    @Override
    public final void play() {
        try {
            if (State.isSoundEffectsEnabled()) {
                if (audioClip == null) {
                    initializeAudio(path);
                }
                audioClip.play();
            }
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "SoundEffectPlayer.play()", me.getMessage());
        }
    }

    @Override
    public final void stop() {
        try {
            if (State.isSoundEffectsEnabled()) {
                if (audioClip == null) {
                    initializeAudio(path);
                }
                audioClip.stop();
            }
        } catch (MediaException me) {
            OBSERVABLE.notify(Category.ERROR, Error.MEDIAEXCEPTION,
                    "SoundEffectPlayer.stop()", me.getMessage());
        }
    }

    @Override
    public final boolean isRunning() {
        if (audioClip == null) {
            return false;
        } else {
            return audioClip.isPlaying();
        }
    }

    @Override
    public final String getPath() {
        return path;
    }

    @Override
    public final void setPath(final String location) {
        path = location;
        if (State.isSoundEffectsEnabled()) {
            initializeAudio(path);
        }
    }

    @Override
    public void setVolume(final double volumeLevel) {
        if (audioClip == null) {
            return;
        }
        audioClip.setVolume(volumeLevel);
    }

}
