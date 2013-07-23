/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.renderer;

import org.jfree.chart3d.plot.XYZPlot;

/**
 * An abstract base class that can be used to create new {@link XYZRenderer}
 * subclasses.
 */
public class AbstractXYZRenderer {

  private XYZPlot plot;
  
  protected AbstractXYZRenderer() {  
  }
  
  /**
   * Returns the plot that the renderer is assigned to, if any.
   * 
   * @return The plot (possibly <code>null</code>). 
   */
  public XYZPlot getPlot() {
    return this.plot;
  }
  
  /**
   * Sets the plot that the renderer is assigned to.
   * 
   * @param plot  the plot (<code>null</code> permitted). 
   */
  public void setPlot(XYZPlot plot) {
    this.plot = plot;
  }

}
