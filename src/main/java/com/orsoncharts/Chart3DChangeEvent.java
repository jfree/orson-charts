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

package com.orsoncharts;

import java.util.EventObject;

import com.orsoncharts.util.ArgChecks;

/**
 * An event indicating some change in the attributes of a chart.  Typically 
 * this indicates that the chart needs to be redrawn.  Any object that 
 * implements the {@link Chart3DChangeListener} interface can register
 * with a chart to receive change event notification.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class Chart3DChangeEvent extends EventObject {

    /** The chart that is the source of the event. */
    private Chart3D chart;
  
    /**
     * Creates a new event.
     * 
     * @param chart  the chart that is the source of the event ({@code null} 
     *     not permitted). 
     */
    public Chart3DChangeEvent(Chart3D chart) {
        this(chart, chart);
    }
    
    /**
     * Creates a new event.
     * 
     * @param source  the source.
     * @param chart  the chart that is the source of the event ({@code null} 
     *     not permitted).
     */
    public Chart3DChangeEvent(Object source, Chart3D chart) {
        super(source);
        ArgChecks.nullNotPermitted(chart, "chart");
        this.chart = chart;
    }
  
    /**
     * Returns the chart that this event is associated with.
     * 
     * @return The chart (never {@code null}). 
     */
    public Chart3D getChart() {
        return this.chart;
    }
}
