/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import com.orsoncharts.axis.Axis3DChangeEvent;
import com.orsoncharts.axis.Axis3DChangeListener;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.data.Dataset3DChangeListener;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.renderer.xyz.XYZRenderer;
import com.orsoncharts.ArgChecks;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.renderer.Renderer3DChangeListener;

/**
 * An XYZ plot.
 */
public class XYZPlot extends AbstractPlot3D implements Dataset3DChangeListener,
        Axis3DChangeListener, Renderer3DChangeListener {

    /** The dataset. */
    private XYZDataset dataset;

    /** The renderer. */
    private XYZRenderer renderer;
  
    /** The x-axis. */
    private ValueAxis3D xAxis;

    /** The y-axis. */
    private ValueAxis3D yAxis;
  
    /** The z-axis. */
    private ValueAxis3D zAxis;

  /**
   * Creates a new plot with the specified axes.
   * 
   * @param dataset  the dataset (<code>null</code> not permitted).
   * @param renderer  the renderer (<code>null</code> not permitted).
   * @param xAxis  the x-axis (<code>null</code> not permitted).
   * @param yAxis  the y-axis (<code>null</code> not permitted).
   * @param zAxis  the z-axis (<code>null</code> not permitted).
   */
  public XYZPlot(XYZDataset dataset, XYZRenderer renderer, ValueAxis3D xAxis, 
          ValueAxis3D yAxis, ValueAxis3D zAxis) {
    ArgChecks.nullNotPermitted(dataset, "dataset");
    ArgChecks.nullNotPermitted(renderer, "renderer");
    ArgChecks.nullNotPermitted(xAxis, "xAxis");
    ArgChecks.nullNotPermitted(yAxis, "yAxis");
    ArgChecks.nullNotPermitted(zAxis, "zAxis");
    this.dimensions = new Dimension3D(10, 10, 10);
    this.dataset = dataset;
    this.dataset.addChangeListener(this);
    this.renderer = renderer;
    this.renderer.setPlot(this);
    this.renderer.addChangeListener(this);
    this.xAxis = xAxis;
    this.xAxis.addChangeListener(this);
    this.xAxis.configureAsXAxis(this);
    this.yAxis = yAxis;
    this.yAxis.addChangeListener(this);
    this.yAxis.configureAsYAxis(this);
    this.zAxis = zAxis;
    this.zAxis.addChangeListener(this);
    this.zAxis.configureAsZAxis(this);
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

  public ValueAxis3D getXAxis() {
    return this.xAxis;
  }

  public void setXAxis(ValueAxis3D xAxis) {
    this.xAxis = xAxis;
    fireChangeEvent();
  }

  public ValueAxis3D getYAxis() {
    return this.yAxis;
  }

  public void setYAxis(ValueAxis3D yAxis) {
    this.yAxis = yAxis;
    fireChangeEvent();
  }

  public ValueAxis3D getZAxis() {
    return this.zAxis;
  }

  public void setZAxis(ValueAxis3D zAxis) {
    this.zAxis = zAxis;
    fireChangeEvent();
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

    @Override
    public void axisChanged(Axis3DChangeEvent event) {
        fireChangeEvent();
    }

    @Override
    public void rendererChanged(Renderer3DChangeEvent event) {
        fireChangeEvent();
    }

    @Override
    public void datasetChanged(Dataset3DChangeEvent event) {
        this.xAxis.configureAsXAxis(this);
        this.yAxis.configureAsYAxis(this);
        this.zAxis.configureAsZAxis(this);
        super.datasetChanged(event);
    }
    
}
