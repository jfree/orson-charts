/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

/**
 * An interface for obtaining data for a pie chart.
 */
public interface PieDataset3D {

  /**
   * Returns the number of items in the dataset.
   *
   * @return The number of items in the dataset.
   */
  public int getItemCount();

  //public List<Comparable> getKeys();
  /**
   * Returns the key for the specified item.
   *
   * @param item  the item.
   *
   * @return The key (not <code>null</code>)
   */
  public Comparable getKey(int item);
  //public int getIndex(Comparable key);
  
  /**
   * Returns the value for the specified item.
   *
   * @param item  the item.
   *
   * @return The value for the specified item (possibly <code>null</code>).
   */
  public Number getValue(int item);
  
  //public Number getValue(Comparable key);

}
