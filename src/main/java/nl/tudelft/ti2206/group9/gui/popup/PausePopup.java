package nl.tudelft.ti2206.group9.gui.popup;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.scene.GameScene;

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

        getLeftButton().setOnMouseClicked(mouseEvent -> {
            ShaftEscape.getButtonAudioPlayer().play();
            hide();
            resumeEvent.handle(mouseEvent);
            GameScene.getSoundtrackPlayer().play();
        });

        getRightButton().setOnMouseClicked(mouseEvent -> {
            ShaftEscape.getButtonAudioPlayer().play();
            hide();
            menuEvent.handle(mouseEvent);
            GameScene.getSoundtrackPlayer().resetSpeed();
            GameScene.getSoundtrackPlayer().stop();
        });
    }

    @Override
    public Node[] createContent() {
        final Text text = new Text("Paused");
        text.setFill(Color.BLACK);
        return new Node[]{text};
    }

}
