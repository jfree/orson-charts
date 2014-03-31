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

package com.orsoncharts.axis;

import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.XYZPlot;

/**
 * An enumeration of the value axis types.  A {@link ValueAxis3D} can be 
 * used in several different plot dimensions.
 * 
 * @since 1.3
 */
public enum ValueAxis3DType {
    
    /** A value axis in a {@link CategoryPlot3D}. */
    VALUE,
    
    /** An x-axis in an {@link XYZPlot}. */
    X, 
    
    /** A y-axis in an {@link XYZPlot}. */
    Y,
    
    /** A z-axis in an {@link XYZPlot}. */
    Z
    
}
