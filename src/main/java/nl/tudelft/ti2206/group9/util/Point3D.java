package nl.tudelft.ti2206.group9.util;

/**
 * Utility class, just a simple 3 dimensional point.
 * 
 * @author Maarten
 */
public class Point3D {

	/** x-coordinate of point */
	private double x;
	/** y-coordinate of point */
	private double y;
	/** z-coordinate of point */
	private double z;

	/** Equivalent to Point3D(0, 0, 0) */
	public static final Point3D ZERO = new Point3D(0, 0, 0);
	/** Equivalent to Point3D(1, 0, 0) */
	public static final Point3D UNITX = new Point3D(1, 0, 0);
	/** Equivalent to Point3D(0, 1, 0) */
	public static final Point3D UNITY = new Point3D(0, 1, 0);
	/** Equivalent to Point3D(0, 0, 1) */
	public static final Point3D UNITZ = new Point3D(0, 0, 1);
	/** Equivalent to Point3D(1, 1, 1) */
	public static final Point3D UNITCUBE = new Point3D(1, 1, 1);
	
	/**
	 * Constructs a new Point in 3D-space.
	 * @param xSet x-coordinate
	 * @param y y-coordinate
	 * @param z z-coordinate
	 */
	public Point3D(final double xSet, final double y, final double z) {
		this.x = xSet;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Constructs a new Point in 3D-space using the coordinates of another
	 * Point3D.
	 * @param point the Point to copy the coordinates from.
	 */
	public Point3D(final Point3D point) {
		set(point);
	}
	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(final double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(final double y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(final double z) {
		this.z = z;
	}
	
	/**
	 * @param point the ponit to set
	 */
	public void set(final Point3D point) {
		x = point.x;
		y = point.y;
		z = point.z;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point3D (" + x + ", " + y + ", " + z + ")";
	}
	
}
