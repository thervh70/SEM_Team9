package nl.tudelft.ti2206.group9.gui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * PausePopup, to be shown when the Game is paused.
 * @author Maarten, Robin
 */
@SuppressWarnings("restriction")
public class PausePopup extends AbstractInfoPopup {

	/**
	 * Creates a new PausePopup.
	 * @param resumeEvent event that should happen when "Resume" button is
	 * clicked.
	 * @param menuEvent event that should happen when "Main Menu" button is
	 * clicked.
	 */
	public PausePopup(final EventHandler<MouseEvent> resumeEvent,
    		final EventHandler<MouseEvent> menuEvent) {
		super(new Button("Resume"), new Button("Return to Main Menu"));

        getLeftButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(final MouseEvent y) {
                hide();
                resumeEvent.handle(y);
            }
        });

        getRightButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(final MouseEvent n) {
                hide();
                menuEvent.handle(n);
            }
        });
    }

	@Override
	public Node[] createContent() {
        final Text text = new Text("Paused");
        text.setFill(Color.BLACK);
		return new Node[]{text};
	}

}
