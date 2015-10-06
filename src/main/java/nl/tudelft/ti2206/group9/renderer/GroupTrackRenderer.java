package nl.tudelft.ti2206.group9.renderer;

import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.level.Track;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.shape.Box;

/**
 * Renders the entire Track. The Track is a collection of tiles.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class GroupTrackRenderer extends Group implements Renderer {

	/**
	 * Default constructor.
	 */
	public GroupTrackRenderer() {
		super();
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
				getChildren().add(trackPiece);
			}
		}
	}

	/** Does nothing yet. */
	public void update() { } //NOPMD - nothing should be updated yet

}
