/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.renderer;

import org.jfree.chart3d.data.XYZDataset;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.graphics3d.Dimension3D;
import org.jfree.graphics3d.World;

public interface XYZRenderer {

  /**
   * Returns the plot that this renderer is assigned to.
   * 
   * @return The plot (possibly <code>null</code>). 
   */
  public XYZPlot getPlot();
  
  /**
   * Sets the plot that the renderer is assigned to.
   * 
   * @param plot  the plot (<code>null</code> permitted). 
   */
  public void setPlot(XYZPlot plot);
  
  /**
   * Constructs and places one item from the specified dataset into the given 
   * world.
   * 
   * @param world
   * @param dataset
   * @param series
   * @param item
   * @param xOffset
   * @param yOffset
   * @param zOffset 
   */
  public void composeItem(XYZDataset dataset, int series, int item, World world,
          Dimension3D dimensions, double xOffset, double yOffset, 
          double zOffset);

}
