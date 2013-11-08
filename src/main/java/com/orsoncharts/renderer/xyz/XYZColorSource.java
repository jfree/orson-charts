/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;

/**
 * A source of <code>Color</code> values for an {@link XYZRenderer}.
 * <p>
 * Classes that implement this interface should also implement 
 * <code>java.io.Serializable</code>, otherwise it will not be possible to 
 * serialize and deserialize charts that use the non-serializable instance.
 */
public interface XYZColorSource {

    /**
     * Returns the color for one item in the plot.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The color (never <code>null</code>). 
     */
    Color getColor(int series, int item);
    
    /**
     * Returns the color that represents the specified series.
     * 
     * @param series  the series index.
     * 
     * @return The color (never <code>null</code>).
     */
    Color getLegendColor(int series);

}
