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
import com.orsoncharts.util.ArgChecks;

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
        ArgChecks.nullNotPermitted(color, "color");
        addVertex(new Point3D(x, y, z));
        this.color = color;
    }
    
    /**
     * Returns the color that was specified via the constructor.
     * 
     * @return The color (never <code>null</code>). 
     */
    public Color getColor() {
        return this.color;
    }
    
}
