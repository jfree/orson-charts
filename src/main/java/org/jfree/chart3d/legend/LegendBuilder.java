/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2020, by Object Refinery Limited.  All rights reserved.
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
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package org.jfree.chart3d.legend;

import org.jfree.chart3d.Chart3D;
import org.jfree.chart3d.Orientation;
import org.jfree.chart3d.graphics2d.Anchor2D;
import org.jfree.chart3d.plot.Plot3D;
import org.jfree.chart3d.style.ChartStyle;
import org.jfree.chart3d.table.TableElement;

/**
 * A legend builder is responsible for creating a legend for a chart.  The API
 * has been kept to a minimum intentionally, so as not to overly constrain 
 * developers that want to implement a custom legend builder.  The 
 * {@code get/setItemFont()} methods have been added for convenience
 * because changing the font of the legend item text is a very common 
 * operation.
 * <p>
 * Classes that implement this interface should also implement 
 * {@code java.io.Serializable} if you intend to serialize and deserialize 
 * chart objects.
 * 
 * @see Chart3D#setLegendBuilder(LegendBuilder) 
 */
public interface LegendBuilder {

    /**
     * Creates a legend for the specified plot.  If this method returns 
     * {@code null}, no legend will be displayed.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param anchor  the anchor ({@code null} not permitted).
     * @param orientation  the legend orientation ({@code null} not 
     *         permitted).
     * @param style  the style ({@code null} not permitted).
     * 
     * @return A legend (possibly {@code null}).
     * 
     * @since 1.2
     */
    TableElement createLegend(Plot3D plot, Anchor2D anchor, 
            Orientation orientation, ChartStyle style);

}
