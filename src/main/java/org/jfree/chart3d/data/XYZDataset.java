/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

/**
 * Defines the methods used to access data in the form of multiple series
 * containing (x, y, z) data items.
 */
public interface XYZDataset {

  public int getSeriesCount();

  public int getItemCount(int series);

  public double getX(int series, int item);

  public double getY(int series, int item);

  public double getZ(int series, int item);

}
