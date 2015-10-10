package nl.tudelft.ti2206.group9.level;

import java.util.LinkedList;
import java.util.List;

import nl.tudelft.ti2206.group9.level.entity.AbstractEntity;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Mathias
 */
public class TrackPart {

    /** List of all entities in this trackpart. */
    private final List<Node> entities = new LinkedList<Node>();

    /** Size of the TrackPart. */
    private int length;

    /**
     * Get the list of entities.
     * @return List of entities
     */
    public final List<Node> getEntities() {
        return entities;
    }

    /**
     * Adds an entity to the entities list.
     * @param entity entity to be added
     */
    public final void addEntity(final Node entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from the entities list.
     * @param entity entity to be removed
     */
    public final void removeEntity(final Node entity) {
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

    /**
     * A node of the TrackPart.
     * @author Maarten
     */
    static class Node {
    	/** Type of the node. */
    	private final Class<? extends AbstractEntity> type;
    	/** Center of the Entity. */
    	private final Point3D center;

		/**
		 * @param nodeType the type of the Node.
		 * @param entityCenter the center of the Entity.
		 */
		public Node(final Class<? extends AbstractEntity> nodeType,
				final Point3D entityCenter) {
			super();
			this.type = nodeType;
			this.center = entityCenter;
		}

		/**
		 * @return the type of the Node.
		 */
		public Class<? extends AbstractEntity> getType() {
			return type;
		}

		/**
		 * @return the center of the Entity.
		 */
		public Point3D getCenter() {
			return center;
		}
    }

}
