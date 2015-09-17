package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import nl.tudelft.ti2206.group9.entities.*;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Maarten.
 */
@SuppressWarnings("restriction")
public class ExternalTicker extends AnimationTimer {

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

		final Box track = new Box(3, 0.1, 500);
		track.setMaterial(new PhongMaterial(Color.WHITESMOKE));
		GameScreen.addWorld(track);

		final Group entities = renderEntities();
		GameScreen.addWorld(entities);

		GameScreen.addOverlay(new Text(0, 16, "Score: " 
				+ State.modulo(State.getScore())));
		GameScreen.addOverlay(new Text(0, 32, "Distance: " 
				+ State.modulo(State.getDistance())));
		GameScreen.addOverlay(new Text(0, 48, "Coins: " + State.getCoins()));
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
				} else if (entity instanceof Log){
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

