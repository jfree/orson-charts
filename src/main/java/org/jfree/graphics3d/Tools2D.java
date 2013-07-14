/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d;

import java.awt.geom.Point2D;

/**
 * A collection of utility methods for 2D geometry.  These methods draw upon
 * the algorithms and code in "Computer Graphics For Java Programmers 
 * (2nd Edition)" by Leen Ammeraal and Kang Zhang.
 */
public class Tools2D {
    
    /**
     * Calculates twice the area of a triangle for points specified in 
     * counter-clockwise order (if the points are specified in clockwise order
     * the result will be negative).
     * 
     * @param a  the first point.
     * @param b  the second point.
     * @param c  the third point.
     * 
     * @return The area x 2.
     */
    public static double area2(Point2D a, Point2D b, Point2D c) {
        double ax = a.getX();
        double ay = a.getY();
        double bx = b.getX();
        double by = b.getY();
        double cx = c.getX();
        double cy = c.getY();
        return (ax - cx) * (by - cy) - (ay - cy) * (bx - cx);
    }
    
    /**
     * Tests whether point <code>p</code> is inside the given triangle (the
     * points of the triangle must be specified in counter-clockwise order).
     * 
     * @param a  the first point.
     * @param b  the second point.
     * @param c  the third point.
     * @param p  the point to test.
     * 
     * @return A boolean.
     */
    static boolean insideTriangle(Point2D a, Point2D b, Point2D c, 
            Point2D p) {  
        return Tools2D.area2(a, b, p) >= 0 && Tools2D.area2(b, c, p) >= 0 
                && Tools2D.area2(c, a, p) >= 0;
    }
    
    static Triangle2D[] triangulate(Point2D[] polygon) {
        return triangulate(polygon, null);
    }
    
    static Triangle2D[] triangulate(Point2D[] polygon, Triangle2D[] result) {
        if (result == null) {
            result = new Triangle2D[polygon.length - 2];
        }
        else {
            if (result.length != polygon.length - 2) {
                throw new IllegalArgumentException(
                        "The result array must have length " 
                        + (polygon.length - 2));
            }
        }
        int vertexCount = polygon.length;
        int j = vertexCount - 1;
        int iA = 0; 
        int iB = 0;
        int iC = 0;
        int[] next = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            next[j] = i;
            j = i;
        }
        for (int k = 0; k < vertexCount - 2; k++) {
            Point2D a, b, c;
            boolean triangleFound = false;
            int count = 0;
            while (!triangleFound && ++count < vertexCount) {
                iB = next[iA];
                iC = next[iB];
                a = polygon[iA];
                b = polygon[iB];
                c = polygon[iC];
                if (Tools2D.area2(a, b, c) >= 0) {
                    j = next[iC];
                    while (j != iA && (!insideTriangle(a, b, c, polygon[j]))) {
                        j = next[j];
                    }
                    if (j == iA) {
                        // triangle ABC contains no other vertex
                        result[k] = new Triangle2D(a, b, c);
                        next[iA] = iC;
                        triangleFound = true;
                    }
                }
                iA = next[iA];
            }
            if (count == vertexCount) {
                throw new RuntimeException("Polygon error.");
            }
        }
        return result;
    }
    
    public static Point2D centrePoint(Point2D pt0, Point2D pt1, Point2D pt2, Point2D pt3) {
      float x = (float) ((pt0.getX() + pt1.getX() + pt2.getX() + pt3.getX()) / 4.0);
      float y = (float) ((pt0.getY() + pt1.getY() + pt2.getY() + pt3.getY()) / 4.0);
      return new Point2D.Float(x, y);
    }
}
