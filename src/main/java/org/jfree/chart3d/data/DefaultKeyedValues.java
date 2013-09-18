/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of (key, value) pairs.  This is the basic structure of the data 
 * required for a pie chart.
 */
public class DefaultKeyedValues implements KeyedValues {

  private List<KeyedValue> data;
  
  /**
   * Creates a new (empty) list of keyed values.
   */
  public DefaultKeyedValues() {
    this.data = new ArrayList<KeyedValue>();
  }
  
  /**
   * Clears all the data.
   */
  public void clear() {
    this.data.clear();
  }

  /**
   * 
   * @param key
   * @param value 
   */
  public void addValue(Comparable key, double value) {
    addValue(key, Double.valueOf(value));
  }
 
  /**
   * Adds a value or, if there is an existing value with the same key, updates 
   * an existing value.
   * 
   * @param key  the key (<code>null</code> not permitted)
   * @param value  the value.
   */
  public void addValue(Comparable key, Number value) {
    DefaultKeyedValue dkv;
    int index = getIndex(key);
    if (index >= 0) {
      dkv = (DefaultKeyedValue) this.data.get(index);
      dkv.setValue(value);
    } else {
      this.data.add(new DefaultKeyedValue(key, value));
    }
  }
  
  public void removeValue(Comparable key) {
    int index = getIndex(key);
    if (index >= 0) {
      removeValue(index);
    }
  }
  
  public void removeValue(int index) {
    this.data.remove(index);
  }
  
  @Override
  public Comparable getKey(int index) {
    KeyedValue kv = this.data.get(index);
    return kv.getKey();
  }

  @Override
  public int getIndex(Comparable key) {
    for (int i = 0; i < this.data.size(); i++) {
      KeyedValue kv = this.data.get(i);
      if (kv.getKey().equals(key)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public List<Comparable> getKeys() {
    List<Comparable> keys = new ArrayList<Comparable>();
    for (KeyedValue kv : this.data) {
      keys.add(kv.getKey());
    }
    return keys;
  }

  @Override
  public Number getValue(Comparable key) {
    int index = this.getIndex(key);
    return getValue(index);
  }

  @Override
  public int getItemCount() {
    return this.data.size();
  }

  @Override
  public Number getValue(int item) {
    KeyedValue kv = this.data.get(item);
    return kv.getValue();
  }
    
}
