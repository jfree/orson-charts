/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

/**
 * A data item where a key is associated with a numerical value.
 */
public class DefaultKeyedValue {

  private Comparable key;

  private Number value;

  /**
   * Creates a new instance.
   * 
   * @param key  the key (<code>null</code> not permitted).
   * @param value  the value.
   */
  public DefaultKeyedValue(Comparable key, Number value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Returns the key.
   * 
   * @return The key (never <code>null</code>). 
   */
  public Comparable getKey() {
    return this.key;
  }

  /**
   * Returns the value.
   * 
   * @return The value (possibly <code>null</code>). 
   */
  public Number getValue() {
    return this.value;
  }
  
  @Override
  public String toString() {
    return "(" + this.key.toString() + ", " + value + ")";
  }
}
