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

package com.orsoncharts.plot;

import java.awt.Color;

/**
 * A color source for use by the {@link PiePlot3D} class.  This is the 
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
    Color getColor(Comparable<?> key);
    
    /**
     * Sets the color associated with the specified key.  If the supplied
     * color is <code>null</code>, this will have the effect of clearing any
     * previous setting and reverting to the default color.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param color  the color (<code>null</code> permitted).
     */
    void setColor(Comparable<?> key, Color color);
    
}

