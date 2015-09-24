package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Log;
import nl.tudelft.ti2206.group9.entities.Pillar;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.Track;

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
		final Label highLabel = new Label("Highscore: "
				+ State.getHighscore());
		final Label scoreLabel = new Label("Score: "
				+ State.modulo(State.getScore()));
		final Label distanceLabel = new Label("Distance: "
				+ State.modulo(State.getDistance()));
		final Label coinsLabel = new Label("Coins: "
				+ State.getCoins());

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

			entities.getChildren().addAll(
					renderTrack(), renderWall());

			for (final AbstractEntity entity
					: State.getTrack().getEntities()) {
				final Box entityBox = new Box(1, 1, 1);

				setDimensions(entity, entityBox);
				setMaterial(entity, entityBox);

				entityBox.setCache(true);
				entityBox.setCacheHint(CacheHint.SPEED);
				entities.getChildren().add(entityBox);
            }
		}
		return entities;
	}

	/**
	 * Separate method to set the dimensions of the box.
	 * @param entity The entity that contains the dimensions.
	 * @param entityBox The box representing the entity.
	 */
	private static void setDimensions(
			final AbstractEntity entity, final Box entityBox) {
		entityBox.setWidth(entity.getSize().getX());
		entityBox.setHeight(entity.getSize().getY());
		entityBox.setDepth(entity.getSize().getZ());
		entityBox.setTranslateX(
				entity.getCenter().getX());
		entityBox.setTranslateY(
				-entity.getCenter().getY());
		entityBox.setTranslateZ(
				entity.getCenter().getZ());
	}
	/**
	 * Separate method to set material.
	 * @param entity The entity that has to be textured.
	 * @param entityBox The box that represents the entity.
	 */
	private static void setMaterial(
			final AbstractEntity entity, final Box entityBox) {
		if (entity instanceof Player) {
			entityBox.setMaterial(Style.PLAYER);
		} else if (entity instanceof Coin) {
			entityBox.setMaterial(Style.COIN);
		} else if (entity instanceof Log) {
			entityBox.setMaterial(Style.WOOD);
		} else if (entity instanceof Pillar) {
			entityBox.setMaterial(Style.PILLAR);
		} else /*if (entity instanceof Fence) */ {
			entityBox.setMaterial(Style.FENCE);
		}
	}

	/**
	 * This method is for producing track pieces.
	 * This is a working version, however refactoring is needed.
	 * @return Group with all the track parts
	 */
	private Group renderTrack() {
		final Group result = new Group();
		final double trackBoxX = 1.5;
		final double trackBoxZ = 1.5;
		for (int i = 0; i < (int) Track.LENGTH; i++) {
			for (int j = 0; j < Track.WIDTH; j++) {
				final Box trackPiece = new Box(
						trackBoxX, 0, trackBoxZ);
				trackPiece.setTranslateX(j - 1);
				trackPiece.setTranslateZ(i);
				trackPiece.setMaterial(Style.FLOOR);
				trackPiece.setCache(true);
				trackPiece.setCacheHint(CacheHint.SPEED);
				result.getChildren().add(trackPiece);
			}
		}
		return result;
	}

	/**
	 * The same applies here as described above.
	 * This part handles the walls.
	 * h: 2 walls
	 * i: 500 wallpieces in depth
	 * j: 3 wallpieces in height
	 * @return Group with all the wallpieces
	 */
	private Group renderWall() {
		final Group result = new Group();
		final int offset = 3;
		final double correction = 1.5;
		for (int h = 0; h < 2; h++) {
			for (int i = 0; i < (int) Track.LENGTH; i++) {
				for (int j = 0; j < Track.WIDTH; j++) {
					final Box wallPiece = new Box(0, 1, 1);
					wallPiece.setTranslateY(j - offset);
					wallPiece.setTranslateZ(i);
					wallPiece.setTranslateX(h
							* offset - correction);
					wallPiece.setMaterial(chooseMaterial());
					wallPiece.setCache(true);
					wallPiece.setCacheHint(CacheHint.SPEED);
					result.getChildren().add(wallPiece);
				}
			}
		}
		return result;
	}

	/**
	 * Method to return a random material for the walls.
	 * @return The material for the wallPiece.
	 */
	private static PhongMaterial chooseMaterial() {
		final double random = Math.random();
		final double alpha = 0.5;
		final double beta = 0.75;

		if (random < alpha) {
			return Style.BRICK;
		} else if (random > alpha && random < beta) {
			return Style.CRACK;
		} else {
			return Style.MOSS;
		}
	}
}

