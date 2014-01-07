/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
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
    int getXCount();
  
    /**
     * Returns the number of items in the y-dimension.
     * 
     * @return The number of items in the y-dimension. 
     */
    int getYCount();
  
    /**
     * Returns the data item at the specified position.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The data value (possibly <code>null</code>).
     */
    T getValue(int xIndex, int yIndex);

    /**
     * Returns the data value at the specified position as a double primitive,
     * or <code>Double.NaN</code> if the value is not an instance of 
     * <code>Number</code>.  Where the {@link #getValue(int, int)} method 
     * returns <code>null</code>, this method returns <code>Double.NaN</code>.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The data value.
     */
    double getDoubleValue(int xIndex, int yIndex);

}
