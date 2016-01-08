/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;

/**
 * A source of {@code Color} values for an {@link XYZRenderer}.
 * <p>
 * Classes that implement this interface should also implement 
 * {@code java.io.Serializable}, otherwise it will not be possible to 
 * serialize and deserialize charts that use the non-serializable instance.
 */
public interface XYZColorSource {

    /**
     * Returns the color for one item in the plot.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The color (never {@code null}). 
     */
    Color getColor(int series, int item);
    
    /**
     * Returns the color that represents the specified series.
     * 
     * @param series  the series index.
     * 
     * @return The color (never {@code null}).
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
