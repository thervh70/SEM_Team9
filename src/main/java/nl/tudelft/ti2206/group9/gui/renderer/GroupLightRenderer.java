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
     *
     * @param zIndex Depth to render the lights.
     */
    private void renderLight(final int zIndex) {
        final int amount = 3;
        final int translateY = -4;

        for (int i = 0; i < amount; i++) {
            final PointLight light = new PointLight(Color.GRAY);
            light.setTranslateZ(i * Track.LENGTH + zIndex * 2);
            light.setTranslateY(translateY);
            getChildren().add(light);
        }
    }

    /**
     * Updates the position, adds and deletes lights from the scene.
     */
    public void update() {
        if (InternalTicker.isRunning()) {
            final double unitsPerTick = Track.getUnitsPerTick();
            for (final Node node : getChildren()) {
                node.setTranslateZ(node.getTranslateZ() - unitsPerTick);
            }

            final double lightDepth = getChildren().get(
                    getChildren().size() - 1).getTranslateZ();
            if (lightDepth < Track.LENGTH) {
                renderLight((int) lightDepth);
            }

            final int renderspace = (int) Track.LENGTH;
            for (int i = 0; i < getChildren().size(); i++) {
                if (this.getChildren().get(i).getTranslateZ()
                        + renderspace > GameScene.CAMERA_TRANS.getZ()) {
                    break;
                }

                getChildren().remove(i);
            }
        }
    }
}
