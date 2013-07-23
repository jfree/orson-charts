/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.plot;

import org.jfree.chart3d.axis.Axis3D;
import org.jfree.chart3d.axis.NumberAxis3D;
import org.jfree.chart3d.data.XYZDataset;
import org.jfree.chart3d.renderer.XYZRenderer;
import org.jfree.graphics3d.Dimension3D;
import org.jfree.graphics3d.World;

/**
 * An XYZ plot.
 */
public class XYZPlot implements Plot3D {

  private XYZDataset dataset;

  /** 
   * The plot dimensions defines the size of the plot in world coordinate
   * space.  It should never be null.
   */
  private Dimension3D dimensions;
  
  private Axis3D xAxis;
  private Axis3D yAxis;
  private Axis3D zAxis;

  private XYZRenderer renderer;

  public XYZPlot(NumberAxis3D xAxis, NumberAxis3D yAxis, NumberAxis3D zAxis) {
    this.dimensions = new Dimension3D(10, 10, 10);
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    this.zAxis = zAxis;
  }

  /**
   * Returns the dimensions for the plot in the 3D world in which it will 
   * be composed.
   * 
   * @return The dimensions (never <code>null</code>). 
   */
  @Override
  public Dimension3D getDimensions() {
    return this.dimensions;
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

  /**
   * Returns the renderer for the plot.
   * 
   * @return The renderer (possibly <code>null</code>).
   */
  public XYZRenderer getRenderer() {
    return this.renderer;
  }

  /**
   * Sets the renderer for the plot.
   * 
   * @param renderer  the renderer (<code>null</code> permitted). 
   */
  public void setRenderer(XYZRenderer renderer) {
    this.renderer = renderer;
    if (this.renderer != null) {
      this.renderer.setPlot(this);
    }
  }

  @Override
  public void composeToWorld(World world, double xOffset, double yOffset, double zOffset) {
    // for each data point in the dataset
    // figure out if the composed shape intersects with the visible subset
    // of the world, and if so add the object
    int seriesCount = this.dataset.getSeriesCount();
    for (int series = 0; series < seriesCount; series++) {
      int itemCount = this.dataset.getItemCount(series);
      for (int item = 0; item < itemCount; item++) {
        this.renderer.composeItem(this.dataset, series, item, world, 
            this.dimensions, xOffset, yOffset, zOffset);
      }
    }
  }

}
