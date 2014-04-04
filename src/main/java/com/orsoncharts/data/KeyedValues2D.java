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
 * A two dimensional grid of data values where each value is uniquely 
 * identified by two keys (the <code>rowKey</code> and the 
 * <code>columnKey</code>).  Any instance of <code>Comparable</code> can be 
 * used as a key (<code>String</code> objects are instances of 
 * <code>Comparable</code>, making them convenient key objects).
 */
public interface KeyedValues2D<T> extends Values2D<T> {

    /**
     * Returns the row key with the specified index.
     * 
     * @param rowIndex  the row index.
     * 
     * @return The key. 
     */
    public Comparable<?> getRowKey(int rowIndex);

    /**
     * Returns the column key with the specified index.
     * 
     * @param columnIndex  the index.
     * 
     * @return The key. 
     */
    public Comparable<?> getColumnKey(int columnIndex);

    /**
     * Returns the index of the specified key, or <code>-1</code> if there
     * is no such key.
     * 
     * @param rowKey  the row key (<code>null</code> not permitted).
     * 
     * @return The index, or <code>-1</code>. 
     */
    public int getRowIndex(Comparable<?> rowKey);

    /**
     * Returns the index of the specified key, or <code>-1</code> if there
     * is no such key.
     * 
     * @param columnKey  the column key (<code>null</code> not permitted).
     * 
     * @return The index, or <code>-1</code>. 
     */
    public int getColumnIndex(Comparable<?> columnKey);
  
    /**
     * Returns a list of the row keys (the order is significant, since data 
     * values can be accessed by index as well as by key).
     * <br><br>
     * NOTE: this method must be implemented so that modifications to the
     * returned list do not impact the underlying data structure.
     * 
     * @return A list of row keys.
     */
    public List<Comparable<?>> getRowKeys();

    /**
     * Returns a list of the column keys (the order is significant, since data 
     * values can be accessed by index as well as by key).
     * <br><br>
     * NOTE: this method must be implemented so that modifications to the
     * returned list do not impact the underlying data structure.
     * 
     * @return A list of column keys.
     */
    public List<Comparable<?>> getColumnKeys();

    /**
     * Returns the value (possibly <code>null</code>) associated with the 
     * specified keys.  If either or both of the keys is not defined in this
     * data structure, a runtime exception will be thrown.
     * 
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     * 
     * @return The value (possibly <code>null</code>). 
     */
    public T getValue(Comparable<?> rowKey, Comparable<?> columnKey);

}
