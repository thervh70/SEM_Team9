package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Ancestor class for all Entities.
 * @author Maarten
 */
public abstract class AbstractEntity {  // NOPMD - states complexity is too high
										// because of the checkCollision method

	/** Center of the bounding box of this Entity. */
	private Point3D center;
	/** Size of the bounding box of this Entity. */
	private Point3D size;

	/**
	 * Default constructor.
	 * @param cent center of the bounding box
	 * @param siz size of the bounding box
	 */
	public AbstractEntity(final Point3D cent, final Point3D siz) {
		super();
		this.center = cent;
		this.size = siz;
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
			/** Method run in Thread,
			 * removes entity after track is done */
			public void run() {
				State.getTrack().removeEntity(self);
			}
		}).start();
	}

	/**
	 * Called when this entity collides with <code>collidee</code>.
	 * (Called by {@link #checkCollision(AbstractEntity)}.
	 * @param collidee Entity that this entity collides with.
	 */
	public abstract void collision(final AbstractEntity collidee);

	/**
	 * @return the center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * @param cent the center to set
	 */
	public void setCenter(final Point3D cent) {
		this.center = cent;
	}

	/**
	 * @return the size
	 */
	public Point3D getSize() {
		return size;
	}

	/**
	 * @param siz the size to set
	 */
	public void setSize(final Point3D siz) {
		this.size = siz;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result *= prime;
		if (center != null) {
			result += center.hashCode();
		}
		result *= prime;
		if (size != null) {
			result += size.hashCode();
		}
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param obj object to compare with
	 * @return whether this is equal to obj
	 */
	@Override
	public boolean equals(final Object obj) { // NOPMD - complexity of generated
		if (this == obj) {                    // equals method is indeed high
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
	 * @return a string representing this AbstractEntity
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [center=" + center + ","
				+ "size=" + size + "]";
	}

}
