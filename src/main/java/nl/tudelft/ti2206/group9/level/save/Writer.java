package nl.tudelft.ti2206.group9.level.save;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.shop.CurrentItems;
import nl.tudelft.ti2206.group9.shop.ShopItemLoader;
import nl.tudelft.ti2206.group9.shop.ShopItemUnlocker;
import nl.tudelft.ti2206.group9.util.Base64Writer;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

import org.json.simple.JSONObject;

/**
 * This class takes care of the writing of JSON
 * into external files. This way games can be saved.
 * @author Mathias
 */
public final class Writer {

    /**
     * Private constructor.
     */
    private Writer() { }

    /**
     * Save a game by writing it to a JSON file.
     * @param path the path of the JSON file it has to be written to
     */
    static void saveGame(final String path) {
        final String mainObject = writeToJSON();

        Base64Writer fw = null;
        try {
            fw = new Base64Writer(
                    new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(path), "UTF-8"
            )));
            fw.writeString(mainObject);
            fw.flush();
        } catch (IOException e) {
            OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
                    "Writer.saveGame(String)", e.getMessage());
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                OBSERVABLE.notify(Category.ERROR, Error.IOEXCEPTION,
                        "Writer.saveGame(String) (2)", e.getMessage());
            }
        }
    }

    /**
     * Create a JSON string from all the data
     * which can be writte to a JSON file.
     * @return the JSON string
     */         // JSONObject.put uses HashMap, that gives unchecked warnings.
    @SuppressWarnings("unchecked")
    private static String writeToJSON() {
        final JSONObject mainObject = new JSONObject();
        final JSONObject settings = new JSONObject();
        final JSONObject soundtracksettings = new JSONObject();
        soundtracksettings.put("soundtrackEnabled",
                State.isSoundtrackEnabled());
        soundtracksettings.put("soundtrackVolume",
                State.getSoundtrackVolume());
        settings.put("soundtracksettings", soundtracksettings);
        final JSONObject soundeffectsettings = new JSONObject();
        soundeffectsettings.put("soundEffectsEnabled",
                State.isSoundEffectsEnabled());
        soundeffectsettings.put("soundEffectVolume",
                State.getSoundEffectVolume());
        settings.put("soundEffectssettings", soundeffectsettings);

        mainObject.put("playername", State.getPlayerName());
        mainObject.put("coins", State.getCoins());

        final JSONObject highscore = new JSONObject();
        highscore.put("score", State.getHighscore());
        mainObject.put("highscore", highscore);

        final JSONObject shopItems = createShopItemsObject();

        mainObject.put("settings", settings);
        mainObject.put("shopItems", shopItems);

        return mainObject.toJSONString();
    }

    /**
     * Creates all objects needed for saving information about
     * shop items that have been bought.
     * @return shopItems object containing all data about bought items.
     */
    @SuppressWarnings("unchecked")
    // JSONObject.put uses HashMap, that gives unchecked warnings.
    private static JSONObject createShopItemsObject() {
        final JSONObject skins = new JSONObject();
        skins.put("andy", ShopItemUnlocker.getUnlockedShopItem("Andy"));
        skins.put("boy", ShopItemUnlocker.getUnlockedShopItem("B-man"));
        skins.put("captain", ShopItemUnlocker.getUnlockedShopItem("Captain"));
        skins.put("iron", ShopItemUnlocker.getUnlockedShopItem("Iron Man"));
        skins.put("plank", ShopItemUnlocker.getUnlockedShopItem("Plank"));
        final JSONObject soundtracks = new JSONObject();
        soundtracks.put("animals", ShopItemUnlocker.
                getUnlockedShopItem("Animals"));
        soundtracks.put("duckTales", ShopItemUnlocker.
                getUnlockedShopItem("Duck Tales"));
        soundtracks.put("mario", ShopItemUnlocker.getUnlockedShopItem("Mario"));
        soundtracks.put("nyanCat", ShopItemUnlocker.
                getUnlockedShopItem("Nyan Cat"));
        soundtracks.put("shakeItOff", ShopItemUnlocker.
                getUnlockedShopItem("Shake It Off"));

        final JSONObject equipped = new JSONObject();
        equipped.put("skin", ShopItemLoader.loadSkinsToList()
                .indexOf(CurrentItems.getSkin()));
        equipped.put("soundtrack", ShopItemLoader.loadSoundtracksToList()
                .indexOf(CurrentItems.getSoundtrack()));

        final JSONObject shopItems = new JSONObject();
        shopItems.put("skins", skins);
        shopItems.put("soundtracks", soundtracks);
        shopItems.put("equipped", equipped);
        return shopItems;
    }
}
