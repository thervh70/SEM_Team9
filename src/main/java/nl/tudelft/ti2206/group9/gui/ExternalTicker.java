package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.entities.*;
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
		GameScreen.clearWorld();
		GameScreen.clearOverlay();

		if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
			final Group entities = renderEntities();
			GameScreen.addWorld(entities);
		}

		GameScreen.addOverlay(renderScore());
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
			/**
			 * This method is for producing track pieces.
			 * This is a working version, however refactoring is needed.
			 */
			for(int i = 0; i < 100; i++){
				for(int j = 0; j < 3; j++){
					final Box trackPiece = new Box(1.5, 0, 1.5);
					trackPiece.setTranslateX(j - 1);
					trackPiece.setTranslateZ(i);
					trackPiece.setMaterial(Style.FLOOR);
					trackPiece.setCache(true);
					trackPiece.setCacheHint(CacheHint.SPEED);
					entities.getChildren().add(trackPiece);
				}
			}

			/**
			 * The same applies here as described above.
			 * This part handles the walls.
			 * h: 2 walls
			 * i: 500 wallpieces in depth
			 * j: 3 wallpieces in height
			 */
			for(int h = 0; h < 2; h++) {
				for (int i = 0; i < 100; i++) {
					for (int j = 0; j < 3; j++) {
						double random = Math.random();
						final Box wallPiece = new Box(0, 1, 1);
						wallPiece.setTranslateY(j - 3);
						wallPiece.setTranslateZ(i);
						wallPiece.setTranslateX(h * 3 - 1.5);
						if( random < 0.5) {
							wallPiece.setMaterial(Style.BRICK);
						} else if ( random > 0.5 && random < 0.75) {
							wallPiece.setMaterial(Style.CRACK);
						} else {
							wallPiece.setMaterial(Style.MOSS);
						}
						wallPiece.setCache(true);
						wallPiece.setCacheHint(CacheHint.SPEED);
						entities.getChildren().add(wallPiece);
					}
				}
			}

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
					entityBox.setMaterial(Style.COIN);
				} else if (entity instanceof Log){
					entityBox.setMaterial(Style.WOOD);
				} else if (entity instanceof Pillar) {
					entityBox.setMaterial(new PhongMaterial(Color.WHITE));
				} else /*if (entity instanceof Fence) */{
					entityBox.setMaterial(new PhongMaterial(Color.GRAY));
				}

				entityBox.setCache(true);
				entityBox.setCacheHint(CacheHint.SPEED);
				entities.getChildren().add(entityBox);
            }
		}
		return entities;
	}
}

