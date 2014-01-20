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

package com.orsoncharts.renderer.category;

import java.awt.Color;

/**
 * A color source that can supply the colors for category plots.  This is the 
 * interface through which the renderer will obtain colors for each data item 
 * in the chart.  A default implementation 
 * ({@link StandardCategoryColorSource}) is provided and you can customise 
 * the rendering colors by providing an alternate implementation.
 */
public interface CategoryColorSource {

    /**
     * Returns the color for one data item in the chart.  We return a 
     * <code>Color</code> rather than a paint, because some manipulations
     * are done for the shading during the 3D rendering.
     * 
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * 
     * @return The color.
     */
    Color getColor(int series, int row, int column);
  
    /**
     * Returns the color to be used in the legend to represent the specified
     * series.
     * 
     * @param series  the series index.
     * 
     * @return The color. 
     */
    Color getLegendColor(int series);

    /**
     * Restyles the source using the specified colors.  Refer to the 
     * implementing class to confirm the precise behaviour (typically all 
     * existing color settings are cleared and the specified colors are 
     * installed as the new defaults).
     * 
     * @param colors  the colors.
     * 
     * @since 1.2
     */
    void style(Color... colors);

}

