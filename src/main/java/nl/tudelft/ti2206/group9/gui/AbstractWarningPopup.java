package nl.tudelft.ti2206.group9.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;

import java.util.Arrays;

/**
 * @author Mathias
 */
public abstract class AbstractWarningPopup extends Popup {

    /** Width of the PopUpMenu. */
    public static final double WIDTH = 260;
    /** Height of the PopUpMenu. */
    public static final double HEIGHT = 90;
    /** Size of the HBox. */
    protected static final double HBOX_SPACING = 10;
    /** Size of the VBox. */
    protected static final double VBOX_SPACING = 50;

    /** The left button in the Popup. */
    private final Button button;

    /**
     * Constructs a new Popup with default {@link #WIDTH} and {@link #HEIGHT}.
     * @param bttn Button that should be shown in the bottom-center.
     */
    public AbstractWarningPopup(final Button bttn) {
        super();
        button = bttn;

        setWidth(WIDTH);
        setHeight(HEIGHT);
        setHideOnEscape(false);

        final Rectangle rect = new Rectangle(WIDTH, HEIGHT, Color.WHITESMOKE);
        rect.setStroke(Color.BLACK);

        Style.setPopupButtonStyle(button);

        final HBox hbox = new HBox(HBOX_SPACING, button);
        hbox.setAlignment(Pos.CENTER);

        final Node[] content = createContent();
        final Node[] content2 = Arrays.copyOf(content, content.length + 1);
        content2[content.length] = hbox;
        final VBox vbox = new VBox(VBOX_SPACING, content2);
        vbox.setAlignment(Pos.CENTER);

        getContent().addAll(rect, vbox);
    }

    /**
     * This method returns the content that should be added to the VBox of the
     * Popup. Each next node will be placed below the previous.
     * @return A list of Nodes to be added to the VBox of the Popup.
     */
    public abstract Node[] createContent();

    /**
     * @return the main Button
     */
    public Button getButton() {
        return button;
    }
}
