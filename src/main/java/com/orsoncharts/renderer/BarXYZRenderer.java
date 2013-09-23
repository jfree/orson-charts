/**
 * ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */
package com.orsoncharts.renderer;

import java.awt.Color;
import java.awt.Paint;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;

/**
 * A renderer that draws 3D bars on an {@link XYZPlot}.
 */
public class BarXYZRenderer extends AbstractXYZRenderer implements XYZRenderer {
 
  /**
   * Creates a new default instance.
   */
  public BarXYZRenderer() {
  }

  public Paint getItemPaint(int series, int item) {
    if (series == 0) {
      return Color.RED;
    }
    if (series == 1) {
      return Color.GREEN;
    }
    if (series == 2) {
      return Color.BLUE;
    }
    return Color.GRAY;
  }

  @Override
  public void composeItem(XYZDataset dataset, int series, int item, 
      World world, Dimension3D dimensions, double xOffset, double yOffset, 
      double zOffset) {

    XYZPlot plot = getPlot();
    Axis3D xAxis = plot.getXAxis();
    Axis3D yAxis = plot.getYAxis();
    Axis3D zAxis = plot.getZAxis();
   
    double x = dataset.getX(series, item);
    double z = dataset.getY(series, item);
    double y = dataset.getZ(series, item);
    Paint paint = getItemPaint(series, item);

    double xx = xAxis.translateToWorld(x, dimensions.getWidth());
    double yy = yAxis.translateToWorld(y, dimensions.getHeight());
    double zz = zAxis.translateToWorld(z, dimensions.getDepth());

    double zero = yAxis.translateToWorld(0.0, dimensions.getHeight());
    
    Object3D bar = Object3D.createBar(0.8, xx + xOffset, yy + yOffset, 
            zz + zOffset, zero + yOffset, (Color) paint);
    world.add(bar);
  }

}

