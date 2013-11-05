/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import java.awt.Color;

// 255, 61, 81
// 81, 204,107
// 73, 151, 214
// 255, 163, 74
// 171, 88, 171
// 220, 104, 78
// 232, 112, 177


// Colorseed
// 228, 233, 239
// 184, 197, 219
// 111, 122, 143
// 95, 89, 89
// 206, 167, 145
// 188, 182, 173

/**
 * A paint source for use by the {@link PiePlot3D} class.  This is the 
 * interface through which the plot will obtain colors for each data item 
 * (segment) in the chart.  A default implementation
 * ({@link StandardPaintSource}) is provided.
 */
public interface ColorSource {

    /**
     * Returns the color for one data item in the chart.  We return a 
     * <code>Color</code> rather than a paint, because some manipulations
     * are done for the shading during the 3D rendering.
     * 
     * @param item  the item index.
     * 
     * @return The color.
     */
    public Color getColor(Comparable key);
    
    public void setColor(Comparable key, Color color);
    
}

