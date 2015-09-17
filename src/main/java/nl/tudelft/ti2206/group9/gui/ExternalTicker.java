package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.image.Image;
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

		PhongMaterial floor = new PhongMaterial();
		Image texture = new Image("texture_cobblestone.png");
		floor.setDiffuseMap(texture);

		for(int i = 0; i < 500; i++){
			for(int j = 0; j < 3; j++){
				final Box trackPiece = new Box(1, 0, 1);
				trackPiece.setTranslateX(j - 1);
				trackPiece.setTranslateZ(i);
				trackPiece.setMaterial(floor);
				GameScreen.addWorld(trackPiece);
			}
		}

		PhongMaterial brick = new PhongMaterial();
		Image brickTexture = new Image("texture_brick.png");
		brick.setDiffuseMap(brickTexture);

		PhongMaterial moss = new PhongMaterial();
		Image mossTexture = new Image("texture_moss.png");
		moss.setDiffuseMap(mossTexture);

		PhongMaterial crack = new PhongMaterial();
		Image crackTexture = new Image("texture_crack.png");
		crack.setDiffuseMap(crackTexture);

		/**
		 * h: 2 walls
		 * i: 500 wallpieces in depth
		 * j: 3 wallpieces in height
		 */
		for(int h = 0; h < 2; h++) {
			for (int i = 0; i < 500; i++) {
				for (int j = 0; j < 3; j++) {
					final Box wallPiece = new Box(0, 1, 1);
					wallPiece.setTranslateY(j - 3);
					wallPiece.setTranslateZ(i);
					wallPiece.setTranslateX(h * 3 - 1.5);
					wallPiece.setMaterial(brick);
					GameScreen.addWorld(wallPiece);
				}
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
				} else if (entity instanceof Log){
					entityBox.setMaterial(wood);
				} else if (entity instanceof Pillar) {
					entityBox.setMaterial(new PhongMaterial(Color.WHITE));
				} else /*if (entity instanceof Fence) */{
					entityBox.setMaterial(new PhongMaterial(Color.GRAY));
				}

				entities.getChildren().add(entityBox);
            }
		}
		return entities;
	}
}

