package nl.tudelft.ti2206.group9.gui.popup;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.scene.HighscoreScene;
import nl.tudelft.ti2206.group9.gui.scene.MainMenuScene;
import nl.tudelft.ti2206.group9.level.State;

/**
 * DeathPopup, which is to be shown when the Player dies.
 * @author Maarten, Robin
 */
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
            playButtonSound();
            hide();
            retryEvent.handle(mouseEvent);
        });

        getRightButton().setOnMouseClicked(mouseEvent -> {
            playButtonSound();
            hide();
            menuEvent.handle(mouseEvent);
        });
    }

    @Override
    public Node[] createContent() {
        final int headerSize = 18;
        final int textSize = 14;
        final Text header = new Text("Game Ended");
        header.setFont(Style.getFont(headerSize));
        header.setTextAlignment(TextAlignment.CENTER);

        final String s = "Final Score: " + (int) State.getScore()
                + "\n\nHigh Score: " + State.getHighscore()
                + "\n\nTotal amount of coins: " + State.getCoins();
        final Text scoreText = new Text(s);
        scoreText.setFont(Style.getFont(textSize));
        scoreText.setTextAlignment(TextAlignment.CENTER);

        final Button toHighscores = createButton("Highscores");
        toHighscores.setOnMouseClicked(e -> {
            playButtonSound();
            hide();
            ShaftEscape.setScene(new MainMenuScene());
            ShaftEscape.setScene(new HighscoreScene());
        });

        return new Node[]{header, scoreText, toHighscores};
    }

}
