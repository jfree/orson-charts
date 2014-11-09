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
 * A line between two points in 3D space.
 * 
 * @since 1.5
 */
public class Line3D extends Object3D {
    
    /**
     * Creates a new {@code Line3D} instance.
     * 
     * @param x0  the x-coordinate for the start of the line.
     * @param y0  the y-coordinate for the start of the line.
     * @param z0  the z-coordinate for the start of the line.
     * @param x1  the x-coordinate for the end of the line.
     * @param y1  the y-coordinate for the end of the line.
     * @param z1  the z-coordinate for the end of the line.
     * @param color  the color ({@code null} not permitted).
     */
    public Line3D(float x0, float y0, float z0, float x1, float y1, float z1, 
            Color color) {
        super(color);
        addVertex(x0, y0, z0);
        addVertex(x1, y1, z1);
        addFace(new Face(this, new int[] {0, 1}));
    }

}
