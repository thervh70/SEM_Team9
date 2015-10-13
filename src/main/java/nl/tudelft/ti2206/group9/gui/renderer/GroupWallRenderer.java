package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.scene.GameScene;
import nl.tudelft.ti2206.group9.level.InternalTicker;
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
        renderWall(0);
    }

    /**
     * Method to render the walls.
     * @param zIndex Depth of the render.
     */
    private void renderWall(final int zIndex) {
        final int xOffset = 3;
        final int yOffset = 10;
        final int zOffset = 4;
        final double correction = 1.5;
        for (int h = 0; h < 2; h++) {
            for (int i = zIndex; i < zIndex + Track.LENGTH; i++) {
                for (int j = 0; j < yOffset + 1; j++) {
                    final Box wallPiece = new Box(0, 1, 1);
                    wallPiece.setTranslateX(h * xOffset - correction);
                    wallPiece.setTranslateY(j - yOffset);
                    wallPiece.setTranslateZ(i - zOffset);
                    wallPiece.setMaterial(Style.BRICK);
                    wallPiece.setCache(true);
                    wallPiece.setCacheHint(CacheHint.SPEED);
                    getChildren().add(wallPiece);
                }
            }
        }
    }

    /**
     * This method is responsible for updating, adding and removing wall pieces.
     */
    public void update() {
        if (InternalTicker.isRunning()) {
            for (final Node node : this.getChildren()) {
                node.setTranslateZ(node.getTranslateZ()
                        - Track.getUnitsPerTick());
            }

            double wallDepth = this.getChildren().get(
                    this.getChildren().size() - 1).getTranslateZ();
            if (wallDepth < Track.LENGTH) {
                renderWall((int) wallDepth);
            }

            int index = 0;
            while (true) {
                if (index >= this.getChildren().size()) {
                    break;
                } else if (this.getChildren().get(index).getTranslateZ()
                        > GameScene.CAMERA_TRANS.getZ()) {
                    break;
                }

                this.getChildren().remove(index);
                index++;
            }
        }
    }

}
