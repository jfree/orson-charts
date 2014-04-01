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

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * A face sorter that orders the faces by z-value.
 * 
 * @since 1.3
 */
public class StandardFaceSorter implements FaceSorter, Serializable {

    /**
     * Creates a new instance.
     */
    public StandardFaceSorter() {
        // nothing to do    
    }
    
    @Override
    public List<Face> sort(List<Face> faces, Point3D[] eyePts) {
        Collections.sort(faces, new ZOrderComparator(eyePts));
        return faces;
    }
    
}
