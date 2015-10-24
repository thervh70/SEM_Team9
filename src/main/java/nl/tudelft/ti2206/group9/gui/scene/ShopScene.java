package nl.tudelft.ti2206.group9.gui.scene;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.shop.ShopItem;
import nl.tudelft.ti2206.group9.shop.soundtrack.AbstractSoundtrack;
import nl.tudelft.ti2206.group9.shop.CurrentItems;
import nl.tudelft.ti2206.group9.shop.ShopItemLoader;
import nl.tudelft.ti2206.group9.shop.ShopItemUnlocker;
import nl.tudelft.ti2206.group9.shop.skin.AbstractSkin;
import nl.tudelft.ti2206.group9.util.GameObserver;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

/**
 * Class that creates the content for a shop screen.
 * @author Maikel on 04/10/2015.
 */
@SuppressWarnings("restriction")
public class ShopScene extends AbstractMenuScene {

    /**
     * Type of buttons that exist.
     */
    enum BType {
        /** Back button. */
        SHOP_BACK,
    }

    /** Spacing between items in H/V-Boxes. */
    private static final int BOX_SPACING = 10;
    /** Row constraint for labels. */
    private static final int ROW_CONSTRAINT = 2;
    /** Column constraint. */
    private static final int COLUMN_CONSTRAINT = 4;
    /** Row constraint and column span. */
    private static final int ROW_CONSTRAINT_SPAN = 5;
    /**
     * Label span.
     */
    private static final int LABEL_SPAN = 2;

    /** CurrentSkin label width. */
    private static final int LABEL_WIDTH = 250;
    /** Shop carousel height. */
    private static final int CAROUSEL_HEIGHT = 325;
    /**
     * tabPane height.
     */
    private static final int TABPANE_HEIGHT = 460;

    /** Label for displaying current currentSkin. */
    private Label currentSkin;
    /** Label with amount of coins. */
    private Label amountLabel;
    /**
     * Label to display current soundtrack.
     */
    private Label currentSoundtrack;


    @Override
    public Node[] createContent() {
        final TabPane tabPane = new TabPane();
        tabPane.setMinHeight(TABPANE_HEIGHT);
        currentSkin = createLabel("SKIN: "
                + CurrentItems.getSkin().getItemName(), 0, COLUMN_CONSTRAINT);
        currentSoundtrack = createLabel("SOUNDTRACK: "
                + "Radioactive", 2, COLUMN_CONSTRAINT);
        final Tab skinTab = createSkinTab();
        final Tab soundTab = createSoundTab();

        tabPane.getTabs().addAll(skinTab, soundTab);
        tabPane.setFocusTraversable(true);
        amountLabel = createLabel("", COLUMN_CONSTRAINT, ROW_CONSTRAINT);
        currentSoundtrack.setMinWidth(LABEL_WIDTH);
        currentSoundtrack.setText("SOUNDTRACK: "
                + CurrentItems.getSoundtrackName());
        final Button backButton = createButton("BACK", 0, ROW_CONSTRAINT);
        final Label coinsLabel = createLabel("COINS: ", 2, ROW_CONSTRAINT);
        amountLabel.setText(Integer.toString(State.getCoins()));
        setButtonFunction(backButton, BType.SHOP_BACK);

        GridPane.setColumnSpan(tabPane, ROW_CONSTRAINT_SPAN);
        GridPane.setColumnSpan(currentSkin, LABEL_SPAN);
        GridPane.setColumnSpan(currentSoundtrack, LABEL_SPAN);
        GridPane.setConstraints(tabPane,
                0, 0);

        return new Node[]{tabPane, backButton,
                coinsLabel, amountLabel, currentSkin, currentSoundtrack};
    }

    /**
     * Method to fill the shop with skins.
     * @return VBox VBox containing an currentSkin item.
     */
    private HBox createCarousel() {

        final ObservableList<AbstractSkin> items =
                ShopItemLoader.loadSkinsToList();

        final HBox hbox = new HBox(BOX_SPACING);

        for (AbstractSkin s : items) {

            final VBox vbox = new VBox(BOX_SPACING);
            final Label price = createLabel("Price", 0, 0);
            final Label name = createLabel("Name", 0, 0);
            final Button buy = createButton("BUY", 0, 0);

            setBuyButtonVisability(buy, s);

            buy.setOnAction(event -> {
                if (ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
                    CurrentItems.setSkin(s);
                    currentSkin.setText("SKIN: "
                            + CurrentItems.getSkin().getItemName());
                } else {
                    if (State.getCoins() >= s.getItemPrice()) {
                        State.setCoins(State.getCoins() - s.getItemPrice());
                        ShopItemUnlocker.setUnlockedShopItem(s.getItemName(), true);
                        amountLabel.setText(Integer.toString(State.getCoins()));
                        buy.setText("EQUIP");
                    }
                }
                SaveGame.saveGame();
            });

            vbox.setAlignment(Pos.CENTER);
            final ImageView imgview = new ImageView(
                    s.getSkinMaterial().getDiffuseMap());
            price.setText(Integer.toString(s.getItemPrice()));
            name.setText(s.getItemName());
            vbox.getChildren().addAll(imgview, name, price, buy);
            hbox.getChildren().addAll(vbox);
        }
        return hbox;
    }

    /**
     * Change te button visability if currentSkin is buyable/unlocked.
     * @param buy Button to set.
     * @param s Skin.
     */
    private void setBuyButtonVisability(final Button buy,
            final AbstractSkin s) {
        if (s.getItemPrice() >= State.getCoins()
                && !ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
            buy.setDisable(true);
        } else {
            buy.setDisable(false);
        }
        if (ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
            buy.setText("EQUIP");
        }
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    protected void setButtonFunction(final Button button,
            final BType type) {
        button.setOnAction(event1 -> {
            playButtonSound();
            if (type == BType.SHOP_BACK) {
                OBSERVABLE.notify(GameObserver.Category.MENU,
                        GameObserver.Menu.SHOP_BACK);
                ShaftEscape.setScene(new MainMenuScene());
            }
        });
    }

    /**
     * Creating the skin tab.
     * @return Tab.
     */
    public Tab createSkinTab() {
        final Tab tab = new Tab("Skins");
        final HBox itemBox = createCarousel();

        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinHeight(CAROUSEL_HEIGHT);

        itemBox.setAlignment(Pos.CENTER);
        scrollPane.setContent(itemBox);

        tab.setContent(scrollPane);
        tab.setClosable(false);

        return tab;
    }

    /**
     * Creating the sound tab.
     * @return Tab.
     */
    public Tab createSoundTab() {
        final Tab tab = new Tab("Soundtracks");

        final VBox itemBox = createSoundTrackCarousel();

        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinHeight(CAROUSEL_HEIGHT);
        itemBox.setAlignment(Pos.CENTER);

        scrollPane.setContent(itemBox);
        tab.setContent(scrollPane);
        tab.setClosable(false);

        return tab;
    }

    /**
     * Creating the soundTrack carousel that
     * displays all the available soundtracks.
     * @return HBox
     */
    public VBox createSoundTrackCarousel() {
        final ObservableList<AbstractSoundtrack> items =
                ShopItemLoader.loadSoundtracksToList();
        final VBox itemBox = new VBox(BOX_SPACING);

        for (AbstractSoundtrack s : items) {

            final HBox hbox = new HBox(BOX_SPACING);

            final ImageView imageView =
                    new ImageView(new Image("nl/tudelft/ti2206/"
                            + "group9/gui/scene/music_notes.png"));
            final Label nameLabel = createLabel(s.getItemName(), 0, 0);
            final Label priceLabel =
                    createLabel(Integer.toString(s.getItemPrice()), 0, 0);
            final Button buyButton = createButton("BUY", 0, 0);
            setSoundBuyButtonVisability(buyButton, s);
            buyButton.setOnAction(event -> {
                if (ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
                    CurrentItems.setSoundtrackPlayer(s);
                    currentSoundtrack.setText("SOUNDTRACK: "
                            + CurrentItems.getSoundtrackName());
                } else {
                    if (State.getCoins() >= s.getItemPrice()) {
                        State.setCoins(State.getCoins() - s.getItemPrice());
                        ShopItemUnlocker.setUnlockedShopItem(s.getItemName(), true);
                        amountLabel.setText(Integer.toString(State.getCoins()));
                        buyButton.setText("ACTIVATE");
                    }
                }
                SaveGame.saveGame();
            });
            hbox.getChildren().addAll(imageView, nameLabel, priceLabel, buyButton);
            hbox.setAlignment(Pos.CENTER);
            itemBox.getChildren().addAll(hbox);
        }
        return itemBox;
    }

    /**
     * Setting the visibility of the buy buttons.
     *
     * @param buy Button
     * @param s   Soundtrack
     */
    private void setSoundBuyButtonVisability(final Button buy,
            final AbstractSoundtrack s) {
        if (s.getItemPrice() >= State.getCoins()
                && !ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
            buy.setDisable(true);
        } else {
            buy.setDisable(false);
        }
        if (ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
            buy.setText("ACTIVATE");
        }
    }
}
