/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

/**
 * A default XYZDataset implementation.
 */
public class DefaultXYZDataset extends AbstractDataset3D implements XYZDataset {

  private static final int POINTS = 2500;
  private double[][] xValues;
  private double[][] yValues;
  private double[][] zValues;

  public DefaultXYZDataset(int seriesCount, int pointCount) {
    this.xValues = new double[seriesCount][pointCount];
    this.yValues = new double[seriesCount][pointCount];
    this.zValues = new double[seriesCount][pointCount];
    for (int s = 0; s < seriesCount; s++) {
      for (int i = 0; i < pointCount; i++) {
        xValues[s][i] = Math.random() * 10.0;
        yValues[s][i] = Math.random() * 10.0;
        zValues[s][i] = Math.random() * 10.0;
      }
    }
  }

  @Override
  public int getSeriesCount() {
    return xValues.length;
  }

  @Override
  public int getItemCount(int series) {
    return xValues[series].length;
  }

  @Override
  public double getX(int series, int item) {
    return xValues[series][item];
  }

  @Override
  public double getY(int series, int item) {
    return yValues[series][item];
  }

  @Override
  public double getZ(int series, int item) {
    return zValues[series][item];
  }

}
