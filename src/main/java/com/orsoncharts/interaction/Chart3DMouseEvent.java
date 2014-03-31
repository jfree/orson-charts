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
     * @param chart  the source chart (<code>null</code> not permitted).
     * @param trigger  the mouse event that triggered this event
     *                 (<code>null</code> not permitted).
     * @param element  the element (if any) under the mouse pointer
     *                (<code>null</code> permitted).
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
     * @return The chart (never <code>null</code>).
     */
    public Chart3D getChart() {
        return this.chart;
    }

    /**
     * Returns the mouse event that triggered this event.
     *
     * @return The event (never <code>null</code>).
     */
    public MouseEvent getTrigger() {
        return this.trigger;
    }

    /**
     * Returns the interactive element (if any) under the mouse point.
     *
     * @return The chart entity (possibly <code>null</code>).
     */
    public RenderedElement getElement() {
        return this.element;
    }

}

