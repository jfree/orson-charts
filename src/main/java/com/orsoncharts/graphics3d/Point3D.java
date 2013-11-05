/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

/**
 * A point in 3D space.  Instances of this class are immutable.
 */
public class Point3D {

    /** The origin <code>(0, 0, 0)</code>. */
    public static final Point3D ORIGIN = new Point3D(0, 0, 0);
    
    /** The point <code>(1, 0, 0)</code>. */
    public static final Point3D UNIT_X = new Point3D(1, 0, 0);
    
    /** The point <code>(0, 1, 0)</code>. */
    public static final Point3D UNIT_Y = new Point3D(0, 1, 0);
    
    /** The point <code>(0, 0, 1)</code>. */
    public static final Point3D UNIT_Z = new Point3D(0, 0, 1);
    
    /** The x-coordinate. */
    public double x;
    
    /** The y-coordinate. */
    public double y;
    
    /** The z-coordinate. */
    public double z;
    
    /**
     * Creates a new <code>Point3D</code> instance from spherical coordinates.
     * 
     * @param theta  
     * @param phi
     * @param rho  the distance from the origin.
     * 
     * @return The point (never <code>null</code>).
     */
    public static Point3D createPoint3D(double theta, double phi, double rho) {
        double x = rho * Math.sin(phi) * Math.cos(theta);
        double y = rho * Math.sin(phi) * Math.sin(theta);
        double z = rho * Math.cos(phi);
        return new Point3D(x, y, z);
    }

    /**
     * Creates a new point in 3D space.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate.
     */
    public Point3D(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }

    public double getTheta() {
        return Math.atan2(y, x);
    }
    
    public double getPhi() {
        return Math.acos(z / getRho());        
    }
    
    public double getRho() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    /**
     * Returns a string representation of this instance, primarily for 
     * debugging purposes.
     * 
     * @return 
     */
    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
  
}
