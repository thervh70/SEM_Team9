package nl.tudelft.ti2206.group9.gui.renderer;

import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import nl.tudelft.ti2206.group9.gui.scene.GameScene;

/**
 * Renders the entire group of lights.
 * @author Robin
 */
@SuppressWarnings("restriction")
public class GroupLightRenderer extends Group implements Renderer {

    /**
     * This method sets up the Entities.
     */
	public GroupLightRenderer() {
        super();

        final int translateY = -5;
        final int aTranslateZ = -30;
        final int bTranslateZ = 40;
        final int cTranslateZ = 15;

        //Light behind camera, this one should be static
        final PointLight lightA = new PointLight(Color.LIGHTGRAY);
        lightA.setTranslateZ(aTranslateZ);
        lightA.setTranslateY(translateY);
        GameScene.addWorld(lightA);

        //Far light, should be moving
        final PointLight lightB = new PointLight(Color.GRAY);
        lightB.setTranslateZ(bTranslateZ);
        lightB.setTranslateY(translateY);
        GameScene.addWorld(lightB);

        //Near Light, should be moving
        final PointLight lightC = new PointLight(Color.GRAY);
        lightC.setTranslateZ(cTranslateZ);
        lightC.setTranslateY(translateY);
        GameScene.addWorld(lightC);
    }

    /** Needed for moving of lights. */
    public void update() { //NOPMD - not implemented yet

    }
}
