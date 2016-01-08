/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
     * @param v  the vector ({@code null} not permitted).
     * 
     * @return The length.
     */
    public static double length(Point3D v) {
        return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
    }
    
    /**
     * Returns a new vector that is the normalised form of the specified 
     * vector.
     * 
     * @param v  the vector ({@code null} not permitted).
     * 
     * @return The normalised form of the specified vector.
     * 
     * @since 1.2
     */
    public static Point3D normalise(Point3D v) {
        double length = length(v);
        return new Point3D(v.x / length, v.y / length, v.z / length);
    }
    
    /**
     * Returns the scalar product of two vectors.
     * 
     * @param a  vector A ({@code null} not permitted).
     * @param b  vector B ({@code null} not permitted).
     * 
     * @return The scalar product. 
     */
    public static double scalarprod(Point3D a, Point3D b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;    
    }
    
    /**
     * Returns the normal vector for the plane defined by three points.
     * 
     * @param a  point A ({@code null} not permitted).
     * @param b  point B ({@code null} not permitted).
     * @param c  point C ({@code null} not permitted).
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
     * @param a  vector A ({@code null} not permitted).
     * @param b  vector B ({@code null} not permitted).
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
