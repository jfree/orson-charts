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

package com.orsoncharts.legend;

import java.awt.Shape;
import java.util.Map;
import java.awt.Color;
import com.orsoncharts.plot.Plot3D;

/**
 * Information about an item (typically a data series) that can be displayed
 * in a legend.  All plots will return a list of these, to be used in the
 * construction of a chart legend, via the {@link Plot3D#getLegendInfo()}
 * method.
 */
public interface LegendItemInfo {
    
    /**
     * Returns the series key.
     * 
     * @return The series key (never {@code null}). 
     */
    Comparable<?> getSeriesKey();
    
    /**
     * Returns the series label that will be displayed in the legend.  Very
     * often this is the same as {@code getSeriesKey().toString()}, but 
     * here we have the option to provide some alternative text.
     * 
     * @return The label (never {@code null}).
     */
    String getLabel();

    /**
     * Returns a longer description of the series (this could be used in 
     * tooltips, for example).
     * 
     * @return The description (possibly {@code null}). 
     */
    String getDescription();
    
    /**
     * Returns the shape used to represent the series in the legend.  This
     * may be {@code null}, in which case the legend builder should
     * use a default shape.
     * 
     * @return The shape (possibly {@code null}). 
     */
    Shape getShape();

    /**
     * Returns the color used to represent a series.
     * 
     * @return The color (never {@code null}).
     */
    Color getColor();
    
    /**
     * A map containing other properties for the legend item.  Not currently
     * used, but available for future expansion.
     * 
     * @return A map (never {@code null}). 
     */
    Map<Comparable<?>, Object> getProperties();
    
}
