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

package com.orsoncharts.style;

import java.util.EventObject;

import com.orsoncharts.util.ArgChecks;

/**
 * An event that signals a change to a {@link ChartStyle}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @since 1.2
 */
@SuppressWarnings("serial")
public class ChartStyleChangeEvent extends EventObject {

    private ChartStyle style;
  
    /**
     * Creates a new event.
     * 
     * @param style  the style ({@code null} not permitted). 
     */
    public ChartStyleChangeEvent(ChartStyle style) {
        this(style, style);
    }
    
    /**
     * Creates a new event.
     * 
     * @param source  the source.
     * @param style  the style ({@code null} not permitted).
     */
    public ChartStyleChangeEvent(Object source, ChartStyle style) {
        super(source);
        ArgChecks.nullNotPermitted(style, "style");
        this.style = style;
    }
  
    /**
     * Returns the chart style that this event is associated with.
     * 
     * @return The style (never {@code null}). 
     */
    public ChartStyle getChartStyle () {
        return this.style;
    }
    
}
