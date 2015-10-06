package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.renderer.GroupEntitiesRenderer;
import nl.tudelft.ti2206.group9.renderer.GroupTrackRenderer;
import nl.tudelft.ti2206.group9.renderer.GroupWallRenderer;

/**
 * @author Maarten.
 */
@SuppressWarnings("restriction")
public class ExternalTicker extends AnimationTimer {

	/** Height of the box in-game where the score is displayed. */
	private static final int SCORE_BOX_HEIGHT = 130;
	/** Width of the box in-game where the score is displayed. */
	private static final int SCORE_BOX_WIDTH = 140;
	/** Label for the countdownLabel animation. */
	private final Label countdownLabel = new Label();

	/** List that stores the entities, to be held up-to-date with Track. */
	private final GroupEntitiesRenderer entities;
	/** Group that stores the wall. */
	private final GroupWallRenderer wall;
	/** Group that stores the track. */
	private final GroupTrackRenderer track;

	/** Default constructor. */
	public ExternalTicker() {
		super();

		if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
			entities = new GroupEntitiesRenderer();
			wall = new GroupWallRenderer();
			track = new GroupTrackRenderer();

			entities.setDepthTest(DepthTest.ENABLE);
			GameScene.addWorld(entities, wall, track);
		} else {
			entities = null;
			wall = null;
			track = null;
		}
	}

	@Override
	public final void handle(final long now) {
		synchronized (ShaftEscape.TICKER_LOCK) {
			renderScene();
		}
	}

    /**
     * This method renders the scene.
     */
	private void renderScene() {
		GameScene.clearOverlay();
		GameScene.addOverlay(renderScore(), countdownLabel);

		if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
			entities.update();
			wall.update();
			track.update();
		}
	}

	/**
	 * @return VBox with score labels
	 */
	private VBox renderScore() {
		final Label nameLabel = new Label(State.getPlayerName());
		final Label highLabel = new Label("Highscore: "
				+ State.getHighscore());
		final Label scoreLabel = new Label("Score: "
				+ State.modulo(State.getScore()));
		final Label distanceLabel = new Label("Distance: "
				+ State.modulo(State.getDistance()));
		final Label coinsLabel = new Label("Coins: "
				+ State.getCoins());

		Style.setLabelStyle(nameLabel);
		Style.setLabelStyle(highLabel);
		Style.setLabelStyle(scoreLabel);
		Style.setLabelStyle(distanceLabel);
		Style.setLabelStyle(coinsLabel);

		final VBox scoreBox = new VBox(nameLabel, highLabel, scoreLabel,
				distanceLabel, coinsLabel);
		scoreBox.setStyle(" -fx-background-color:BLACK;");
		scoreBox.setMinSize(SCORE_BOX_WIDTH, SCORE_BOX_HEIGHT);
		return scoreBox;
	}

	/**
	 * Render the countdownLabel method.
	 * @param index The index to be rendered.
	 */
	public void countdown(final int index) {
		final int textSize = 64;
		final int offsetX = 20;
		final int offsetY = 50;

		countdownLabel.setText(Integer.toString(index));
		countdownLabel.setFont(Font.font("Roboto", FontWeight.BOLD, textSize));
		countdownLabel.setTextFill(Color.WHITE);
		countdownLabel.setLayoutX(ShaftEscape.WIDTH / 2 - offsetX);
		countdownLabel.setLayoutY(ShaftEscape.HEIGHT / 2 - offsetY);

		countdownAnimation(index);
	}

	/**
	 * Render the animations for the countdownLabel method.
	 * @param index The index to be rendered.
	 */
	private void countdownAnimation(final int index) {
		final int duration = 400;
		final ScaleTransition st = new ScaleTransition(
				Duration.millis(duration), countdownLabel);
		st.setByY(1);
		st.setByX(1);
		st.setCycleCount(2);
		st.setAutoReverse(true);
		st.play();

		final FadeTransition ft = new FadeTransition(
				Duration.millis(duration), countdownLabel);
		ft.setAutoReverse(true);
		ft.setCycleCount(2);
		ft.setToValue(1);
		ft.setFromValue(0);
		ft.play();

		st.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent event) {
				final int newIndex = index - 1;
				if (newIndex > 0) {
					countdown(newIndex);
				} else {
					InternalTicker.start();
					GameScene.setRunning(true);
				}
			}
		});
	}

}

