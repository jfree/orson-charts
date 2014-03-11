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
     * @param owner  the object that the face belongs to (<code>null</code> not 
     *     permitted).
     * @param vertices  the vertices.
     * 
     * @since 1.3
     */
    public DoubleSidedFace(Object3D owner, int[] vertices) {
        super(owner, vertices); 
    }
}
