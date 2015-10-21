package nl.tudelft.ti2206.group9.shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.ti2206.group9.shop.skin.AndySkin;
import nl.tudelft.ti2206.group9.shop.skin.BoySkin;
import nl.tudelft.ti2206.group9.shop.skin.CaptainSkin;
import nl.tudelft.ti2206.group9.shop.skin.IronManSkin;
import nl.tudelft.ti2206.group9.shop.skin.NoobSkin;
import nl.tudelft.ti2206.group9.shop.skin.PlankSkin;
import nl.tudelft.ti2206.group9.shop.skin.Skin;
import nl.tudelft.ti2206.group9.shop.soundtrack.AnimalsSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.DefaultSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.DuckTalesSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.MarioSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.NyanCatSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.ShakeItOffSoundtrack;
import nl.tudelft.ti2206.group9.shop.soundtrack.Soundtrack;

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
        Skin.setIronManSkin(new IronManSkin());
        Skin.setCaptainSkin(new CaptainSkin());
        Skin.setAndySkin(new AndySkin());
        Skin.setNoobSkin(new NoobSkin());
        Skin.setBoySkin(new BoySkin());
        Skin.setPlankSkin(new PlankSkin());
    }

    /**
     * Method that creates all soundtrack items with default values,
     * so that they can be loaded at once at the starting of the application.
     */
    public static void loadSoundtracks() {
        Soundtrack.setAnimalsSoundtrack(new AnimalsSoundtrack());
        Soundtrack.setDuckTalesSoundtrack(new DuckTalesSoundtrack());
        Soundtrack.setDefaultSoundtrack(new DefaultSoundtrack());
        Soundtrack.setMarioSoundtrack(new MarioSoundtrack());
        Soundtrack.setNyanCatSoundtrack(new NyanCatSoundtrack());
        Soundtrack.setShakeItOffSoundtrack(new ShakeItOffSoundtrack());
    }

    /**
     * Method that creates all the skins based on current values,
     * so that they can be loaded in the shop scene.
     * @return ObservableList<Skin> the list with all skins.
     */
    public static ObservableList<Skin> loadSkinsToList() {
        final ObservableList<Skin> list = FXCollections.observableArrayList();
        list.addAll(Skin.getNoobSkin(), Skin.getAndySkin(),
                Skin.getBoySkin(), Skin.getIronManSkin(),
                Skin.getCaptainSkin(), Skin.getPlankSkin());
        return list;
    }

    /**
     * Method that creates all soundtracks based on current values,
     * so that they can be loaded in the shop scene.
     * @return ObservableList<Soundtrack> the list with all soundtracks.
     */
    public static ObservableList<Soundtrack> loadSoundtracksToList() {
        final ObservableList<Soundtrack> list = FXCollections.
                observableArrayList();
        list.addAll(Soundtrack.getAnimalsSoundtrack(),
                Soundtrack.getDuckTalesSoundtrack(),
                Soundtrack.getDefaultSoundtrack(),
                Soundtrack.getMarioSoundtrack(),
                Soundtrack.getNyanCatSoundtrack(),
                Soundtrack.getShakeItOffSoundtrack());
        return list;
    }

}
