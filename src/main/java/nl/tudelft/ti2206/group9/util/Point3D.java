package nl.tudelft.ti2206.group9.util;

/**
 * Utility class, just a simple 3 dimensional point.
 *
 * @author Maarten
 */
public class Point3D {

    /** x-coordinate of point. */
    private double x;
    /** y-coordinate of point. */
    private double y;
    /** z-coordinate of point. */
    private double z;

    /** Equivalent to Point3D(0, 0, 0). */
    public static final Point3D ZERO = new Point3D(0, 0, 0);
    /** Equivalent to Point3D(1, 0, 0). */
    public static final Point3D UNITX = new Point3D(1, 0, 0);
    /** Equivalent to Point3D(0, 1, 0). */
    public static final Point3D UNITY = new Point3D(0, 1, 0);
    /** Equivalent to Point3D(0, 0, 1). */
    public static final Point3D UNITZ = new Point3D(0, 0, 1);
    /** Equivalent to Point3D(1, 1, 1). */
    public static final Point3D UNITCUBE = new Point3D(1, 1, 1);

    /**
     * Constructs a new Point in 3D-space.
     * @param xSet x-coordinate
     * @param ySet y-coordinate
     * @param zSet z-coordinate
     */
    public Point3D(final double xSet, final double ySet,
                   final double zSet) {
        this.x = xSet;
        this.y = ySet;
        this.z = zSet;
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
     * @param add the amount of units to add to x
     */
    public final void addX(final double add) {
        this.x += add;
    }

    /**
     * @param add the amount of units to add to y
     */
    public final void addY(final double add) {
        this.y += add;
    }

    /**
     * @param add the amount of units to add to z
     */
    public final void addZ(final double add) {
        this.z += add;
    }

    /**
     * @return the x
     */
    public final double getX() {
        return x;
    }

    /**
     * @param newX the x to set
     */
    public final void setX(final double newX) {
        this.x = newX;
    }

    /**
     * @return the y
     */
    public final double getY() {
        return y;
    }

    /**
     * @param newY the y to set
     */
    public final void setY(final double newY) {
        this.y = newY;
    }

    /**
     * @return the z
     */
    public final double getZ() {
        return z;
    }

    /**
     * @param newZ the z to set
     */
    public final void setZ(final double newZ) {
        this.z = newZ;
    }

    /**
     * @param point the ponit to set
     */
    public final void set(final Point3D point) {
        x = point.x;
        y = point.y;
        z = point.z;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return hashCode
     */
    @Override
    public final int hashCode() {
        final int shift = 32;
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> shift));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> shift));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> shift));
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj Object to compare with.
     * @return whether this Point3D is equal to obj.
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point3D other = (Point3D) obj;
        if (Double.doubleToLongBits(x)
                != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y)
                != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(z) //NOPMD - equals is generated this way
                != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     * @return a String representing this Point3D in "(x, y, z)" format.
     */
    @Override
    public final String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
