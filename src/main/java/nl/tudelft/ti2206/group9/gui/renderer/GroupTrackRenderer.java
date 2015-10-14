package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import nl.tudelft.ti2206.group9.gui.Style;
import nl.tudelft.ti2206.group9.gui.scene.GameScene;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.Track;


/**
 * Renders the entire Track. The Track is a collection of tiles.
 * @author Maarten
 */
@SuppressWarnings("restriction")
public class GroupTrackRenderer extends AbstractGroupRenderer {

    /**
     * Default constructor.
     */
    public GroupTrackRenderer() {
        super();
        renderTrack(0);
    }

    /**
     * Method to render.
     * @param zIndex Depth of the render.
     */
    private void renderTrack(final int zIndex) {
        final double trackBoxX = 1.5;
        final double trackBoxZ = 1.5;
        final double translateY = -6.5;
        for (int h = 0; h < 2; h++) {
            for (int j = 0; j < Track.WIDTH; j++) {
                for (int i = zIndex; i < zIndex + Track.LENGTH; i++) {
                    final Box trackPiece = new Box(
                            trackBoxX, 0, trackBoxZ);
                    trackPiece.setTranslateX(j - 1);
                    trackPiece.setTranslateY(h * translateY);
                    trackPiece.setTranslateZ(i);
                    trackPiece.setMaterial(Style.FLOOR);
                    trackPiece.setCache(true);
                    trackPiece.setCacheHint(CacheHint.SPEED);
                    getChildren().add(trackPiece);
                }
            }
        }
    }

    /**
     * This method is responsible for moving, deleting and adding track pieces.
     */
    public void update() {
        if (InternalTicker.isRunning()) {
            final double unitsPerTick = Track.getUnitsPerTick();
            for (final Node node : this.getChildren()) {
                node.setTranslateZ(node.getTranslateZ() - unitsPerTick);
            }

            final double trackDepth = this.getChildren().
                    get(this.getChildren().size() - 1).getTranslateZ();
            if (trackDepth < Track.LENGTH) {
                renderTrack((int) trackDepth);
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
