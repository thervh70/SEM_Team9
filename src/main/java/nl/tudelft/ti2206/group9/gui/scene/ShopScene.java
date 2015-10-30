package nl.tudelft.ti2206.group9.gui.scene;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.shop.CurrentItems;
import nl.tudelft.ti2206.group9.shop.ShopItemLoader;
import nl.tudelft.ti2206.group9.shop.ShopItemUnlocker;
import nl.tudelft.ti2206.group9.shop.skin.AbstractSkin;
import nl.tudelft.ti2206.group9.shop.soundtrack.AbstractSoundtrack;
import nl.tudelft.ti2206.group9.util.GameObserver;

import java.util.Timer;
import java.util.TimerTask;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

/**
 * Class that creates the content for a shop screen.
 * @author Maikel on 04/10/2015.
 */
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
    /** Tabpane for the shop. */
    private TabPane tabPane;
    /** Hover scale. */
    private static final double BUTTON_HOVER_SCALE = 1.2;
    /** Preview button size. */
    private static final int PREVIEW_SIZE = 35;
    /** Back Button. */
    private static final Button BACK_BUTTON
            = createButton("BACK", 0, ROW_CONSTRAINT);





    @Override
    public Node[] createContent() {
        tabPane = new TabPane();
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
        final Label coinsLabel = createLabel("COINS: ", 2, ROW_CONSTRAINT);
        amountLabel.setText(Integer.toString(State.getCoins()));
        setButtonFunction(BACK_BUTTON, BType.SHOP_BACK);

        GridPane.setColumnSpan(tabPane, ROW_CONSTRAINT_SPAN);
        GridPane.setColumnSpan(currentSkin, LABEL_SPAN);
        GridPane.setColumnSpan(currentSoundtrack, LABEL_SPAN);
        GridPane.setConstraints(tabPane,
                0, 0);

        return new Node[]{tabPane, BACK_BUTTON,
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
        for (final AbstractSkin s : items) {
            final VBox vbox = new VBox(BOX_SPACING);
            final Label price = createLabel("Price", 0, 0);
            final Label name = createLabel("Name", 0, 0);
            final Button buy = createButton("BUY", 0, 0);
            setBuyButtonVisability(buy, s);
            setSkinBuyButton(buy, s);
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
     * Set buy button functionality.
     *@param buy Button to set.
     *@param s Skin.
     */
    private void setSkinBuyButton(final Button buy, final AbstractSkin s) {
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
                    SaveGame.saveGame();
                    tabPane.getTabs().clear();
                    tabPane.getTabs().addAll(createSkinTab(), createSoundTab());
                    tabPane.getSelectionModel().select(0);
                }
            }
        });
    }


    /**
     * Change te button visability if currentSkin is buyable/unlocked.
     * @param buy Button to set.
     * @param s Skin.
     */
    private void setBuyButtonVisability(final Button buy,
            final AbstractSkin s) {
        if (s.getItemPrice() > State.getCoins()
                && !ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
            buy.setDisable(true);
            buy.setTooltip(new Tooltip("Not enough coins :("));
        } else {
            buy.setDisable(false);
            buy.setTooltip(new Tooltip("Buy skin!"));
        }
        if (ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
            buy.setText("EQUIP");
            buy.setTooltip(new Tooltip("Equip skin!"));
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
        for (final AbstractSoundtrack s : items) {
            final HBox hbox = new HBox(BOX_SPACING);
            final Button previewButton = new Button();
            final Image playImg = new Image("nl/tudelft/ti2206/"
                    + "group9/gui/scene/music_notes.png");
            final BackgroundImage backImg = new BackgroundImage(playImg,
                    null, null, null, null);
            final Background back = new Background(backImg);
            previewButton.setBackground(back);
            previewButton.setMinWidth(PREVIEW_SIZE);
            previewButton.setMinHeight(PREVIEW_SIZE);
            setPreviewButtonHover(previewButton);
            setPreviewFunction(previewButton, s);
            final Label nameLabel = createLabel(s.getItemName(), 0, 0);
            final Label priceLabel =
                    createLabel(Integer.toString(s.getItemPrice()), 0, 0);
            final Button buyButton = createButton("BUY", 0, 0);
            setSoundBuyButtonVisability(buyButton, s);
            setSoundBuyButtonFunction(buyButton, s);
            hbox.getChildren().addAll(previewButton,
                    nameLabel, priceLabel, buyButton);
            hbox.setAlignment(Pos.CENTER);
            itemBox.getChildren().addAll(hbox);
        }
        return itemBox;
    }

    /**
     * Set the function of the buy button in the soundtrack.
     * @param buyButton Button to set.
     * @param s Soundtrack.
     */
    private void setSoundBuyButtonFunction(final Button buyButton,
                                           final AbstractSoundtrack s) {
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
                    tabPane.getTabs().clear();
                    tabPane.getTabs().addAll(createSkinTab(), createSoundTab());
                    tabPane.getSelectionModel().select(1);
                }
            }
            SaveGame.saveGame();
        });
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
            buy.setTooltip(new Tooltip("Not enough coins :("));
        } else {
            buy.setDisable(false);
            buy.setTooltip(new Tooltip("Buy soundtrack!"));
        }
        if (ShopItemUnlocker.getUnlockedShopItem(s.getItemName())) {
            buy.setTooltip(new Tooltip("Activate soundtrack!"));
            buy.setText("ACTIVATE");
        }
    }
    /**
     * Set hover animation on previewbutton.
     * @param b Button to be set.
     */
    private void setPreviewButtonHover(final Button b) {
        b.setTooltip(new Tooltip("Play soundtrack"));
        b.setOnMouseEntered(e -> {
            b.setScaleX(BUTTON_HOVER_SCALE);
            b.setScaleY(BUTTON_HOVER_SCALE);
        });

        /** Action to be taken on MouseExited Event. */
        b.setOnMouseExited(e -> {
            b.setScaleX(1);
            b.setScaleY(1);
        });
    }

    /**
     * Override background, the Shop background shows "Shop".
     */
    @Override
    public String getBackgroundPath() {
        return "shopBackground.png";
    }

    /**
     * Set function of previewButton.
     * @param b Button to be set.
     * @param s Soundtrack.
     */
    private void setPreviewFunction(final Button b,
                                    final AbstractSoundtrack s) {

        final int playTime = 7000;
        final boolean soundEnabled = State.isSoundtrackEnabled();

        b.setOnAction(event -> {
            final Timer timer = new Timer();
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        s.getSoundtrackPlayer().stop();
                        b.setDisable(false);
                        State.setSoundtrackEnabled(soundEnabled);
                        MainMenuScene.getAudioPlayer().play();
                        BACK_BUTTON.setDisable(false);
                    });
                }
            };
            BACK_BUTTON.setDisable(true);
            b.setDisable(true);
            State.setSoundtrackEnabled(true);
            MainMenuScene.getAudioPlayer().pause();
            s.getSoundtrackPlayer().setVolume(State.getSoundtrackVolume());
            s.getSoundtrackPlayer().play();
            timer.schedule(task, playTime);
        });

    }
}
