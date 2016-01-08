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

package com.orsoncharts.interaction;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.graphics3d.RenderedElement;

/**
 * A mouse event for a chart that is displayed in a {@link Chart3DPanel}.
 *
 * @see Chart3DMouseListener
 * 
 * @since 1.3
 */
public class Chart3DMouseEvent extends EventObject implements Serializable {

    /** The chart that the mouse event relates to. */
    private Chart3D chart;

    /** The Java mouse event that triggered this event. */
    private MouseEvent trigger;

    /** The chart element (if any). */
    private RenderedElement element;

    /**
     * Constructs a new event.
     *
     * @param chart  the source chart ({@code null} not permitted).
     * @param trigger  the mouse event that triggered this event
     *                 ({@code null} not permitted).
     * @param element  the element (if any) under the mouse pointer
     *                ({@code null} permitted).
     */
    public Chart3DMouseEvent(Chart3D chart, MouseEvent trigger,
                             RenderedElement element) {
        super(chart);
        this.chart = chart;
        this.trigger = trigger;
        this.element = element;
    }

    /**
     * Returns the chart that the mouse event relates to.
     *
     * @return The chart (never {@code null}).
     */
    public Chart3D getChart() {
        return this.chart;
    }

    /**
     * Returns the mouse event that triggered this event.
     *
     * @return The event (never {@code null}).
     */
    public MouseEvent getTrigger() {
        return this.trigger;
    }

    /**
     * Returns the interactive element (if any) under the mouse point.
     *
     * @return The chart entity (possibly {@code null}).
     */
    public RenderedElement getElement() {
        return this.element;
    }

}

