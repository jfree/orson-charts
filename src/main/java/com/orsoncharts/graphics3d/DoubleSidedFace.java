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
 * A double-sided face.  This is identical to a normal face except that during
 * rendering these faces will be drawn no matter which side they are viewed
 * from.
 * 
 * @since 1.1
 */
public class DoubleSidedFace extends Face {
 
    /**
     * Creates a new double-sided face.
     * 
     * @param vertices  the vertices.
     * @param color  the color.
     * @param outline  draw the outline?
     */
    public DoubleSidedFace(int[] vertices, Color color, boolean outline) {
        super(vertices, color, outline); 
    }
}
