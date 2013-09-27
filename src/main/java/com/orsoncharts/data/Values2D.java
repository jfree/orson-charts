/**
 * ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

/**
 * A generic representation of a two dimensional grid of data values.
 */
public interface Values2D<T> {
    
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
    public T getValue(int xIndex, int yIndex);

    /**
     * Returns the data item at the specified position as a double primitive.
     * Where the {@link #getValue(int, int)} method returns <code>null</code>, 
     * this method returns <code>Double.NaN</code>.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The data value.
     */
    public double getDoubleValue(int xIndex, int yIndex);

}
