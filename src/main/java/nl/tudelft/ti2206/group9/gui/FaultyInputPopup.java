package nl.tudelft.ti2206.group9.gui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * @author Mathias
 */
public class FaultyInputPopup extends AbstractWarningPopup {

    /**
     * Default constructor.
     * Calls super and links the button to an Event.
     * @param okEvent the Event to be executed
     */
    public FaultyInputPopup(final EventHandler<MouseEvent> okEvent) {
        super(new Button("OK"));

        getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(final MouseEvent y) {
                hide();
                okEvent.handle(y);
            }
        });
    }

    /**
     * Creates the content of this Popup.
     * @return Node contains the content of this Popup
     */
    @Override
    public Node[] createContent() {
        final String message = "The given playername is not valid!";
        final Text messageText = new Text(message);
        return new Node[]{messageText};
    }
}