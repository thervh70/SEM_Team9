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

		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 3; j++){
				final Box trackPiece = new Box(1, 0, 1);
				trackPiece.setTranslateX(j - 1);
				trackPiece.setTranslateZ(i);
				trackPiece.setMaterial(Style.FLOOR);
				GameScreen.addWorld(trackPiece);
			}
		}

		/**
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
					entityBox.setMaterial(Style.COIN);
				} else if (entity instanceof Log){
					entityBox.setMaterial(Style.WOOD);
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

