package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;

@SuppressWarnings("restriction")
public class ExternalTicker extends AnimationTimer {

	@Override
	public void handle(final long now) {
		synchronized (GameWindow.LOCK) {
			renderScene();
		}
	}

	private void renderScene() {
		GameWindow.clearWorld();
		GameWindow.clearOverlay();

		final Box track = new Box(3, 0.1, 500);
		track.setMaterial(new PhongMaterial(Color.WHITESMOKE));
		GameWindow.addWorld(track);

		final Group entities = renderEntities();
		GameWindow.addWorld(entities);

		GameWindow.addOverlay(new Text(0, 16, "Score: " + State.getScore()));
		GameWindow.addOverlay(new Text(0, 32, "Distance: " 
				+ State.moduloDistance()));
		GameWindow.addOverlay(new Text(0, 48, "Coins: " + State.getCoins()));
	}

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

