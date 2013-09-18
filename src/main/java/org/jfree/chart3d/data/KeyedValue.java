/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

/**
 * A (key, value) pair.
 */
public interface KeyedValue {
  
  public Comparable getKey();
  public Number getValue(); 
}
