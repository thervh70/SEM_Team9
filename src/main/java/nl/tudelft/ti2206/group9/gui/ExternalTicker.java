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
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.Track;

/**
 * @author Maarten.
 */
@SuppressWarnings("restriction")
public class ExternalTicker extends AnimationTimer {

	/** Equal to 1000. */
	private static final double E3 = 1000.0;
	/** Height of the box in-game where the score is displayed. */
	private static final int SCORE_BOX_HEIGHT = 90 + 60;
	/** Width of the box in-game where the score is displayed. */
	private static final int SCORE_BOX_WIDTH = 130;

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
			final Box track = new Box(3, 0.1, 500);
			track.setMaterial(new PhongMaterial(Color.WHITESMOKE));
			GameScreen.addWorld(track);

			final Group entities = renderEntities();
			GameScreen.addWorld(entities);
		}
		
		GameScreen.addOverlay(renderScore());
	}

	/**
	 * @return VBox with score labels
	 */
	private VBox renderScore() {
		Label scoreLabel = new Label(("Score: "
				+ State.modulo(State.getScore())));
		Label distanceLabel = new Label("Distance: "
				+ State.modulo(State.getDistance()));
		Label coinsLabel = new Label(("Coins: " + State.getCoins()));
		Label speedLabel = new Label(("Speed: " 
				+ (double) Math.round(Track.getUnitsPerTick() * E3) / E3));
		Label ticksLabel = new Label(("Ticks: " + State.getTrack().getTicks()));

		Style.setLabelStyle(scoreLabel);
		Style.setLabelStyle(distanceLabel);
		Style.setLabelStyle(coinsLabel);
		Style.setLabelStyle(speedLabel);
		Style.setLabelStyle(ticksLabel);

		VBox scoreBox = new VBox(scoreLabel, distanceLabel, coinsLabel, 
				speedLabel, ticksLabel);
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
				} else {
					entityBox.setMaterial(new PhongMaterial(Color.GREEN));
				}

				entities.getChildren().add(entityBox);
            }
		}
		return entities;
	}
}

