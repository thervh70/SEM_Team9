package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import nl.tudelft.ti2206.group9.gui.scene.GameScene;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.Track;

/**
 * Renders the entire group of lights.
 * @author Robin
 */
@SuppressWarnings("restriction")
public class GroupLightRenderer extends AbstractGroupRenderer {

    /**
     * This method sets up the Entities.
     */
    public GroupLightRenderer() {
        super();
        renderLight(0);

        final int translateY = -5;
        final int translateStaticLight = -30;

        //Light behind camera, this one should be static
        final PointLight lightA = new PointLight(Color.LIGHTGRAY);
        lightA.setTranslateZ(translateStaticLight);
        lightA.setTranslateY(translateY);
        GameScene.addWorld(lightA);
    }

    /**
     * Render method for the lights.
     * @param zIndex Depth to render the lights.
     */
    private void renderLight(final int zIndex) {
        final int amount = 3;
        final int translateY = -5;

        for (int i = 0; i < amount; i++) {
            final PointLight light = new PointLight(Color.GRAY);
            light.setTranslateZ(i * Track.LENGTH  + zIndex * 2);
            light.setTranslateY(translateY);
            getChildren().add(light);
        }
    }

    /** Needed for moving of lights. */
    public void update() { //NOPMD - not implemented yet
        if (InternalTicker.isRunning()) {
            for (final Node node : this.getChildren()) {
                node.setTranslateZ(node.getTranslateZ()
                        - Track.getUnitsPerTick());
            }

            double lightDepth = this.getChildren().get(
                    this.getChildren().size() - 1).getTranslateZ();
            if (lightDepth < Track.LENGTH) {
                renderLight((int) lightDepth);
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
