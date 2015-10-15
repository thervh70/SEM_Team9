package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.Node;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
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

    /** Height of ceiling. Negative, because y is inverted. */
    private static final int CEILING_HEIGHT = -8;
    /** Used to point to the third vertex in the faces array of the Mesh. */
    private static final int V3 = 3;

    /**
     * Default constructor.
     */
    public GroupTrackRenderer() {
        super();
        renderTracks(-1);
    }

    /**
     * Method to render the walls.
     * @param zPosition Depth of the render.
     */
    private void renderTracks(final double zPosition) {
        getChildren().add(createMesh(zPosition, 0));
        getChildren().add(createMesh(zPosition, CEILING_HEIGHT));
    }

    /**
     * Creates a track mesh.
     * @param zOffset the z offset.
     * @param yPos the y position of the track.
     * @return a track mesh.
     */
    private MeshView createMesh(final double zOffset, final int yPos) {
        final float trackDepth = (float) Track.LENGTH;
        final float trackWidth = (float) (Track.WIDTH + 1) / 2f;

        final TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(new float[]{
                -trackWidth, yPos, 0,
                -trackWidth, yPos, trackDepth,
                trackWidth, yPos, trackDepth,
                trackWidth, yPos, 0,
        });
        mesh.getTexCoords().addAll(new float[]{
                -trackWidth, 0,
                -trackWidth, trackDepth,
                trackWidth, trackDepth,
                trackWidth, 0,
        });
        if (yPos < -1) {       // on the floor, the faces have to face up
            mesh.getFaces().addAll(new int[]{ 0, 0, 1, 1, 2, 2,
                    2, 2, V3, V3, 0, 0 });
        } else {               // on the ceiling, the faces have to face down
            mesh.getFaces().addAll(new int[]{ 2, 2, 1, 1, 0, 0,
                    0, 0, V3, V3, 2, 2 });
        }
        final MeshView box = new MeshView(mesh);
        box.setTranslateZ(zOffset);
        box.setMaterial(Style.FLOOR);
        return box;
    }

    /**
     * This method is responsible for moving, deleting and adding track pieces.
     */
    public void update() {
        if (InternalTicker.isRunning()) {
            // Move existing tracks
            final double unitsPerTick = Track.getUnitsPerTick();
            for (final Node node : getChildren()) {
                node.setTranslateZ(node.getTranslateZ() - unitsPerTick);
            }

            // Render new tracks when needed
            final double trackDepth = getChildren().
                    get(getChildren().size() - 1).getTranslateZ();
            if (trackDepth < Track.LENGTH) {
                renderTracks(trackDepth + Track.LENGTH);
            }

            // Remove tracks out of sight
            Node child;
            for (int i = 0; i < getChildren().size(); i++) {
                child = getChildren().get(i);
                if (child.getTranslateZ() + Track.LENGTH
                        > GameScene.CAMERA_TRANS.getZ()) {
                    break;
                }
                getChildren().remove(i);
            }
        }
    }

}
