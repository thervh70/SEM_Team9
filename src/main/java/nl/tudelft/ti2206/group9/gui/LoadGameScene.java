package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver;

/**
 * Created by Maikel on 24/09/2015.
 */
public class LoadGameScene extends MenuScene {

        /**
         * Type of buttons that exist.
         */
        enum BType {
            /** Back button. */
            LOAD_BACK,
            /** Button to load a game. */
            LOAD_START
        }

        /**
         * Creates the buttons.
         * @return array of Nodes to be added to the Scene.
         */
        @Override
        public Node[] createContent() {

            final Button backButton = createButton("Back", 0, 24);
            final Button loadButton = createButton("Load", 4, 24);

            setButtonFunction(backButton, BType.LOAD_BACK);
            setButtonFunction(loadButton, BType.LOAD_START);
            return new Node[]{backButton, loadButton};
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
                    if (type == BType.LOAD_BACK) {
                        GameObservable.notify(GameObserver.Category.MENU, GameObserver.Menu.LOAD_BACK);
                        ShaftEscape.setScene(new MainMenuScene());
                    }
                        }
            });
        }

}


