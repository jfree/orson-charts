/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.plot;

import org.jfree.chart3d.axis.Axis3D;
import org.jfree.chart3d.data.XYZDataset;
import org.jfree.chart3d.renderer.XYZRenderer;
import org.jfree.graphics3d.World;

/**
 * An XYZ plot.
 */
public class XYZPlot implements Plot3D {

  private XYZDataset dataset;

  private Axis3D xAxis;
  private Axis3D yAxis;
  private Axis3D zAxis;

  private XYZRenderer renderer;

  public XYZPlot(Axis3D xAxis, Axis3D yAxis, Axis3D zAxis) {
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    this.zAxis = zAxis;
  }

  public XYZDataset getDataset() {
    return this.dataset;
  }

  public void setDataset(XYZDataset dataset) {
    this.dataset = dataset;
  }

  public Axis3D getXAxis() {
    return this.xAxis;
  }

  public void setXAxis(Axis3D xAxis) {
    this.xAxis = xAxis;
  }

  public Axis3D getYAxis() {
    return this.yAxis;
  }

  public void setYAxis(Axis3D yAxis) {
    this.yAxis = yAxis;
  }

  public Axis3D getZAxis() {
    return this.zAxis;
  }

  public void setZAxis(Axis3D zAxis) {
    this.zAxis = zAxis;
  }

  public XYZRenderer getRenderer() {
    return this.renderer;
  }

  public void setRenderer(XYZRenderer renderer) {
    this.renderer = renderer;
  }

  public void composeToWorld(World world, double xOffset, double yOffset, double zOffset) {
    // for each data point in the dataset
    // figure out if the composed shape intersects with the visible subset
    // of the world, and if so add the object
    int seriesCount = this.dataset.getSeriesCount();
    for (int s = 0; s < seriesCount; s++) {
      int itemCount = this.dataset.getItemCount(s);
      for (int i = 0; i < itemCount; i++) {
        this.renderer.composeItem(world, this.dataset, s, i, xOffset, yOffset, zOffset);
      }
    }
  }
}
