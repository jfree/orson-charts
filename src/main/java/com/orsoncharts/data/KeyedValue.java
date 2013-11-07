/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

/**
 * A <code>(key, value)</code> pair that is used as a building block for some
 * data structures for the charts.
 */
public interface KeyedValue<T> {
  
    /**
     * Returns the key (by design, this key is required to be 
     * non-<code>null</code>).
     * 
     * @return The key (never <code>null</code>). 
     */
    public Comparable getKey();
  
    /**
     * Returns the value.
     * 
     * @return The value (possibly <code>null</code>). 
     */
    public T getValue();
  
}
