/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import java.util.List;

/**
 * A two dimensional grid of data values where each value is uniquely 
 * identified by two keys (the <code>xKey</code> and the <code>yKey</code>).
 * Any instance of <code>Comparable</code> can be used as a key
 * (<code>String</code> objects are instances of <code>Comparable</code>, 
 * making them convenient key objects).
 */
public interface KeyedValues2D<T> extends Values2D<T> {

    /**
     * Returns the x-key with the specified index.
     * 
     * @param xIndex  the index.
     * 
     * @return The key. 
     */
    public Comparable<?> getXKey(int xIndex);

    /**
     * Returns the y-key with the specified index.
     * 
     * @param yIndex  the index.
     * 
     * @return The key. 
     */
    public Comparable<?> getYKey(int yIndex);

    /**
     * Returns the index of the specified key, or <code>-1</code> if there
     * is no such key.
     * 
     * @param xkey  the x-key (<code>null</code> not permitted).
     * 
     * @return The index, or <code>-1</code>. 
     */
    public int getXIndex(Comparable<?> xkey);

    /**
     * Returns the index of the specified key, or <code>-1</code> if there
     * is no such key.
     * 
     * @param ykey  the y-key (<code>null</code> not permitted).
     * 
     * @return The index, or <code>-1</code>. 
     */
    public int getYIndex(Comparable<?> ykey);
  
    /**
     * Returns a list of the x-keys (the order is significant, since data 
     * values can be accessed by index as well as by key).
     * <br><br>
     * NOTE: this method must be implemented so that modifications to the
     * returned list do not impact the underlying data structure.
     * 
     * @return A list of x-keys.
     */
    public List<Comparable<?>> getXKeys();

    /**
     * Returns a list of the y-keys (the order is significant, since data 
     * values can be accessed by index as well as by key).
     * <br><br>
     * NOTE: this method must be implemented so that modifications to the
     * returned list do not impact the underlying data structure.
     * 
     * @return A list of y-keys.
     */
    public List<Comparable<?>> getYKeys();

    /**
     * Returns the value (possibly <code>null</code>) associated with the 
     * specified keys.  If either or both of the keys is not defined in this
     * data structure, a runtime exception will be thrown.
     * 
     * @param xKey  the x-key (<code>null</code> not permitted).
     * @param yKey  the y-key (<code>null</code> not permitted).
     * 
     * @return The value (possibly <code>null</code>). 
     */
    public T getValue(Comparable<?> xKey, Comparable<?> yKey);

}
