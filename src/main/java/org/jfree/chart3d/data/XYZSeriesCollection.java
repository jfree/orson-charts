/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

import java.util.ArrayList;
import java.util.List;
import org.jfree.graphics3d.ArgChecks;

/**
 * A collection of XYZSeries objects.
 */
public class XYZSeriesCollection implements XYZDataset {

  private List<XYZSeries> series;

  public XYZSeriesCollection() {
    this.series = new ArrayList<XYZSeries>();
  }

  @Override
  public int getSeriesCount() {
    return this.series.size();
  }

  public void add(XYZSeries series) {
    ArgChecks.nullNotPermitted(series, "series");
    this.series.add(series);
  }

  @Override
  public int getItemCount(int series) {
    ArgChecks.nullNotPermitted(this, null);
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public double getX(int series, int item) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public double getY(int series, int item) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public double getZ(int series, int item) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
