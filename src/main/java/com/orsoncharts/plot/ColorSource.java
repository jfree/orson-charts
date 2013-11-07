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
 * ({@link StandardColorSource}) is provided.
 */
public interface ColorSource {

    /**
     * Returns the color for one data item in the chart.  We return a 
     * <code>Color</code> rather than a paint, because some manipulations
     * that require a <code>Color</code> instance are done for the shading 
     * during the 3D rendering.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The color.
     */
    Color getColor(Comparable key);
    
    /**
     * Sets the color associated with the specified key.  If the supplied
     * color is <code>null</code>, this will have the effect of clearing any
     * previous setting and reverting to the default color.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param color  the color (<code>null</code> permitted).
     */
    void setColor(Comparable key, Color color);
    
}

