package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Log;
import nl.tudelft.ti2206.group9.entities.Pillar;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;

/**
 * @author Maarten.
 */
@SuppressWarnings("restriction")
public class ExternalTicker extends AnimationTimer {

	/** Height of the box in-game where the score is displayed. */
	private static final int SCORE_BOX_HEIGHT = 120;
	/** Width of the box in-game where the score is displayed. */
	private static final int SCORE_BOX_WIDTH = 140;

	@Override
	public final void handle(final long now) {
		synchronized (GUIConstant.LOCK) {
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
			final Box track = new Box(3, 0.1, 500);
			track.setMaterial(new PhongMaterial(Color.WHITESMOKE));
			GameScene.addWorld(track);

			final Group entities = renderEntities();
			GameScene.addWorld(entities);
		}

		GameScene.addOverlay(renderScore());
	}

	/**
	 * @return VBox with score labels
	 */
	private VBox renderScore() {
		final Label highLabel = new Label("Highscore: "	+ State.getHighscore());
		final Label scoreLabel = new Label("Score: "
				+ State.modulo(State.getScore()));
		final Label distanceLabel = new Label("Distance: "
				+ State.modulo(State.getDistance()));
		final Label coinsLabel = new Label("Coins: " + State.getCoins());

		Style.setLabelStyle(highLabel);
		Style.setLabelStyle(scoreLabel);
		Style.setLabelStyle(distanceLabel);
		Style.setLabelStyle(coinsLabel);

		final VBox scoreBox = new VBox(highLabel, scoreLabel,
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
			for (final AbstractEntity entity : State.getTrack().getEntities()) {
				final Box entityBox = new Box(1, 1, 1);

				entityBox.setWidth(entity.getSize().getX());
				entityBox.setHeight(entity.getSize().getY());
				entityBox.setDepth(entity.getSize().getZ());
				entityBox.setTranslateX(entity.getCenter().getX());
				entityBox.setTranslateY(-entity.getCenter().getY());
				entityBox.setTranslateZ(entity.getCenter().getZ());

				if (entity instanceof Player) {
					entityBox.setMaterial(new PhongMaterial(Color.ORANGE));
				} else if (entity instanceof Coin) {
					entityBox.setMaterial(new PhongMaterial(Color.GOLD));
				} else if (entity instanceof Log) {
					entityBox.setMaterial(new PhongMaterial(Color.BROWN));
				} else if (entity instanceof Pillar) {
					entityBox.setMaterial(new PhongMaterial(Color.WHITE));
				} else {
					entityBox.setMaterial(new PhongMaterial(Color.GRAY));
				}
				entities.getChildren().add(entityBox);
            }
		}
		return entities;
	}
}

