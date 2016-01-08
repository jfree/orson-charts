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

package com.orsoncharts.axis;

import java.util.List;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.marker.CategoryMarker;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * An axis that displays categories and is used with a {@link CategoryPlot3D}
 * for the row and column axes.  Most of the methods in this interface are 
 * intended to be called by the plot that the axis is assigned to, they won't 
 * normally be called by external code.
 */
public interface CategoryAxis3D extends Axis3D {
    
    /**
     * Returns {@code true} if this axis is being used as the row axis
     * in a plot, and {@code false} otherwise.
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    boolean isRowAxis();
    
    /**
     * Returns {@code true} if this axis is being used as the column axis
     * in a plot, and {@code false} otherwise.
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    boolean isColumnAxis();
    
    /**
     * Configure the axis as a row axis for the specified plot.  Note that this
     * method will be called by the plot, it will not normally be called by 
     * external code.
     * 
     * @param plot  the plot ({@code null} not permitted). 
     */
    void configureAsRowAxis(CategoryPlot3D plot);
    
    /**
     * Configure the axis as a column axis for the specified plot.  Note that 
     * this method will be called by the plot, it will not normally be called 
     * by external code.
     * 
     * @param plot  the plot ({@code null} not permitted). 
     */
    void configureAsColumnAxis(CategoryPlot3D plot);

    /**
     * Returns the width of a single category in units corresponding to 
     * the current axis range.
     * 
     * @return The width of a single category. 
     */
    double getCategoryWidth();
    
    /**
     * Returns the numerical value along the axis that corresponds to the
     * specified category.  If the category is unknown, this method will
     * return {@code Double.NaN}.
     * 
     * @param category  the category ({@code null} not permitted).
     * 
     * @return The axis value. 
     */
    double getCategoryValue(Comparable<?> category);
    
    /**
     * Generates the tick data for the axis (assumes the axis is being used
     * as the row axis).  The dataset is passed as an argument to provide the 
     * opportunity to incorporate dataset-specific info into tick labels (for 
     * example, a row label might show the total for that row in the dataset)
     * ---whether or not this is used depends on the axis implementation.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The tick data.
     * 
     * @since 1.2
     */
    List<TickData> generateTickDataForRows(CategoryDataset3D dataset);

    /**
     * Generates the tick data for the axis (assumes the axis is being used
     * as the row axis).  The dataset is passed as an argument to provide the 
     * opportunity to incorporate dataset-specific info into tick labels (for 
     * example, a row label might show the total for that row in the dataset)
     * ---whether or not this is used depends on the axis implementation.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The tick data.
     * 
     * @since 1.2
     */
    List<TickData> generateTickDataForColumns(CategoryDataset3D dataset);

    /** 
     * Returns a list of marker data instances for the markers that fall
     * within the current axis range.
     * 
     * @return A list of marker data. 
     */
    List<MarkerData> generateMarkerData();
    
    /**
     * Returns the marker with the specified key, if there is one.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The marker (possibly {@code null}). 
     * 
     * @since 1.2
     */
    CategoryMarker getMarker(String key);
}
