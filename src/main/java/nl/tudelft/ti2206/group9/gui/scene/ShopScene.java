package nl.tudelft.ti2206.group9.gui.scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.skin.Skin;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.entity.Player;
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

    /** Row the list is put on. */
    private static final int LIST_ROW = 16;

    /** Label for displaying current skin. */
    private static Label currentSkin;

    /** Box to contain all item vboxes. */
    private static HBox itemBox = new HBox(10);

    /** Label with amount of coins. */
    private static Label amountLabel = createLabel("", 4, 6);


    @Override
    public Node[] createContent() {
        ObservableList<Skin> items = Style.loadSkinsToList();

        currentSkin = createLabel("CURRENT SKIN: " + State.getSkin().getSkinName(), 1, 5);
        currentSkin.setMinWidth(200);
        final Button backButton = createButton("BACK", 0, 6);
        final Label coinsLabel = createLabel("COINS: ", 2, 6);

        amountLabel.setText(Integer.toString(State.getCoins()));
        setButtonFunction(backButton, BType.SHOP_BACK);

        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-focus-color: transparent");

        scrollPane.setFitToHeight(true);
        itemBox.setAlignment(Pos.CENTER);

        if (itemBox.getChildren().isEmpty()) {
            for (Skin s : items) {
                itemBox.getChildren().add(createCarousel(s));
            }
        }
        scrollPane.setContent(itemBox);

        GridPane.setColumnSpan(scrollPane, 5);
        GridPane.setColumnSpan(currentSkin, 5);
        GridPane.setConstraints(scrollPane, 0, 4);

        return new Node[]{scrollPane, backButton, coinsLabel, amountLabel, currentSkin};
    }

    /**
     * Method to fill the shop with skins.
     * @param s Skin.
     */
    private static VBox createCarousel(final Skin s) {
        Label price = createLabel("Price", 0, 0);
        Label name = createLabel("Name", 0, 0);
        Button buy = createButton("BUY", 0, 0);

        if (s.getSkinUnlocked()) {
            buy.setText("EQUIP");
        }

        buy.setOnAction((event -> {
            if (s.getSkinUnlocked()) {
                State.setSkin(s);
                currentSkin.setText("CURRENT SKIN: " + State.getSkin().getSkinName());
            } else {
                if (State.getCoins() >= s.getSkinPrice() ) {
                    State.setCoins(State.getCoins() - s.getSkinPrice());
                    s.buySkin();
                    amountLabel.setText(Integer.toString(State.getCoins()));
                    buy.setText("EQUIP");
                }
            }
            SaveGame.saveGame();
        }));

        Image image;
        ImageView imgview = new ImageView();

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

            price.setText(Integer.toString(s.getSkinPrice()));
            name.setText(s.getSkinName());
            image = s.getSkinMaterial().getDiffuseMap();
            imgview.setImage(image);

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
