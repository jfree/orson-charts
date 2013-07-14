/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.renderer;

import org.jfree.chart3d.data.XYZDataset;
import org.jfree.graphics3d.World;

public interface XYZRenderer {
    
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
  public void composeItem(World world, XYZDataset dataset, int series, int item,
          double xOffset, double yOffset, double zOffset);

}
