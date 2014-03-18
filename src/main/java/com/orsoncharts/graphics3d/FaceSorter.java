/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.util.List;

/**
 * An interface that must be implemented by objects that can sort faces prior
 * to rendering (the Painter's algorithm).
 * 
 * @since 1.3
 */
public interface FaceSorter {

    /**
     * Returns a list of faces in the order that they should be painted.
     * 
     * @param faces  the faces before sorting (<code>null</code> not permitted).
     * @param eyePts  the points in 3D space relative to the viewing position.
     * 
     * @return An ordered list (note that the result may be the same list 
     *     passed in via the <code>faces</code> argument). 
     */
    List<Face> sort(List<Face> faces, Point3D[] eyePts);
    
}
