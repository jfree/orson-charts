/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.graphics3d;

/**
 * A collection of utility methods for 3D geometry.
 */
public class Utils3D {
    
    private Utils3D() {
        // no need to instantiate this class
    }
    
    /**
     * Returns the length of the vector v.
     * 
     * @param v  the vector (<code>null</code> not permitted).
     * 
     * @return The length.
     */
    public static double length(Point3D v) {
        return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
    }
    
    /**
     * Returns the scalar product of two vectors.
     * 
     * @param a  vector A (<code>null</code> not permitted).
     * @param b  vector B (<code>null</code> not permitted).
     * 
     * @return The scalar product. 
     */
    public static double scalarprod(Point3D a, Point3D b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;    
    }
    
    /**
     * Returns the normal vector for the plane defined by three points.
     * 
     * @param a  point A (<code>null</code> not permitted).
     * @param b  point B (<code>null</code> not permitted).
     * @param c  point C (<code>null</code> not permitted).
     * 
     * @return The normal vector. 
     */
    public static Point3D normal(Point3D a, Point3D b, Point3D c) {
        double ax = a.x - c.x;
        double ay = a.y - c.y;
        double az = a.z - c.z;
        double bx = b.x - c.x;
        double by = b.y - c.y;
        double bz = b.z - c.z;
        return new Point3D(ay * bz - az * by, az * bx - ax * bz, 
                ax * by - ay * bx);
    }

    /**
     * Returns the angle between the two vectors.
     * 
     * @param a  vector A (<code>null</code> not permitted).
     * @param b  vector B (<code>null</code> not permitted).
     * 
     * @return The (positive) angle in radians. 
     */
    public static double angle(Point3D a, Point3D b) {
        double dp = a.x * b.x + a.y * b.y + a.z * b.z;
        double alen = length(a);
        double blen = length(b);
        double c = dp / (alen * blen);
        // rounding can cause abs(c) to be greater than one, let's sweep that 
        // under the carpet...
        c = Math.max(-1.0, Math.min(1.0, c));
        return Math.acos(c);    
    }

}
