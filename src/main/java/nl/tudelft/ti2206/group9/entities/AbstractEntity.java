package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Ancestor class for all Entities.
 * @author Maarten
 */
public abstract class AbstractEntity {

	/** Center of the bounding box of this Entity */
	private Point3D center;
	/** Size of the bounding box of this Entity */
	private Point3D size;

	/**
	 * Default constructor.
	 * @param center center of the bounding box
	 * @param size size of the bounding box
	 */
	public AbstractEntity(final Point3D center, final Point3D size) {
		super();
		this.center = center;
		this.size = size;
	}

	/**
	 * Call this method to check if this entity is colliding to another one.
	 * The method {@link #collision(AbstractEntity)} is called when entities
	 * collide.
	 * @param other AbstractEntity to check collision with.
	 */
	public final void checkCollision(final AbstractEntity other) {
		final Point3D thisCenter = new Point3D(this.getCenter());
		final Point3D thisSize = new Point3D(this.getSize());
		final Point3D otherCenter = new Point3D(other.getCenter());
		final Point3D otherSize = new Point3D(other.getSize());

		if (thisCenter.getX() + thisSize.getX() / 2
		 >= otherCenter.getX() - otherSize.getX() / 2
		 && thisCenter.getX() - thisSize.getX() / 2
		 <= otherCenter.getX() + otherSize.getX() / 2
		 && thisCenter.getY() + thisSize.getY() / 2
		 >= otherCenter.getY() - otherSize.getY() / 2
		 && thisCenter.getY() - thisSize.getY() / 2
		 <= otherCenter.getY() + otherSize.getY() / 2
		 && thisCenter.getZ() + thisSize.getZ() / 2
		 >= otherCenter.getZ() - otherSize.getZ() / 2
		 && thisCenter.getZ() - thisSize.getZ() / 2
		 <= otherCenter.getZ() + otherSize.getZ() / 2) {
			collision(other);
			other.collision(this);
		}
	}

	/** Used to remove the entity from the Track. */
	public void selfDestruct() {
		final AbstractEntity self = this;
		new Thread(new Runnable() {
			/** Method run in Thread, removes entity after track is done */
			public void run() {
				synchronized (State.getTrack()) {
					State.getTrack().removeEntity(self);
				}
			}
		}).start();
	}

	/**
	 * Called when this entity collides with <code>collidee</code>.
	 * (Called by {@link #checkCollision(AbstractEntity)}.
	 * @param collidee
	 */
	public abstract void collision(AbstractEntity collidee);

	/**
	 * @return the center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(final Point3D center) {
		this.center = center;
	}

	/**
	 * @return the size
	 */
	public Point3D getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(final Point3D size) {
		this.size = size;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractEntity other = (AbstractEntity) obj;
		if (center == null) {
			if (other.center != null) {
				return false;
			}
		} else if (!center.equals(other.center)) {
			return false;
		}
		if (size == null) {
			if (other.size != null) {
				return false;
			}
		} else if (!size.equals(other.size)) {
			return false;
		}
		return true;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractEntity [center=" + center + ", size=" + size + "]";
	}

}
