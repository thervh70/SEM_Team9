package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.renderer.BoxRenderer;
import nl.tudelft.ti2206.group9.renderer.TrackRenderer;
import nl.tudelft.ti2206.group9.renderer.WallRenderer;

/**
 * @author Maarten.
 */
@SuppressWarnings("restriction")
public class ExternalTicker extends AnimationTimer {

	/** Height of the box in-game where the score is displayed. */
	private static final int SCORE_BOX_HEIGHT = 130;
	/** Width of the box in-game where the score is displayed. */
	private static final int SCORE_BOX_WIDTH = 140;

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
		GameScene.clearWorld();
		GameScene.clearOverlay();

		if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
			final Group entities = renderEntities();
			GameScene.addWorld(entities);
		}

		GameScene.addOverlay(renderScore());
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
     * This method renders all Entities.
     * @return group
     */
	private Group renderEntities() {
		final Group entities = new Group();
		entities.setDepthTest(DepthTest.ENABLE);
		synchronized (State.getTrack()) {

			entities.getChildren().addAll(new TrackRenderer(),
					new WallRenderer());

			for (final AbstractEntity entity : State.getTrack().getEntities()) {
				final Box entityBox = new BoxRenderer(entity);

				entityBox.setCache(true);
				entityBox.setCacheHint(CacheHint.SPEED);
				entities.getChildren().add(entityBox);
            }
		}
		return entities;
	}

}

