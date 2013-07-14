/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

/**
 * A (key, value) item.
 */
public class KeyValueItem {

  private Comparable key;

  private Number value;

  public KeyValueItem(Comparable key, Number value) {
    this.key = key;
    this.value = value;
  }

  public Comparable getKey() {
    return this.key;
  }

  public Number getValue() {
    return this.value;
  }
  
  @Override
  public String toString() {
    return "(" + this.key.toString() + ", " + value + ")";
  }
}
