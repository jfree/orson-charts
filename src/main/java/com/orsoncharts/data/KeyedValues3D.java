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
 * A three dimensional cube of data values where each value is uniquely 
 * identified by three keys (the <code>seriesKey</code>, <code>rowKey</code> 
 * and <code>columnKey</code>).  
 */
public interface KeyedValues3D<T> extends Values3D<T> {

    /**
     * Returns a list of the series keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the series keys (possibly empty, but never 
     *     <code>null</code>). 
     */
    List<Comparable<?>> getSeriesKeys();

    /**
     * Returns a list of the row keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the row keys (possibly empty, but never 
     *     <code>null</code>). 
     */
    List<Comparable<?>> getRowKeys();
    
    /**
     * Returns a list of the column keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the column keys (possibly empty, but never 
     *     <code>null</code>). 
     */
    List<Comparable<?>> getColumnKeys();
    
    /**
     * Returns the series key with the specified index.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The key. 
     */
    Comparable<?> getSeriesKey(int seriesIndex);

    /**
     * Returns the row key with the specified index.
     * 
     * @param rowIndex  the row index.
     * 
     * @return The key. 
     */    
    Comparable<?> getRowKey(int rowIndex);

    /**
     * Returns the column key with the specified index.
     * 
     * @param columnIndex  the column index.
     * 
     * @return The key. 
     */    
    Comparable<?> getColumnKey(int columnIndex);

    /**
     * Returns the index of the specified series key, or <code>-1</code> if
     * there is no matching key.
     * 
     * @param serieskey  the series key (<code>null</code> not permitted).
     * 
     * @return The key index, or <code>-1</code>. 
     */
    int getSeriesIndex(Comparable<?> serieskey);

    /**
     * Returns the index of the specified row key, or <code>-1</code> if there
     * is no matching key.
     * 
     * @param rowkey  the row key (<code>null</code> not permitted).
     * 
     * @return The row index or <code>-1</code>. 
     */
    int getRowIndex(Comparable<?> rowkey);

    /**
     * Returns the index of the specified column key, or <code>-1</code> if 
     * there is no matching key.
     * 
     * @param columnkey  the column key (<code>null</code> not permitted).
     * 
     * @return The column index or <code>-1</code>. 
     */
    int getColumnIndex(Comparable<?> columnkey);

    /**
     * Returns the value for a given series, row and column.
     * 
     * @param seriesKey the series key (<code>null</code> not permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     * 
     * @return The value (possibly <code>null</code>).
     */
    T getValue(Comparable<?> seriesKey, Comparable<?> rowKey, 
            Comparable<?> columnKey);
    
}
