package nl.tudelft.ti2206.group9.gui.scene;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.skin.Skin;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.util.GameObserver;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

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
        SHOP_BACK
    }

    /**
     * Spacing between items in H/V-Boxes.
     */
    private static final int BOX_SPACING = 10;
    /**
     * Row constraint for labels.
     */
    private static final int ROW_CONSTRAINT = 5;
    /**
     * Column constraint.
     */
    private static final int COLUMN_CONSTRAINT = 4;
    /**
     * Row constraint and column span.
     */
    private static final int ROW_CONSTRAINT_SPAN = 5;
    /**
     * CurrentSkin label width.
     */
    private static final int LABEL_WIDTH = 200;
    /**
     * Shop carousel height.
     */
    private static final int CAROUSEL_HEIGHT = 435;
    /** Label for displaying current skin. */
    private static Label currentSkin;
    /** Box to contain all item vboxes. */
    private static HBox itemBox = new HBox(BOX_SPACING);
    /**
     * Label with amount of coins.
     */
    private static Label amountLabel = createLabel("",
            COLUMN_CONSTRAINT, ROW_CONSTRAINT);
    @Override
    public Node[] createContent() {
        ObservableList<Skin> items = Skin.loadSkinsToList();
        currentSkin = createLabel("CURRENT SKIN: "
                + State.getSkin().getSkinName(), 1, COLUMN_CONSTRAINT);
        currentSkin.setMinWidth(LABEL_WIDTH);
        final Button backButton = createButton("BACK", 0, ROW_CONSTRAINT);
        final Label coinsLabel = createLabel("COINS: ", 2, ROW_CONSTRAINT);
        amountLabel.setText(Integer.toString(State.getCoins()));
        setButtonFunction(backButton, BType.SHOP_BACK);
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinHeight(CAROUSEL_HEIGHT);
        itemBox.setAlignment(Pos.CENTER);

        if (itemBox.getChildren().isEmpty()) {
            for (Skin s : items) {
                itemBox.getChildren().add(createCarousel(s));
            }
        }
        scrollPane.setContent(itemBox);
        GridPane.setColumnSpan(scrollPane, ROW_CONSTRAINT_SPAN);
        GridPane.setColumnSpan(currentSkin, ROW_CONSTRAINT_SPAN);
        GridPane.setConstraints(scrollPane,
                0, 2);

        return new Node[]{scrollPane, backButton,
                coinsLabel, amountLabel, currentSkin};
    }

    /**
     * Method to fill the shop with skins.
     * @param s Skin.
     * @return VBox VBox containing an skin item.
     */
    private static VBox createCarousel(final Skin s) {
        Label price = createLabel("Price", 0, 0);
        Label name = createLabel("Name", 0, 0);
        Button buy = createButton("BUY", 0, 0);
        if (Skin.getUnlocked(s.getSkinName())) {
            buy.setText("EQUIP");
        }
        buy.setOnAction((event -> {
            if (Skin.getUnlocked(s.getSkinName())) {
                State.setSkin(s);
                currentSkin.setText("CURRENT SKIN: "
                        + State.getSkin().getSkinName());
            } else {
                if (State.getCoins() >= s.getSkinPrice()) {
                    State.setCoins(State.getCoins() - s.getSkinPrice());
                    Skin.setUnlocked(s.getSkinName(), true);
                    amountLabel.setText(Integer.toString(State.getCoins()));
                    buy.setText("EQUIP");
                }
            }
            SaveGame.saveGame();
        }));
        VBox vbox = new VBox(BOX_SPACING);
        vbox.setAlignment(Pos.CENTER);
        ImageView imgview = new ImageView(s.getSkinMaterial().getDiffuseMap());
        price.setText(Integer.toString(s.getSkinPrice()));
        name.setText(s.getSkinName());
        vbox.getChildren().addAll(imgview, name, price, buy);
        return vbox;
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    protected static void setButtonFunction(final Button button,
                                            final BType type) {
        button.setOnAction(event1 ->  {
                if (type == BType.SHOP_BACK) {
                    OBSERVABLE.notify(GameObserver.Category.MENU,
                            GameObserver.Menu.SHOP_BACK);
                    ShaftEscape.setScene(new MainMenuScene());
                }
        });
    }
}
