package nl.tudelft.ti2206.group9.shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.ti2206.group9.shop.skin.AndySkin;
import nl.tudelft.ti2206.group9.shop.skin.BoySkin;
import nl.tudelft.ti2206.group9.shop.skin.CaptainSkin;
import nl.tudelft.ti2206.group9.shop.skin.IronManSkin;
import nl.tudelft.ti2206.group9.shop.skin.NoobSkin;
import nl.tudelft.ti2206.group9.shop.skin.PlankSkin;
import nl.tudelft.ti2206.group9.shop.skin.AbstractSkin;
import nl.tudelft.ti2206.group9.shop.soundtrack.AnimalsSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.DefaultSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.DuckTalesSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.MarioSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.NyanCatSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.ShakeItOffSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.AbstractSoundtrack;

/**
 * This purpose of this (util) class is loading all shop items for
 * the application.
 * This is done when starting the application and ingame when starting the shop.
 * Because of this class, a responsibility is taken away from the ShopItems.
 * Because all methods are static, it isn't or shouldn't be possible to create
 * a super class or inherit from this class (thus final).
 * @author Mitchell
 */

@SuppressWarnings("restriction")// SuppressWarnings, because
//javafx collections is needed for listing shop items.
public final class ShopItemLoader {

    // Skin items
    /** ANDY skin for the player. */
    private static AndySkin andySkin;

    /** BOY skin for the player. */
    private static BoySkin boySkin;

    /** CAPTAIN skin for the player. */
    private static CaptainSkin captainSkin;

    /** IRON MAN skin for player. */
    private static IronManSkin ironmanSkin;

    /** NOOB skin for player, this is the starting skin. */
    private static NoobSkin noobSkin;

    /** PLANK skin for the player. */
    private static PlankSkin plankSkin;

    //Soundtrack items
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

    /**
     * Private constructor.
     * This so that this class can't be instantiated,
     * as it is a util class full of static methods.
     */
    private ShopItemLoader() { }

    /**
     * Method that creates all the shop items with default values,
     * so that they can be loaded at once at the starting of the application.
     */
    public static void loadShopItems() {
        loadSkins();
        loadSoundtracks();
    }

    /**
     * Method that creates all the skins with default values,
     * so that they can be loaded at once at the starting of the application.
     */
    public static void loadSkins() {
        andySkin = new AndySkin();
        boySkin = new BoySkin();
        captainSkin = new CaptainSkin();
        ironmanSkin = new IronManSkin();
        noobSkin = new NoobSkin();
        plankSkin = new PlankSkin();
    }

    /**
     * Method that creates all soundtrack items with default values,
     * so that they can be loaded at once at the starting of the application.
     */
    public static void loadSoundtracks() {
        animalsSoundtrack = new AnimalsSoundtrack();
        ducktalesSoundtrack = new DuckTalesSoundtrack();
        defaultSoundtrack = new DefaultSoundtrack();
        marioSoundtrack = new MarioSoundtrack();
        nyancatSoundtrack = new NyanCatSoundtrack();
        shakeitoffSoundtrack = new ShakeItOffSoundtrack();
    }

    /**
     * Method that creates all the skins based on current values,
     * so that they can be loaded in the shop scene.
     * @return ObservableList<Skin> the list with all skins.
     */
    public static ObservableList<AbstractSkin> loadSkinsToList() {
        final ObservableList<AbstractSkin> list =
                FXCollections.observableArrayList();
        list.addAll(noobSkin, andySkin, boySkin,
                ironmanSkin, captainSkin, plankSkin);
        return list;
    }

    /**
     * Method that creates all soundtracks based on current values,
     * so that they can be loaded in the shop scene.
     * @return ObservableList<Soundtrack> the list with all soundtracks.
     */
    public static ObservableList<AbstractSoundtrack> loadSoundtracksToList() {
        final ObservableList<AbstractSoundtrack> list = FXCollections.
                observableArrayList();
        list.addAll(animalsSoundtrack, defaultSoundtrack,
                ducktalesSoundtrack, marioSoundtrack,
                nyancatSoundtrack, shakeitoffSoundtrack);
        return list;
    }

    /**
     * Simple getter for IronManSkin.
     * @return Skin of Iron Man.
     */
    public static IronManSkin getIronManSkin() {
        return ironmanSkin;
    }

    /**
     * Simple setter for IronManSkin.
     * @param skin a given IronManSkin to set.
     */
    public static void setIronManSkin(final IronManSkin skin) {
        ironmanSkin = skin;
    }

    /**
     * Simple getter for NoobSkin.
     * @return Skin of Noob.
     */
    public static NoobSkin getNoobSkin() {
        return noobSkin;
    }

    /**
     * Simple setter for NoobSkin.
     * @param skin a given NoobSkin to set.
     */
    public static void setNoobSkin(final NoobSkin skin) {
        noobSkin = skin;
    }

    /**
     * Simple getter for CaptainSkin.
     * @return Skin of Captain.
     */
    public static CaptainSkin getCaptainSkin() {
        return captainSkin;
    }

    /**
     * Simple setter for CaptainSkin.
     * @param skin a given CaptainSkin to set.
     */
    public static void setCaptainSkin(final CaptainSkin skin) {
        captainSkin = skin;
    }

    /**
     * Simple getter for PlankSkin.
     * @return Skin of Plank.
     */
    public static PlankSkin getPlankSkin() {
        return plankSkin;
    }

    /**
     * Simple setter for PlankSkin.
     * @param skin a given PlankSkin to set.
     */
    public static void setPlankSkin(final PlankSkin skin) {
        plankSkin = skin;
    }

    /**
     * Simple getter for BoySkin.
     * @return Skin of Boy.
     */
    public static BoySkin getBoySkin() {
        return boySkin;
    }

    /**
     * Simple setter for BoySkin.
     * @param skin a given BoySkin to set.
     */
    public static void setBoySkin(final BoySkin skin) {
        boySkin = skin;
    }

    /**
     * Simple getter for AndySkin.
     * @return Skin of Andy.
     */
    public static AndySkin getAndySkin() {
        return andySkin;
    }

    /**
     * Simple setter for AndySkin.
     * @param skin a given AndySkin to set.
     */
    public static void setAndySkin(final AndySkin skin) {
       andySkin = skin;
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
