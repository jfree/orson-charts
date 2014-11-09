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

import java.awt.Color;

/**
 * A 3D object that is simply a dot (single vertex).
 */
public class Dot3D extends Object3D {
    
    /**
     * Creates a new {@code Dot3D} instance.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate.
     * @param color  the color ({@code null} not permitted).
     */
    public Dot3D(float x, float y, float z, Color color) {
        super(color);
        addVertex(new Point3D(x, y, z));
    }
    
}
