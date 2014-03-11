/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.util.ArgChecks;

/**
 * An object that references one data item in a {@link KeyedValues3D} data
 * structure.
 * 
 * @since 1.3
 */
public class ValuesItemKey implements ItemKey {
    
    Comparable<? extends Object> key;
    
    /**
     * Creates a new instance.
     * 
     * @param key  the key (<code>null</code> not permitted).
     */
    public ValuesItemKey(Comparable<? extends Object> key) {
        ArgChecks.nullNotPermitted(key, "key");
        this.key = key;
    }
    
    /**
     * Returns the key.
     * 
     * @return The key (never <code>null</code>). 
     */
    public Comparable<?> getKey() {
        return this.key;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValuesItemKey[");
        sb.append(key.toString());
        sb.append("]");
        return sb.toString();
    }
}