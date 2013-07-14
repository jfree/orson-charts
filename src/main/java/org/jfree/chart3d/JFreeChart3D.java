/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d;

import org.jfree.chart3d.plot.Plot3D;
import org.jfree.graphics3d.ArgChecks;

/**
 * A chart object for 3D charts.
 */
public class JFreeChart3D {

  Plot3D plot;

  /**
   * Creates a 3D chart for the specified plot.
   * 
   * @param plot 
   */
  public JFreeChart3D(Plot3D plot) {
    ArgChecks.nullNotPermitted(plot, "plot");
    this.plot = plot;
  }

  /**
   * Returns the plot.
   *
   * @return The plot.
   */
  public Plot3D getPlot() {
    return this.plot;
  }

}
