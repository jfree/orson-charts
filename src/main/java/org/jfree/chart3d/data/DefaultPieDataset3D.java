/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

import java.util.ArrayList;
import java.util.List;
import org.jfree.graphics3d.ArgChecks;

/**
 * A pie dataset.
 */
public class DefaultPieDataset3D implements PieDataset3D {

  private List<KeyValueItem> items;

  /**
   * Creates a new (empty) dataset.
   */
  public DefaultPieDataset3D() {
    this.items = new ArrayList<KeyValueItem>();
  }

  /**
   * Adds a value to the dataset.
   * 
   * @param key  the key (<code>null</code> not permitted).
   * @param value 
   */
  public void add(Comparable key, Number value) {
    ArgChecks.nullNotPermitted(key, "key");
    // TODO : need to validate that the keys are unique
    this.items.add(new KeyValueItem(key, value));
  }

  /**
   * Returns the number of items in the dataset.
   * 
   * @return The number of items in the dataset. 
   */
  @Override
  public int getItemCount() {
    return this.items.size();
  }

  @Override
  public Comparable getKey(int item) {
    KeyValueItem kvi = this.items.get(item);
    return kvi.getKey();
  }

  @Override
  public Number getValue(int item) {
    KeyValueItem kvi = this.items.get(item);
    return kvi.getValue();
  }

}
