/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.awt.Color;

/**
 * A 3D object that is simply a dot (single vertex).
 */
public class Dot3D extends Object3D {
        
    /** The color. */
    private Color color;
    
    /**
     * Creates a new <code>Dot3D</code> instance.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate.
     * @param color  the color.
     */
    public Dot3D(float x, float y, float z, Color color) {
      addVertex(new Point3D(x, y, z));
      this.color = color;
    }
    
}
