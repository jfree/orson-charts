/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.plot;

import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.data.XYZDataset;
import com.orsoncharts.renderer.XYZRenderer;
import com.orsoncharts.graphics3d.ArgChecks;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;

/**
 * An XYZ plot.
 */
public class XYZPlot extends AbstractPlot3D {

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

  /**
   * Creates a new plot with the specified axes.
   * 
   * @param dataset  the dataset (<code>null</code> not permitted).
   * @param xAxis  the x-axis (<code>null</code> not permitted).
   * @param yAxis  the y-axis (<code>null</code> not permitted).
   * @param zAxis  the z-axis (<code>null</code> not permitted).
   */
  public XYZPlot(XYZDataset dataset, Axis3D xAxis, Axis3D yAxis, Axis3D zAxis) {
    ArgChecks.nullNotPermitted(dataset, "dataset");
    ArgChecks.nullNotPermitted(xAxis, "xAxis");
    ArgChecks.nullNotPermitted(yAxis, "yAxis");
    ArgChecks.nullNotPermitted(zAxis, "zAxis");
    this.dimensions = new Dimension3D(10, 10, 10);
    this.dataset = dataset;
    this.dataset.addChangeListener(this);
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
  
  /**
   * Sets the dimensions for the plot and notifies registered listeners that
   * the plot dimensions have been changed.
   * 
   * @param dim  the new dimensions (<code>null</code> not permitted).
   */
  public void setDimensions(Dimension3D dim) {
    ArgChecks.nullNotPermitted(dim, "dim");
    this.dimensions = dim;
    fireChangeEvent();
  }

  /**
   * Returns the dataset for the plot.
   * 
   * @return The dataset (never <code>null</code>). 
   */
  public XYZDataset getDataset() {
    return this.dataset;
  }

  /**
   * Sets the dataset and sends a change event notification to all registered
   * listeners.
   * 
   * @param dataset  the new dataset (<code>null</code> not permitted).
   */
  public void setDataset(XYZDataset dataset) {
    ArgChecks.nullNotPermitted(dataset, "dataset");
    this.dataset.removeChangeListener(this);
    this.dataset = dataset;
    this.dataset.addChangeListener(this);
    fireChangeEvent();
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

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYZPlot)) {
      return false;
    }
    XYZPlot that = (XYZPlot) obj;
    if (!this.dimensions.equals(that.dimensions)) {
      return false;
    }
    return true;
  }
}
