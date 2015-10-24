package nl.tudelft.ti2206.group9.shop.soundtrack;

import nl.tudelft.ti2206.group9.audio.SoundtrackPlayer;
import nl.tudelft.ti2206.group9.shop.ShopItem;

/**
 * The abstract shop item class for soundtracks, based on the
 * requirements from the ShopItem interface.
 * Besides, every soundtrack item has it's own SoundtrackPlayer.
 *
 * @author Mitchell.
 */
public abstract class AbstractSoundtrack implements ShopItem {

    /**
     * The price of this soundtrack in the shop.
     */
    private final int soundtrackPrice;

    /**
     * The soundtrack name to display in the shop.
     */
    private final String soundtrackName;

    /**
     * The SoundtrackPlayer of the soundtrack that can be bought.
     */
    private final SoundtrackPlayer soundtrackPlayer;

    /**
     * The beginning part of the path to every audio file.
     */
    private static final String AUDIOPATH = "nl/tudelft/ti2206/group9/audio/";

    /**
     * Constructor for a soundtrack that can be bought or is default.
     * Every (buyable) soundtrack consists of a name, a price
     * and the soundtrackPlayer for playing it.
     *
     * @param price              Price of this soundtrack in shop.
     * @param name               Name of the soundtrack to display.
     * @param soundtrackFileName Name of soundtrack file.
     */
    public AbstractSoundtrack(final int price, final String name,
                              final String soundtrackFileName) {
        soundtrackPrice = price;
        soundtrackName = name;
        soundtrackPlayer = loadSoundtrackPlayer(soundtrackFileName);
    }

    @Override
    public int getItemPrice() {
        return soundtrackPrice;
    }

    @Override
    public String getItemName() {
        return soundtrackName;
    }

    /**
     * Gets the SoundtrackPlayer of a Soundtrack.
     * This SoundtrackPlayer will be or is already initialized.
     *
     * @return SoundtrackPlayer that will be / is initialized with a soundtrack.
     */
    public SoundtrackPlayer getSoundtrackPlayer() {
        return soundtrackPlayer;
    }

    /**
     * Loads the soundtrackPlayer that is to be used
     * by the buyable soundtrack.
     *
     * @param soundtrackFileName the name of the file to be initialized.
     * @return SountrackPlayer initialized based on the given file name.
     */
    private SoundtrackPlayer loadSoundtrackPlayer(
            final String soundtrackFileName) {
        final String soundtrackPath = AUDIOPATH + soundtrackFileName + ".mp3";
        return new SoundtrackPlayer(soundtrackPath);
    }

}
