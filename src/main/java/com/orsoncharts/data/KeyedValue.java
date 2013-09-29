/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

/**
 * A (key, value) pair.
 */
public interface KeyedValue<T> {
  
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
    public T getValue();
  
}
