/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d;

import java.awt.geom.Point2D;

/**
 * Represents a triangle in 2D space.
 */
public class Triangle2D {

    public Point2D v0;
    
    public Point2D v1;
    
    public Point2D v2;
    
    public Triangle2D(Point2D v0, Point2D v1, Point2D v2) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
    }
    
}
