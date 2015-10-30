package nl.tudelft.ti2206.group9.shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.ti2206.group9.shop.skin.AbstractSkin;
import nl.tudelft.ti2206.group9.shop.skin.AlbertoSkin;
import nl.tudelft.ti2206.group9.shop.skin.AndySkin;
import nl.tudelft.ti2206.group9.shop.skin.ArieSkin;
import nl.tudelft.ti2206.group9.shop.skin.BoySkin;
import nl.tudelft.ti2206.group9.shop.skin.CaptainSkin;
import nl.tudelft.ti2206.group9.shop.skin.ErikSkin;
import nl.tudelft.ti2206.group9.shop.skin.GuidoSkin;
import nl.tudelft.ti2206.group9.shop.skin.IronManSkin;
import nl.tudelft.ti2206.group9.shop.skin.NoobSkin;
import nl.tudelft.ti2206.group9.shop.skin.PlankSkin;
import nl.tudelft.ti2206.group9.shop.skin.RiniSkin;
import nl.tudelft.ti2206.group9.shop.soundtrack.AbstractSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.AnimalsSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.DuckTalesSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.MarioSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.NyanCatSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.RadioactiveSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.ShakeItOffSoundtrack;

/**
 * This purpose of this (util) class is loading all shop items for
 * the application.
 * This is done when starting the application and ingame when starting the shop.
 * Because of this class, a responsibility is taken away from the ShopItems.
 * Because all methods are static, it isn't or shouldn't be possible to create
 * a super class or inherit from this class (thus final).
 *
 * @author Mitchell
 */
public final class ShopItemLoader { // NOPMD - too many fields, but all
                                    // fields are needed for loading them.

    // Skin items
    /**
     * ANDY skin for the player.
     */
    private static AndySkin andySkin;

    /**
     * BOY skin for the player.
     */
    private static BoySkin boySkin;

    /**
     * CAPTAIN skin for the player.
     */
    private static CaptainSkin captainSkin;

    /**
     * IRON MAN skin for player.
     */
    private static IronManSkin ironmanSkin;

    /**
     * NOOB skin for player, this is the starting skin.
     */
    private static NoobSkin noobSkin;

    /**
     * PLANK skin for the player.
     */
    private static PlankSkin plankSkin;


    //Skins for teacher skins.
    /**
     * Erik skin.
     */
    private static ErikSkin erikSkin;
    /**
     * Arie skin.
     */
    private static ArieSkin arieSkin;
    /**
     * Guido Skin.
     * */
    private static GuidoSkin guidoSkin;
    /**
     * RiniSkin.
     */
    private static RiniSkin riniSkin;
    /**
     * AlbertoSkin.
     */
    private static AlbertoSkin albertoSkin;

    //Soundtrack items
    /**
     * The Animals Soundtrack that can be bought.
     */
    private static AnimalsSoundtrack animalsSoundtrack;

    /**
     * The Default Soundtrack that has been bought.
     */
    private static RadioactiveSoundtrack radioactiveSoundtrack;

    /**
     * The Duck Tales Soundtrack that can be bought.
     */
    private static DuckTalesSoundtrack ducktalesSoundtrack;

    /**
     * The Mario Soundtrack that can be bought.
     */
    private static MarioSoundtrack marioSoundtrack;

    /**
     * The Nyan Cat Soundtrack that can be bought.
     */
    private static NyanCatSoundtrack nyancatSoundtrack;

    /**
     * The Shake It Off Soundtrack that can be bought.
     */
    private static ShakeItOffSoundtrack shakeitoffSoundtrack;

    /**
     * Private constructor.
     * This so that this class can't be instantiated,
     * as it is a util class full of static methods.
     */
    private ShopItemLoader() {
    }

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
        guidoSkin = new GuidoSkin();
        erikSkin = new ErikSkin();
        arieSkin = new ArieSkin();
        albertoSkin = new AlbertoSkin();
        riniSkin = new RiniSkin();
    }

    /**
     * Method that creates all soundtrack items with default values,
     * so that they can be loaded at once at the starting of the application.
     */
    public static void loadSoundtracks() {
        marioSoundtrack = new MarioSoundtrack();
        radioactiveSoundtrack = new RadioactiveSoundtrack();
        nyancatSoundtrack = new NyanCatSoundtrack();
        shakeitoffSoundtrack = new ShakeItOffSoundtrack();
        ducktalesSoundtrack = new DuckTalesSoundtrack();
        animalsSoundtrack = new AnimalsSoundtrack();
    }

    /**
     * Method that creates all the skins based on current values,
     * so that they can be loaded in the shop scene.
     *
     * @return ObservableList<Skin> the list with all skins.
     */
    public static ObservableList<AbstractSkin> loadSkinsToList() {
        final ObservableList<AbstractSkin> list =
                FXCollections.observableArrayList();
        list.addAll(noobSkin, plankSkin, boySkin,
                ironmanSkin, captainSkin, guidoSkin, riniSkin,
                erikSkin, andySkin, arieSkin, albertoSkin);
        return list;
    }

    /**
     * Method that creates all soundtracks based on current values,
     * so that they can be loaded in the shop scene.
     *
     * @return ObservableList<Soundtrack> the list with all soundtracks.
     */
    public static ObservableList<AbstractSoundtrack> loadSoundtracksToList() {
        final ObservableList<AbstractSoundtrack> list = FXCollections.
                observableArrayList();
        list.addAll(radioactiveSoundtrack, marioSoundtrack,
                nyancatSoundtrack, shakeitoffSoundtrack, ducktalesSoundtrack,
                animalsSoundtrack);
        return list;
    }

    /**
     * Simple getter for NoobSkin for the hard reset in State.
     * This is the only getter that is needed, because of the reason above.
     * More getters and setters aren't needed as the shop items don't
     * need to be changed on runtime anymore and the loading of skins is done
     * by the loadShopItemToList() method.
     *
     * @return Skin of Noob.
     */
    public static NoobSkin getNoobSkin() {
        return noobSkin;
    }

    /**
     * Gets the Andy skin for testing purposes.
     * (E2E test)
     *
     * @return Skin of Andy.
     */
    public static AndySkin getAndySkin() {
        return andySkin;
    }

}
