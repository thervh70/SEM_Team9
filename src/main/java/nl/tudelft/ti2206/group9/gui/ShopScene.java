package nl.tudelft.ti2206.group9.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Class that creates the content for a shop screen.
 *
 * Created by Maikel on 04/10/2015.
 */
public class ShopScene extends AbstractMenuScene {

    @Override
    public Node[] createContent() {
        final Button backButton = createButton("BACK", 0, 24);
        final Label totalCoinsLabel = createLabel("AMOUNT OF COINS:", 2, 18);



        return new Node[]{backButton, totalCoinsLabel};
    }


}
