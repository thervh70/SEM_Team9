package nl.tudelft.ti2206.group9.gui.popup;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import nl.tudelft.ti2206.group9.gui.Style;

import java.util.Arrays;

/**
 * @author Mathias
 */
public class WarningPopup extends Popup {

    /** Width of the PopUpMenu. */
    public static final double WIDTH = 270;
    /** Height of the PopUpMenu. */
    public static final double HEIGHT = 100;
    /** Size of the HBox. */
    protected static final double HBOX_SPACING = 10;
    /** Size of the VBox. */
    protected static final double VBOX_SPACING = 30;

    /** The left button in the Popup. */
    private final Button button;
    /** The message this Popup displays. */
    private final String message;

    /**
     * Constructs a new Popup with default {@link #WIDTH} and {@link #HEIGHT}.
     * @param okEvent Eventhandler that handles the MouseEvent
     *                'click OK button'.
     * @param mssg String that contains the message the Popup displays.
     */
    public WarningPopup(final EventHandler<MouseEvent> okEvent,
                        final String mssg) {
        super();
        button = new Button("OK");
        message = mssg;

        setStyle();

        final Rectangle rect = new Rectangle(WIDTH, HEIGHT, Color.WHITESMOKE);
        rect.setStroke(Color.BLACK);

        final HBox hbox = new HBox(HBOX_SPACING, button);
        hbox.setAlignment(Pos.CENTER);

        final Node[] content = createContent();
        final Node[] content2 = Arrays.copyOf(content, content.length + 1);
        content2[content.length] = hbox;
        final VBox vbox = new VBox(VBOX_SPACING, content2);
        vbox.setAlignment(Pos.CENTER);

        getContent().addAll(rect, vbox);

        getButton().setOnMouseClicked(y -> {
            hide();
            okEvent.handle(y);
        });
    }

    /**
     * This method returns the content that should be added to the VBox of the
     * Popup. Each next node will be placed below the previous.
     * @return A list of Nodes to be added to the VBox of the Popup.
     */
    public final Node[] createContent() {
        final int textSize = 13;
        final Text messageText = new Text(message);
        messageText.setFont(Style.getFont(textSize));
        messageText.setTextAlignment(TextAlignment.CENTER);
        return new Node[]{messageText};
    }

    /**
     * @return the main Button
     */
    public Button getButton() {
        return button;
    }

    /**
     * Set the style properties of the warningPopups.
     */
    private void setStyle() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setHideOnEscape(false);
        Style.setPopupButtonStyle(button);
    }
}
