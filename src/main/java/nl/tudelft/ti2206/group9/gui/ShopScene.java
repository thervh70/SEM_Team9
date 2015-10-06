package nl.tudelft.ti2206.group9.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver;
import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

/**
 * Class that creates the content for a shop screen.
 *
 * Created by Maikel on 04/10/2015.
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



    /** Creating a list. */
    private static ObservableList<String> items =
            FXCollections.observableArrayList();
    /** Creating the listview used to display the list. */
    private static ListView<String> itemList = createList(2, LIST_ROW);

    @Override
    public Node[] createContent() {
        itemList.setItems(items);
        items.addAll("Iron man skin - $99999,-",
                "Andy Zaidman skin - $99999,-", "Pokemon Tune - $999999,-");



        final Button backButton = createButton("BACK", 0, 24);
        final Label coinsLabel = createLabel("COINS:", 2, 24);
        final Label amountLabel = createLabel(Integer
                .toString(State.getCoins()), 4, 24);

        setButtonFunction(backButton, BType.SHOP_BACK);
        return new Node[]{backButton, coinsLabel, amountLabel, itemList};
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    protected static void setButtonFunction(final Button button,
                                            final BType type) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                if (type == BType.SHOP_BACK) {
                    OBSERVABLE.notify(GameObserver.Category.MENU,
                            GameObserver.Menu.SHOP_BACK);
                    ShaftEscape.setScene(new MainMenuScene());
                }
            }
        });
    }
    /**
     * Return the list with shop items.
     * @return List to return.
     */
    public static ObservableList<String> getItems() {
        return items;
    }

}
