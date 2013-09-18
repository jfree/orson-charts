/*
 * (C)opyright 2013 by Object Refinery Limited.
 */
package org.jfree.chart3d.data;

/**
 * A generic representation of a two dimensional grid of data values.
 */
public interface Values2D {
    
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
   * Returns the data item at the specified position.
   * 
   * @param xIndex  the x-index.
   * @param yIndex  the y-index.
   * 
   * @return The data value (possibly <code>null</code>).
   */
  public Number getValue(int xIndex, int yIndex);

}
