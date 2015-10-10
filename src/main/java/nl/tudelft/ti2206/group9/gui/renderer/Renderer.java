package nl.tudelft.ti2206.group9.gui.renderer;

/**
 * Should be implemented by all classes that render objects in the scene.
 * @author Maarten
 */
public interface Renderer {

    /**
     * Called every tick. Updates the current Renderer
     * (e.g. position, size, ...)
     */
    void update();

}
