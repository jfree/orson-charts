/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.renderer;

import com.orsoncharts.plot.XYZPlot;

/**
 * An abstract base class that can be used to create new {@link XYZRenderer}
 * subclasses.
 */
public class AbstractXYZRenderer {

    private XYZPlot plot;
  
    private XYZPaintSource paintSource;
  
    /**
     * Creates a new default instance.
     */
    protected AbstractXYZRenderer() {
        this.paintSource = new DefaultXYZPaintSource();
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

  public XYZPaintSource getPaintSource() {
      return this.paintSource;
  }
}
