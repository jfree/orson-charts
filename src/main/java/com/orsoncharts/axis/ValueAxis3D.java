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

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.marker.ValueMarker;

/**
 * An axis that displays a range of continuous values.  These can be used
 * for the value axis in a {@link CategoryPlot3D}, and for the X, Y or Z
 * axes in an {@link XYZPlot}.
 */
public interface ValueAxis3D extends Axis3D {

    /**
     * Returns the type of use that the axis has been configured for.
     * 
     * @return The type (or {@code null} if the axis has not yet been 
     *     configured).
     * 
     * @since 1.3
     */
    ValueAxis3DType getConfiguredType();
    
    /**
     * Configure the axis as a value axis for the specified plot.
     * 
     * @param plot  the plot ({@code null} not permitted). 
     */
    void configureAsValueAxis(CategoryPlot3D plot);
    
    /**
     * Configure the axis as an x-axis for the specified plot.
     * 
     * @param plot  the plot ({@code null} not permitted). 
     */
    void configureAsXAxis(XYZPlot plot);

    /**
     * Configure the axis as an y-axis for the specified plot.
     * 
     * @param plot  the plot ({@code null} not permitted). 
     */
    void configureAsYAxis(XYZPlot plot);
    
    /**
     * Configure the axis as an z-axis for the specified plot.
     * 
     * @param plot  the plot ({@code null} not permitted). 
     */
    void configureAsZAxis(XYZPlot plot);
 
    /**
     * Selects an appropriate tick size and format for the axis based on
     * the axis being rendered from {@code pt0} to {@code pt1}.
     * 
     * @param g2  the graphics target.
     * @param pt0  the starting point.
     * @param pt1  the ending point.
     * @param opposingPt  a point on the opposite side of the axis from the 
     *     labels.
     * 
     * @return The tick size.
     */
    double selectTick(Graphics2D g2, Point2D pt0, Point2D pt1, 
            Point2D opposingPt);
    
    /**
     * Generates a list of tick data items for the specified tick unit.  This
     * data will be passed to the 3D engine and will be updated with a 2D
     * projection that can later be used to write the axis tick labels in the
     * appropriate places.
     * <br><br>
     * If {@code tickUnit} is {@code Double.NaN}, then tick data is
     * generated for just the bounds of the axis.
     * 
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick data (never {@code null}). 
     */
    List<TickData> generateTickData(double tickUnit);
    
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
    ValueMarker getMarker(String key);

}
