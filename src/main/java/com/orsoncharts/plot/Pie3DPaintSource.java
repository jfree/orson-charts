/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import java.awt.Color;

/**
 * A paint source for use by the {@link PiePlot3D} class.  This is the 
 * interface through which the plot will obtain colors for each data item 
 * (segment) in the chart.  A default implementation
 * ({@link StandardPie3DPaintSource}) is provided.
 */
public interface Pie3DPaintSource {

    /**
     * Returns the color for one data item in the chart.  We return a 
     * <code>Color</code> rather than a paint, because some manipulations
     * are done for the shading during the 3D rendering.
     * 
     * @param item  the item index.
     * 
     * @return The color.
     */
    public Color getPaint(int item);
  
    /**
     * Returns the color to be used in the legend to represent the specified
     * series.
     * 
     * @param series  the series index.
     * 
     * @return The color. 
     */
    public Color getLegendPaint(int series);
    
}

