package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.CacheHint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.Track;

/**
 * This class renders the two Walls in the scene.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class GroupWallRenderer extends AbstractGroupRenderer {

	/** Default constructor. */
	public GroupWallRenderer() {
		super();
		final int offset = 3;
		final double correction = 1.5;
		for (int h = 0; h < 2; h++) { //two walls
			for (int i = 0; i < (int) Track.LENGTH; i++) {	// 100 units long
				for (int j = 0; j < Track.WIDTH; j++) {		// height of walls
					final Box wallPiece = new Box(0, 1, 1);
					wallPiece.setTranslateX(h * offset - correction);
					wallPiece.setTranslateY(j - offset);
					wallPiece.setTranslateZ(i);
					wallPiece.setMaterial(randomMaterial());
					wallPiece.setCache(true);
					wallPiece.setCacheHint(CacheHint.SPEED);
					getChildren().add(wallPiece);
				}
			}
		}
	}

	/**
	 * Method to return a random material for the walls.
	 * @return The material for the wallPiece.
	 */
	private static PhongMaterial randomMaterial() {
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

	/** Does nothing yet. */
	public void update() { } //NOPMD - nothing should be updated yet

}
