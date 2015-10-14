package nl.tudelft.ti2206.group9.gui.scene;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

/**
 * @author Mathias
 */
public class AccountScene extends AbstractMenuScene{

    /** Row in Grid of list. */
    private static final int LIST_ROW = 16;

    /** Creating the listview used to display the list. */
    private static ListView<String> list = createList(2, LIST_ROW);

    public enum BType {
        ACC_LOAD,
        ACC_NEW,
        ACC_BACK
    }

    @Override
    public Node[] createContent() {
        SaveGame.readPlayerNames();
        list.setItems(State.getSaveGames());
        list.getSelectionModel().selectFirst();
        /** Create Buttons. */
        final Button loadButton = createButton("LOAD", 2, 24);
        final Button newButton = createButton("NEW", 0, 24);
        final Button backButton = createButton("BACK", 4, 24);
        /** Set Button Functions .*/
        setButtonFunction(loadButton, BType.ACC_LOAD);
        setButtonFunction(newButton, BType.ACC_NEW);
        setButtonFunction(backButton, BType.ACC_BACK);

        String textString = "U bent: ";
        if (State.getPlayerName() == null) {
            textString += "Guest";
        } else {
            textString += State.getPlayerName();
        }
        final Label textLabel = createLabel(textString, 2, 16);
        /** Set tooltips. */
        loadButton.setTooltip(new Tooltip("Load an existing game"));
        newButton.setTooltip(new Tooltip("Create a new game"));
        backButton.setTooltip(new Tooltip("Back to Main Menu"));
        list.setTooltip(new Tooltip("Select player"));
        return new Node[]{loadButton, newButton, backButton, textLabel};
    }

    protected static void setButtonFunction(final Button button,
                                            final BType type) {
        button.setOnAction(event -> {
            if (type == BType.ACC_LOAD) {
                OBSERVABLE.notify(Category.MENU, Menu.ACC_LOAD);
            } else if (type == BType.ACC_NEW) {
                OBSERVABLE.notify(Category.MENU, Menu.ACC_NEW);
            } else {
                OBSERVABLE.notify(Category.MENU, Menu.ACC_BACK);
                ShaftEscape.setScene(new MainMenuScene());
            }
        });
    }
}
