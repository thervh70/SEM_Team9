package nl.tudelft.ti2206.group9.gui.popup;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import nl.tudelft.ti2206.group9.gui.scene.SplashScene;
import nl.tudelft.ti2206.group9.level.State;

/**
 * DeathPopup, which is to be shown when the Player dies.
 * @author Maarten, Robin
 */
@SuppressWarnings("restriction")
public class DeathPopup extends AbstractInfoPopup {

	/**
	 * Creates a new DeathPopup.
	 * @param retryEvent event that should happen when "Try Again" button is
	 * clicked.
	 * @param menuEvent event that should happen when "Main Menu" button is
	 * clicked.
	 */
	public DeathPopup(final EventHandler<MouseEvent> retryEvent,
    		final EventHandler<MouseEvent> menuEvent) {
		super(new Button("Try Again"), new Button("Return to Main Menu"));

        getLeftButton().setOnMouseClicked(mouseEvent -> {
			SplashScene.getButtonAudioPlayer().play(false);
            hide();
            retryEvent.handle(mouseEvent);
        });

        getRightButton().setOnMouseClicked(mouseEvent -> {
			SplashScene.getButtonAudioPlayer().play(false);
            hide();
            menuEvent.handle(mouseEvent);
        });
    }

	@Override
	public Node[] createContent() {
        final Text text = new Text("Game Ended");

        final String s = "Final Score: " + (int) State.getScore();
        final Text finalScore = new Text(s);

        final String h = "High Score: " + State.getHighscore();
        final Text finalHigh = new Text(h);

        final String c = "Total amount of coins: " + State.getCoins();
        final Text finalCoins = new Text(c);

		return new Node[]{text, finalScore, finalHigh, finalCoins};
	}

}
