/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.renderer;

import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart3d.data.XYZDataset;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.graphics3d.Dimension3D;
import org.jfree.graphics3d.Object3D;
import org.jfree.graphics3d.World;

/**
 * A renderer that draws 3D bars on an {@link XYZPlot}.
 */
public class BarXYZRenderer extends AbstractXYZRenderer implements XYZRenderer {
    
  public BarXYZRenderer() {

}

  public Paint getItemPaint(int series, int item) {
    if (series == 0)
      return Color.RED;
    if (series == 1)
        return Color.GREEN;
    if (series == 2)
        return Color.BLUE;
    return Color.GRAY;
  }

  @Override
  public void composeItem(XYZDataset dataset, int series, int item, 
      World world, Dimension3D dimensions, double xOffset, double yOffset, 
      double zOffset) {
    double x = dataset.getX(series, item);
    double y = dataset.getY(series, item);
    double z = dataset.getZ(series, item);
    Paint paint = getItemPaint(series, item);
    Object3D cube = Object3D.createBar(1.0, x + xOffset, y + yOffset, z + zOffset, (Color) paint);
    world.add(cube);
  }

}

