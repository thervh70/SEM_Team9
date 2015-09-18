package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mathias
 */
public class TrackPart {

    /** List of all entities in this trackpart. */
	private final List<AbstractEntity> entities =
			new LinkedList<AbstractEntity>();

    /** Size of the TrackPart. */
    private int length;

    /**
     * Get the list of entities.
     * @return List of entities
     */
    public final List<AbstractEntity> getEntities() {
        return entities;
    }
    
    /**
     * Adds an entity to the entities list.
     * @param entity entity to be added
     */
    public final void addEntity(final AbstractEntity entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from the entities list.
     * @param entity entity to be removed
     */
    public final void removeEntity(final AbstractEntity entity) {
        entities.remove(entity);
    }

    /**
     * Get the length of the TrackPart.
     * @return int the length of the TrackPart
     */
    public final int getLength() {
        return length;
    }

    /**
     * Set the length of the TrackPart.
     * @param lngth the length of the TrackPart
     */
    public final void setLength(final int lngth) {
        length = lngth;
    }
}
