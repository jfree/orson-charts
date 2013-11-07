/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

/**
 * A generic representation of a one dimensional list of data values.
 */
public interface Values<T> {
    
    /**
     * Returns the number of items in the dataset.
     *
     * @return The number of items in the dataset.
     */
    int getItemCount();

    /**
     * Returns the value for the specified item.
     *
     * @param item  the item index.
     *
     * @return The value for the specified item (possibly <code>null</code>).
     */
    T getValue(int item);
  
    /**
     * Returns the value for the specified item as a double primitive, provided
     * that the data type is a subclass of <code>Number</code>.  Where
     * the {@link #getValue(int)} method returns <code>null</code>, this method
     * returns <code>Double.NaN</code>.
     * 
     * @param item  the item index.
     * 
     * @return The value for the specified item. 
     */
    double getDoubleValue(int item);

}
