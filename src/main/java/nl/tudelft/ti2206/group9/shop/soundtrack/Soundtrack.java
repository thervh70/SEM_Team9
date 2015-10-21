package nl.tudelft.ti2206.group9.shop.soundtrack;

import java.util.HashMap;

import nl.tudelft.ti2206.group9.audio.SoundtrackPlayer;
import nl.tudelft.ti2206.group9.shop.ShopItem;

/**
 * The shop item class for soundtracks, based on the requirements from the
 * ShopItem interface.
 * Besides, every soundtrack item has it's own SoundtrackPlayer.
 * Furthermore, a map of all unlocked soundtracks is maintained.
 * @author Mitchell.
 */
public class Soundtrack implements ShopItem {

    /** The Animals Soundtrack that can be bought. */
    private static AnimalsSoundtrack animalsSoundtrack;

    /** The Default Soundtrack that has been bought. */
    private static DefaultSoundtrack defaultSoundtrack;

    /** The Duck Tales Soundtrack that can be bought. */
    private static DuckTalesSoundtrack ducktalesSoundtrack;

    /** The Mario Soundtrack that can be bought. */
    private static MarioSoundtrack marioSoundtrack;

    /** The Nyan Cat Soundtrack that can be bought. */
    private static NyanCatSoundtrack nyancatSoundtrack;

    /** The Shake It Off Soundtrack that can be bought. */
    private static ShakeItOffSoundtrack shakeitoffSoundtrack;

    /** The price of this soundtrack in the shop. */
    private final int soundtrackPrice;

    /** The soundtrack name to display in the shop. */
    private final String soundtrackName;

    /** The SoundtrackPlayer of the soundtrack that can be bought. */
    private final SoundtrackPlayer soundtrackPlayer;

    /** The beginning part of the path to every audio file. */
    private static final String AUDIOPATH = "src/main/resources/"
            + "nl/tudelft/ti2206/group9/audio/";

    /** HashMap used to store which soundtracks are unlocked. */
    private static HashMap<String, Boolean> unlockedMap = new HashMap<>();

    /**
     * Constructor for a soundtrack that can be bought or is default.
     * Every (buyable) soundtrack consists of a name, a price
     * and the soundtrackPlayer for playing it.
     * @param price                   Price of this soundtrack in shop.
     * @param name                    Name of the soundtrack to display.
     * @param soundtrackFileName      Name of soundtrack file.
     */
    public Soundtrack(final int price, final String name,
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
     * @return SoundtrackPlayer that will be / is initialized with a soundtrack.
     */
    public SoundtrackPlayer getSoundtrackPlayer() {
        return soundtrackPlayer;
    }

    /**
     * Loads the soundtrackPlayer that is to be used
     * by the buyable soundtrack.
     * @param soundtrackFileName the name of the file to be initialized.
     * @return SountrackPlayer initialized based on the given file name.
     */
    private static SoundtrackPlayer loadSoundtrackPlayer(
           final String soundtrackFileName) {
        final String soundtrackPath = AUDIOPATH + soundtrackFileName + ".mp3";
        return new SoundtrackPlayer(soundtrackPath);
    }

    /**
     * Creating the HashMap for the soundtrack's unlocked values.
     * Only the default soundtrack is unlocked by default.
     */
    public static void createUnlockedSoundtrackHashmap() {
        unlockedMap.put("Animals", false);
        unlockedMap.put("Default", true);
        unlockedMap.put("Duck Tales", false);
        unlockedMap.put("Mario", false);
        unlockedMap.put("Nyan Cat", false);
        unlockedMap.put("Shake It Off", false);
    }

    /**
     * Gets the unlocked value for a soundtrack.
     * @param soundtrackName Name of a soundtrack.
     * @return boolean unlocked or not.
     */
    public static boolean getUnlockedSoundtrack(final String soundtrackName) {
        return unlockedMap.get(soundtrackName);
    }

    /**
     * Sets the unlocked value for a soundtrack.
     * @param soundtrackName Soundtrack to change the value for.
     * @param unlocked new unlocked value.
     */
    public static void setUnlockedSoundtrack(final String soundtrackName,
            final boolean unlocked) {
        unlockedMap.replace(soundtrackName, unlocked);
    }

    /**
     * Gets the Animals soundtrack that can be bought.
     * @return animalSoundtrack item (AnimalsSoundtrack)
     */
    public static AnimalsSoundtrack getAnimalsSoundtrack() {
        return animalsSoundtrack;
    }

    /**
     * Sets the Animals soundtrack that can be bought.
     * @param soundtrack a given AnimalsSoundtrack to set.
     */
    public static void setAnimalsSoundtrack(
            final AnimalsSoundtrack soundtrack) {
        animalsSoundtrack = soundtrack;
    }

    /**
     * Gets the Default Soundtrack that can be bought.
     * @return defaultSoundtrack item (DefaultSoundtrack)
     */
    public static DefaultSoundtrack getDefaultSoundtrack() {
        return defaultSoundtrack;
    }

    /**
     * Sets the Default soundtrack that can be bought.
     * @param soundtrack a given DefaultSoundtrack to set.
     */
    public static void setDefaultSoundtrack(
            final DefaultSoundtrack soundtrack) {
        defaultSoundtrack = soundtrack;
    }

    /**
     * Gets the Duck Tales soundtrack that can be bought.
     * @return ducktalesSoundtrack item (DuckTalesSoundtrack)
     */
    public static DuckTalesSoundtrack getDuckTalesSoundtrack() {
        return ducktalesSoundtrack;
    }

    /**
     * Sets the Duck Tales soundtrack that can be bought.
     * @param soundtrack a given DuckTalesSoundtrack to set.
     */
    public static void setDuckTalesSoundtrack(
            final DuckTalesSoundtrack soundtrack) {
        ducktalesSoundtrack = soundtrack;
    }

    /**
     * Gets the Mario soundtrack that can be bought.
     * @return marioSoundtrack item (MarioSoundtrack)
     */
    public static MarioSoundtrack getMarioSoundtrack() {
        return marioSoundtrack;
    }

    /**
     * Sets the Mario soundtrack that can be bought.
     * @param soundtrack a given MarioSoundtrack to set.
     */
    public static void setMarioSoundtrack(final MarioSoundtrack soundtrack) {
        marioSoundtrack = soundtrack;
    }

    /**
     * Gets the Nyan Cat soundtrack that can be bought.
     * @return nyancatSoundtrack item (NyanCatSoundtrack)
     */
    public static NyanCatSoundtrack getNyanCatSoundtrack() {
        return nyancatSoundtrack;
    }

    /**
     * Sets the Nyan Cat soundtrack that can be bought.
     * @param soundtrack a given NyanCatSoundtrack to set.
     */
    public static void setNyanCatSoundtrack(
            final NyanCatSoundtrack soundtrack) {
        nyancatSoundtrack = soundtrack;
    }

    /**
     * Gets the Shake It Off soundtrack that can be bought.
     * @return shakeitoffSoundtrack item (ShakeItOffSoundtrack)
     */
    public static ShakeItOffSoundtrack getShakeItOffSoundtrack() {
        return shakeitoffSoundtrack;
    }

    /**
     * Sets the Shake It Off soundtrack that can be bought.
     * @param soundtrack a given ShakeItOffSoundtrack to set.
     */
    public static void setShakeItOffSoundtrack(
            final ShakeItOffSoundtrack soundtrack) {
        shakeitoffSoundtrack = soundtrack;
    }

}
