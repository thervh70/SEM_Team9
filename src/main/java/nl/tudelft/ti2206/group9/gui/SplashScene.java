package nl.tudelft.ti2206.group9.gui;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.audio.AudioPlayer;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Input;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

/**
 * A SplashScene that shows "Press any key to continue".
 * @author Maikel, Maarten and Mitchell
 */
@SuppressWarnings("restriction")
public final class SplashScene extends AbstractMenuScene {

    /** Duration of transition in ms. */
    private static final int TRANSITION_TIME = 750;

	/** The AudioPlayer to be used for a button sound effect. */
	private static AudioPlayer apButton = new AudioPlayer("src/main/"
			+ "resources/nl/tudelft/ti2206/group9/audio/button.wav");

    /**
     * Create Splash label and set AnyKey event handlers.
     * @return an array of Nodes to be added to the Scene.
     */
	@Override
	public Node[] createContent() {
        addMouseClick();
        addKeyPressed();

        final int labelRows = 2;
        final int labelCols = 12;
        final Label text = createLabel("Press any key to continue");
        generateFadeTransition(text);
        text.setPrefWidth(labelCols * GRID_GAP);
        text.setPrefHeight(labelRows * GRID_GAP);
	    GridPane.setConstraints(text, GRID_WIDTH / 2 - labelCols / 2,
	    		GRID_HEIGHT / 2 + 2 - labelRows / 2);

        return new Node[]{text};
	}

	/**
     * Creating a new label for displaying text.
     * @param text a given sentence.
     * @return label resulting label.
     */
    private Label createLabel(final String text) {
        final Label label = new Label(text);
        Style.setLabelStyle(label);
		return label;
	}

    /**
     * Defining what has happens in case of a mouseClickEvent.
     */
    private void addMouseClick() {
    	setOnMouseClicked(me -> {
        	apButton.play(false);
            OBSERVABLE.notify(Category.INPUT, Input.MOUSE,
                    me.getButton());
            OBSERVABLE.notify(Category.MENU, Menu.ANY_KEY);
            ShaftEscape.setScene(new MainMenuScene());
        });
    }

    /**
     * Defining what happens in case of a random keyPressedEvent.
     */
    private void addKeyPressed() {
        setOnKeyPressed(ke -> {
        	apButton.play(false);
            OBSERVABLE.notify(Category.INPUT, Input.KEYBOARD,
                    ke.getCode());
            OBSERVABLE.notify(Category.MENU, Menu.ANY_KEY);
            ShaftEscape.setScene(new MainMenuScene());
        });
    }

    /**
     * Generates a fade transition for a specific label.
     * @param label given label.
     */
	private static void generateFadeTransition(final Label label) {
        final FadeTransition ft = new FadeTransition(
                Duration.millis(TRANSITION_TIME), label);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(TRANSITION_TIME * 2);
        ft.setAutoReverse(true);
        ft.play();
	}

	/**
	 * Every Button has an AudioPlayer for a sound effect.
	 * @return the button AudioPlayer.
	 */
	public static AudioPlayer getButtonAudioPlayer() {
		return apButton;
	}

}
