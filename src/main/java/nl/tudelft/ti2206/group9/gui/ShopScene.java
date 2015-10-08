package nl.tudelft.ti2206.group9.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.skins.AbstractSkin;
import nl.tudelft.ti2206.group9.level.State;
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

    /** Creating a list. */
    private static ObservableList<AbstractSkin> items =
            FXCollections.observableArrayList();
    /** Creating the listview used to display the list. */
    private static TableView<AbstractSkin> itemTable =
            createSkinTable(1, LIST_ROW);

    @Override
    public Node[] createContent() {
        setUpTable();

        final Button backButton = createButton("BACK", 0, 24);
        final Label coinsLabel = createLabel("COINS:", 2, 24);
        final Label amountLabel = createLabel(Integer
                .toString(State.getCoins()), 4, 24);

        setButtonFunction(backButton, BType.SHOP_BACK);
        return new Node[]{backButton, coinsLabel, amountLabel, itemTable};
    }

    /**
     * Method to fill the table with skins.
     */
    private static void setUpTable() {
        items.clear();
        itemTable.getColumns().clear();
        itemTable.setItems(items);
        items.addAll(Style.getAndy(), Style.getBoy(),
                Style.getCaptain(), Style.getIronMan(),
                Style.getNoob(), Style.getPlank());

        TableColumn<AbstractSkin, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("skinName"));
        name.setResizable(false);

        TableColumn<AbstractSkin, Integer> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("skinPrice"));
        name.setResizable(false);

        itemTable.getColumns().add(name);
        itemTable.getColumns().add(price);

        itemTable.setRowFactory(e -> {
            TableRow<AbstractSkin> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    State.setSkin(row.getItem());
                }
            });
            return row;
        });
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
}
