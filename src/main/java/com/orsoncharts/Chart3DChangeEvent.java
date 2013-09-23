/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts;

import java.util.EventObject;
import com.orsoncharts.Chart3D;

/**
 * An event indicating some change in the attributes of a chart.  Typically 
 * this indicates that the chart needs to be redrawn.
 */
public class Chart3DChangeEvent extends EventObject {

  private Chart3D chart;
  
  public Chart3DChangeEvent(Object source, Chart3D chart) {
    super(source);
    this.chart = chart;
  }
  
  public Chart3D getChart() {
    return this.chart;
  }
}
