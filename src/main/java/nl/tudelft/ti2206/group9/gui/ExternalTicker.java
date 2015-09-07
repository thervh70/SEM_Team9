package nl.tudelft.ti2206.group9.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.Track;

@SuppressWarnings("restriction")
public class ExternalTicker extends AnimationTimer {

	@Override
	public void handle(final long now) {
		renderScene();
	}
	
	private void renderScene() {
		Group root = GameWindow.getRoot();
		root.getChildren().clear();

		final Box backwall = new Box(1000, 1000, 1);
		backwall.setMaterial(new PhongMaterial(Color.AQUA));
		backwall.setTranslateZ(Track.LENGTH);

		final Box track = new Box(3, 0.1, 500);
		track.setMaterial(new PhongMaterial(Color.WHITESMOKE));

		//Track first because of layering (kind of buggy)
		root.getChildren().add(backwall);
		root.getChildren().add(track);

//		root.getChildren().add(renderAxes(root));
		
		final Group entities = renderEntities();
		root.getChildren().add(entities);
	}
	
	private Group renderEntities() {
		final Group entities = new Group();
		entities.setDepthTest(DepthTest.ENABLE);
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
		return entities;
	}
/*
	private Group renderAxes(Group root) {
		final Group axisGroup = new Group();

		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		final Box xAxis = new Box(20, .1, .1);
		final Box yAxis = new Box(.1, 20, .1);
		final Box zAxis = new Box(.1, .1, 20);

		xAxis.setTranslateX(10);
		yAxis.setTranslateY(10);
		zAxis.setTranslateZ(10);

		xAxis.setMaterial(redMaterial);
		yAxis.setMaterial(greenMaterial);
		zAxis.setMaterial(blueMaterial);

		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		return axisGroup;
	}
*/
}

