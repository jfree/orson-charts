/* =============
 * OrsonCharts3D
 * =============
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import com.orsoncharts.graphics3d.ArgChecks;
import java.util.EventObject;

/**
 * An event indicating some change in the attributes of a chart.  Typically 
 * this indicates that the chart needs to be redrawn.
 */
public class Chart3DChangeEvent extends EventObject {

    private Chart3D chart;
  
    /**
     * Creates a new event.
     * 
     * @param chart  the chart (<code>null</code> not permitted). 
     */
    public Chart3DChangeEvent(Chart3D chart) {
        this(chart, chart);
    }
    
    /**
     * Creates a new event.
     * 
     * @param source  the source.
     * @param chart  the chart (<code>null</code> not permitted).
     */
    public Chart3DChangeEvent(Object source, Chart3D chart) {
        super(source);
        ArgChecks.nullNotPermitted(chart, "chart");
        this.chart = chart;
    }
  
    /**
     * Returns the chart that this event is associated with.
     * 
     * @return The chart (never <code>null</code>). 
     */
    public Chart3D getChart() {
        return this.chart;
    }
}
