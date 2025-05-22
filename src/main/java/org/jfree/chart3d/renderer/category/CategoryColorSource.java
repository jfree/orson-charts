/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.renderer.category;

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
     * {@code Color} rather than a paint, because some manipulations
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

