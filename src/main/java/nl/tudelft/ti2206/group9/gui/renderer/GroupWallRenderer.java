package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.Node;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
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

    /** Used to point to the third vertex in the faces array of the Mesh. */
    private static final int V3 = 3;

    /** Default constructor. */
    public GroupWallRenderer() {
        super();
        renderWalls(-1);
    }

    /**
     * Method to render the walls.
     * @param zPosition Depth of the render.
     */
    private void renderWalls(final double zPosition) {
        getChildren().add(createMesh(zPosition, -1));
        getChildren().add(createMesh(zPosition, 1));
    }

    /**
     * Creates a wall mesh.
     * @param zOffset the z offset.
     * @param leftRight -1: left. 1: right.
     * @return a wall mesh.
     */
    private MeshView createMesh(final double zOffset, final int leftRight) {
        final float wallHeight = 8f;
        final float wallDepth = (float) Track.LENGTH;
        final float wallXPos = leftRight * (float) (Track.WIDTH + 1) / 2f;

        final TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(new float[]{
                wallXPos, 0, 0,
                wallXPos, 0, wallDepth,
                wallXPos, -wallHeight, wallDepth,
                wallXPos, -wallHeight, 0, // Negative, because y is inverted
        });
        mesh.getTexCoords().addAll(new float[]{
                0, 0,
                0, wallDepth,
                wallHeight, wallDepth,
                wallHeight, 0,
        });
        if (leftRight < 0) {  // the faces have to face right
            mesh.getFaces().addAll(new int[]{ 0, 0, 1, 1, 2, 2,
                    2, 2, V3, V3, 0, 0 });
        } else {              // the faces have to face left
            mesh.getFaces().addAll(new int[]{ 2, 2, 1, 1, 0, 0,
                    0, 0, V3, V3, 2, 2 });
        }
        final MeshView box = new MeshView(mesh);
        box.setTranslateZ(zOffset);
        box.setMaterial(Style.BRICK);
        return box;
    }

    /**
     * This method is responsible for updating, adding and removing wall pieces.
     */
    public void update() {
        if (InternalTicker.isRunning()) {
            // Move existing walls
            final double unitsPerTick = Track.getUnitsPerTick();
            for (final Node node : getChildren()) {
                node.setTranslateZ(node.getTranslateZ() - unitsPerTick);
            }

            // Render new walls if needed
            final double wallDepth = getChildren().get(
                    getChildren().size() - 1).getTranslateZ();
            if (wallDepth < Track.LENGTH) {
                renderWalls(wallDepth + Track.LENGTH);
            }

            // Remove walls out of sight
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
