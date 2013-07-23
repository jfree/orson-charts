/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.renderer;

import org.jfree.chart3d.plot.XYZPlot;

/**
 * An abstract base class that can be used to create new XYZRenderer subclasses.
 */
public class AbstractXYZRenderer {

  private XYZPlot plot;
  
  protected AbstractXYZRenderer() {  
  }
  
  public XYZPlot getPlot() {
    return this.plot;
  }
  
  public void setPlot(XYZPlot plot) {
    this.plot = plot;
  }

}
