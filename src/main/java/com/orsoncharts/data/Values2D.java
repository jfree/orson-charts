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
 * 
 * @param <T> the type of value stored in the grid.
 */
public interface Values2D<T> {
    
    /**
     * Returns the number of rows in the grid.
     * 
     * @return The number of rows in the grid. 
     */
    int getRowCount();
  
    /**
     * Returns the number of columns in the grid.
     * 
     * @return The number of columns in the grid. 
     */
    int getColumnCount();
  
    /**
     * Returns the data item at the specified position.
     * 
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The data value (possibly <code>null</code>).
     */
    T getValue(int rowIndex, int columnIndex);

    /**
     * Returns the data value at the specified position as a double primitive,
     * or <code>Double.NaN</code> if the value is not an instance of 
     * <code>Number</code>.  Where the {@link #getValue(int, int)} method 
     * returns <code>null</code>, this method returns <code>Double.NaN</code>.
     * 
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The data value.
     */
    double getDoubleValue(int rowIndex, int columnIndex);

}
