/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.ArgChecks;

/**
 * A data item where a key is associated with a numerical value.
 */
public class DefaultKeyedValue<T> implements KeyedValue {

    private Comparable key;

    private T value;

    /**
     * Creates a new instance.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param value  the value.
     */
    public DefaultKeyedValue(Comparable key, T value) {
        ArgChecks.nullNotPermitted(key, "key");
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key.
     * 
     * @return The key (never <code>null</code>). 
     */
    @Override
    public Comparable getKey() {
        return this.key;
    }

    /**
     * Returns the value.
     * 
     * @return The value (possibly <code>null</code>). 
     */
    @Override
    public T getValue() {
        return this.value;
    }
  
    /**
     * Sets the value.
     */
    public void setValue(T value) {
        this.value = value;
    }
  
    @Override
    public String toString() {
        return "(" + this.key.toString() + ", " + value + ")";
    }
}
