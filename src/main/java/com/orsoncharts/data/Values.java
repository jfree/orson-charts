/**
 * A generic representation of a one dimensional list of data values.
 */
package com.orsoncharts.data;

/**
 *
 */
public interface Values {
    
  /**
   * Returns the number of items in the dataset.
   *
   * @return The number of items in the dataset.
   */
  public int getItemCount();

  /**
   * Returns the value for the specified item.
   *
   * @param item  the item index.
   *
   * @return The value for the specified item (possibly <code>null</code>).
   */
  public Number getValue(int item);
  
}
