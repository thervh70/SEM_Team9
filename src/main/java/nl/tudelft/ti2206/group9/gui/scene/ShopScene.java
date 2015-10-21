package nl.tudelft.ti2206.group9.gui.scene;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;
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
import nl.tudelft.ti2206.group9.shop.skin.Skin;
import nl.tudelft.ti2206.group9.util.GameObserver;

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
    private static final int ROW_CONSTRAINT = 3;
    /** Column constraint. */
    private static final int COLUMN_CONSTRAINT = 4;
    /** Row constraint and column span. */
    private static final int ROW_CONSTRAINT_SPAN = 5;

    /** CurrentSkin label width. */
    private static final int LABEL_WIDTH = 200;
    /** Shop carousel height. */
    private static final int CAROUSEL_HEIGHT = 325;

    /** Label for displaying current skin. */
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
        tabPane.setMinHeight(460);

        tabPane.getTabs().addAll(createSkinTab(), createSoundTab());

        amountLabel = createLabel("", COLUMN_CONSTRAINT, ROW_CONSTRAINT);

        currentSkin = createLabel("CURRENT SKIN: "
                + State.getSkin().getItemName(), 1, COLUMN_CONSTRAINT);
        currentSkin.setMinWidth(LABEL_WIDTH);
        final Button backButton = createButton("BACK", 0, ROW_CONSTRAINT);
        final Label coinsLabel = createLabel("COINS: ", 2, ROW_CONSTRAINT);
        amountLabel.setText(Integer.toString(State.getCoins()));
        setButtonFunction(backButton, BType.SHOP_BACK);


        GridPane.setColumnSpan(tabPane, ROW_CONSTRAINT_SPAN);
        GridPane.setColumnSpan(currentSkin, ROW_CONSTRAINT_SPAN);
        GridPane.setConstraints(tabPane,
                0, 0);

        return new Node[]{tabPane, backButton,
                coinsLabel, amountLabel, currentSkin};
    }

    /**
     * Method to fill the shop with skins.
     * @param s Skin.
     * @return VBox VBox containing an skin item.
     */
    private VBox createCarousel(final Skin s) {
        final Label price = createLabel("Price", 0, 0);
        final Label name = createLabel("Name", 0, 0);
        final Button buy = createButton("BUY", 0, 0);
        setBuyButtonVisability(buy, s);
        buy.setOnAction(event -> {
            if (Skin.getUnlockedSkin(s.getItemName())) {
                State.setSkin(s);
                currentSkin.setText("CURRENT SKIN: "
                        + State.getSkin().getItemName());
            } else {
                if (State.getCoins() >= s.getItemPrice()) {
                    State.setCoins(State.getCoins() - s.getItemPrice());
                    Skin.setUnlockedSkin(s.getItemName(), true);
                    amountLabel.setText(Integer.toString(State.getCoins()));
                    buy.setText("EQUIP");
                }
            }
            SaveGame.saveGame();
        });
        final VBox vbox = new VBox(BOX_SPACING);
        vbox.setAlignment(Pos.CENTER);
        final ImageView imgview = new ImageView(
                s.getSkinMaterial().getDiffuseMap());
        price.setText(Integer.toString(s.getItemPrice()));
        name.setText(s.getItemName());
        vbox.getChildren().addAll(imgview, name, price, buy);
        return vbox;
    }

    /**
     * Change te button visability if skin is buyable/unlocked.
     * @param buy Button to set.
     * @param s Skin.
     */
    private void setBuyButtonVisability(final Button buy, final Skin s) {
        if (s.getItemPrice() >= State.getCoins()
                && !Skin.getUnlockedSkin(s.getItemName())) {
            buy.setDisable(true);
        } else {
            buy.setDisable(false);
        }
        if (Skin.getUnlockedSkin(s.getItemName())) {
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
        button.setOnAction(event1 ->  {
            playButtonSound();
            if (type == BType.SHOP_BACK) {
                OBSERVABLE.notify(GameObserver.Category.MENU,
                        GameObserver.Menu.SHOP_BACK);
                ShaftEscape.setScene(new MainMenuScene());
            }
        });
    }

    public Tab createSkinTab() {
        Tab tab = new Tab("Skins");

        final HBox itemBox = new HBox(BOX_SPACING);
        final ObservableList<Skin> items = Skin.loadSkinsToList();
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinHeight(CAROUSEL_HEIGHT);
        itemBox.setAlignment(Pos.CENTER);
        itemBox.getChildren().clear();
        for (final Skin s : items) {
            itemBox.getChildren().add(createCarousel(s));
        }
        scrollPane.setContent(itemBox);

        tab.setContent(scrollPane);


        return tab;
    }

    public Tab createSoundTab() {
        Tab tab = new Tab("Soundtracks");

        final VBox itemBox = new VBox(BOX_SPACING);
//        final ObservableList<SoundTrack> items = Soundtrack.load
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinHeight(CAROUSEL_HEIGHT);
        itemBox.setAlignment(Pos.CENTER);
        itemBox.getChildren().clear();

//        for (final SoundTrack s : items) {
//            itemBox.getChildren().addAll(createSoundTrackCarousel(s));
//        }

        return tab;
    }

//    public HBox createSoundTrackCarousel(SoundTrack s) {
//        HBox hbox = new HBox(BOX_SPACING);
//        ImageView imageView = new ImageView(new Image("IMAGE OF SOUNDTRACK :D"));
//        Label nameLabel = createLabel(SoundTrack.getName(), 0, 0);
//        Label priceLabel = createLabel(SoundTrack.getPrice(), 0, 0);
//        Button buyButton = createButton("BUY", 0, 0);
//
//        buyButton.setOnAction(event -> {
//            if (SoundTrack.getUnlocked(s.getSoundTrackName())) {
////                TODO: Set soundtrack here.
//                currentSoundtrack.setText("CURRENT SOUNDTRACK: "
//                        + "GET CURRENT SOUNDTRACK NAME");
//            } else {
//                if (State.getCoins() >= s.getPrice()) {
//                    State.setCoins(State.getCoins() - s.getPrice());
//                    SoundTrack.setUnlocked(s.getName(), true);
//                    amountLabel.setText(Integer.toString(State.getCoins()));
//                    buyButton.setText("ACTIVATE");
//                }
//            }
//            SaveGame.saveGame();
//        });
//
//        hbox.getChildren().addAll(imageView, nameLabel, priceLabel, buyButton);
//
//        return hbox;
//    }

}
