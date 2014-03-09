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
