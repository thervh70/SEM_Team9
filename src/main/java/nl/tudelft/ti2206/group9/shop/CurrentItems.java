package nl.tudelft.ti2206.group9.shop;

import nl.tudelft.ti2206.group9.audio.SoundtrackPlayer;
import nl.tudelft.ti2206.group9.shop.skin.AbstractSkin;

/**
 * This class is dedicated to save all current items the player is using
 * (e.g. currentSkin). In this class you can get and set these items.
 *
 * @author Mathias and Mitchell
 */
public final class CurrentItems {

    /** Skin to be used. */
    private static AbstractSkin skin;
    /** The AudioPlayer to be used for background music. */
    private static SoundtrackPlayer currentSoundtrackPlayer =
            new SoundtrackPlayer(
                    "nl/tudelft/ti2206/group9/audio/"
                    + "soundtrack_Radioactive.mp3");

    /** Private constructor. */
    private CurrentItems() { }

    /**
     * Resets the shop items that are currently equipped/activated,
     * by setting these items to the default skin and soundtrack.
     */
    public static void reset() {
        setSkin(ShopItemLoader.getNoobSkin());
        setSoundtrackPlayer(new SoundtrackPlayer("nl/tudelft/ti2206/"
                + "group9/audio/soundtrack_Radioactive.mp3"));
    }

    /**
     * Getter for the current skin.
     * @return The skin.
     */
    public static AbstractSkin getSkin() {
        return skin;
    }

    /**
     * If new skins are bought and applied it can be done via this setter.
     * @param newSkin The new skin.
     */
    public static void setSkin(final AbstractSkin newSkin) {
        skin = newSkin;
    }

    /**
     * Every GameScene has an AudioPlayer for the soundtrack.
     * @return the soundtrack AudioPlayer.
     */
    public static SoundtrackPlayer getSoundtrackPlayer() {
        return currentSoundtrackPlayer;
    }

    /**
     * Every GameScene has an AudioPlayer for the soundtrack.
     * @param soundtrackPlayer an initialized soundtrackPlayer
     * with a new soundtrack.
     */
    public static void setSoundtrackPlayer(
            final SoundtrackPlayer soundtrackPlayer) {
        currentSoundtrackPlayer = soundtrackPlayer;
    }
}
