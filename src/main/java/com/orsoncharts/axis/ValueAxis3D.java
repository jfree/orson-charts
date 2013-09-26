/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import com.orsoncharts.plot.XYZPlot;

/**
 * A value axis.
 */
public interface ValueAxis3D extends Axis3D {
    
    /**
     * Configure the axis as an x-axis for the specified plot.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    void configureAsXAxis(XYZPlot plot);

    /**
     * Configure the axis as an y-axis for the specified plot.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    void configureAsYAxis(XYZPlot plot);
    
    /**
     * Configure the axis as an z-axis for the specified plot.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    void configureAsZAxis(XYZPlot plot);
 
}
