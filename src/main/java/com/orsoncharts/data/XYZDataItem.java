/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

/**
 * Represents a single (x, y, z) data item, which can be added to a
 * XYZDataSeries.
 */
public class XYZDataItem {

  private double x;

  private double y;

  private double z;

  public XYZDataItem(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

}
