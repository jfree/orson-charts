/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;

/**
 * A source of <code>Color</code> values for an {@link XYZRenderer}.
 */
public interface XYZPaintSource {

    /**
     * Returns the color for one item in the plot.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The color (never <code>null</code>). 
     */
    public Color getPaint(int series, int item);
    
    /**
     * Returns the color that represents the specified series.
     * 
     * @param series  the series index.
     * 
     * @return The color (never <code>null</code>).
     */
    public Color getLegendPaint(int series);

}
