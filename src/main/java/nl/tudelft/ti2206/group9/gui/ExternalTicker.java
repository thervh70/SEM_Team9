package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;

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

		PhongMaterial floor = new PhongMaterial();
		Image texture = new Image("texture_cobblestone.png");
		floor.setDiffuseMap(texture);

		for(int i = 0; i < 500; i++){
			for(int j = 0; j < 3; j++){
				final Box trackPiece = new Box(1, 0, 1);
				trackPiece.setLayoutX(j - 1);
				trackPiece.setTranslateZ(i);
				trackPiece.setMaterial(floor);
				GameScreen.addWorld(trackPiece);
			}
		}


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
		PhongMaterial wood = new PhongMaterial();
		Image texture = new Image("texture_wood.png");
		wood.setDiffuseMap(texture);


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
					entityBox.setMaterial(wood);
				}

				entities.getChildren().add(entityBox);
            }
		}
		return entities;
	}
}

