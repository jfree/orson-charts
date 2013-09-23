/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.plot;

import java.util.EventObject;
import com.orsoncharts.plot.Plot3D;

/**
 * An event used to signal a change to a {@link Plot3D}.
 */
public class Plot3DChangeEvent extends EventObject {

  private Plot3D plot;
  
  public Plot3DChangeEvent(Object source, Plot3D plot) {
    super(source);
    this.plot = plot;
  }
 
  public Plot3D getPlot() {
    return this.plot;
  }
}
