/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.data;

import java.util.List;

/**
 * A list of values that are associated with unique keys.
 */
public interface KeyedValues<T> extends Values<T> { 

    /**
     * Returns the key for the specified item in the list.
     * 
     * @param index  the item index.
     * 
     * @return The key. 
     */
    public Comparable<?> getKey(int index);
  
    /**
     * Returns the index for the specified key, or <code>-1</code> if the key
     * is not present in the list.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The item index, or <code>-1</code>. 
     */
    public int getIndex(Comparable<?> key);
  
    /**
     * Returns a list of all the keys.  Note that the list will be a copy, so
     * modifying it will not impact this data structure.
     * 
     * @return A list of keys (possibly empty, but never <code>null</code>).
     */
    public List<Comparable<?>> getKeys();

    /**
     * Returns the value associated with the specified key, or 
     * <code>null</code>.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The value (possibly <code>null</code>). 
     */
    public T getValue(Comparable<?> key);

}
