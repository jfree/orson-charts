/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

/**
 * A (key, value) pair.
 */
public interface KeyedValue {
  
  /**
   * Returns the key.
   * 
   * @return The key. 
   */
  public Comparable getKey();
  
  /**
   * Returns the value.
   * 
   * @return The value (possibly <code>null</code>). 
   */
  public Number getValue();
  
}
