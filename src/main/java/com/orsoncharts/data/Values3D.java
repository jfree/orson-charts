/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

/**
 * A generic representation of a three dimensional grid (cube) of data values.
 * 
 * @see KeyedValues3D
 */
public interface Values3D {

  /**
   * Returns the number of items in the x-dimension.
   * 
   * @return The number of items in the x-dimension. 
   */
  public int getXCount();
  
  /**
   * Returns the number of items in the y-dimension.
   * 
   * @return The number of items in the y-dimension. 
   */
  public int getYCount();
  
  /**
   * Returns the number of items in the z-dimension.
   * 
   * @return The number of items in the z-dimension. 
   */
  public int getZCount();
  
  /**
   * Returns the data item at the specified position.
   * 
   * @param xIndex  the x-index.
   * @param yIndex  the y-index.
   * @param zIndex  the z-index.
   * 
   * @return The data value (possibly <code>null</code>).
   */
  public Number getValue(int xIndex, int yIndex, int zIndex);
  
}
