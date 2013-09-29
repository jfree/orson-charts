/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import java.util.List;

/**
 * A three dimensional cube of data values where each value is uniquely 
 * identified by three keys (the seriesKey, rowKey and columnKey).  
 */
public interface KeyedValues3D<T> extends Values3D {

    /**
     * Returns a list of the series-keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the series-keys (possibly empty, but never 
     *     <code>null</code>). 
     */
    public List<Comparable> getSeriesKeys();

    /**
     * Returns a list of the row-keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the row-keys (possibly empty, but never 
     *     <code>null</code>). 
     */
    public List<Comparable> getRowKeys();
    
    /**
     * Returns a list of the column-keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the column-keys (possibly empty, but never 
     *     <code>null</code>). 
     */
    public List<Comparable> getColumnKeys();
    
    public Comparable getSeriesKey(int seriesIndex);

    public Comparable getRowKey(int rowIndex);

    public Comparable getColumnKey(int columnIndex);

    public int getSeriesIndex(Comparable serieskey);

    public int getRowIndex(Comparable rowkey);

    public int getColumnIndex(Comparable columnkey);

    /**
     * Returns the value for a given series, row and column.
     * 
     * @param seriesKey the series key (<code>null</code> not permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     * 
     * @return The value (possibly <code>null</code>).
     */
    public T getValue(Comparable seriesKey, Comparable rowKey, 
            Comparable columnKey);
    
}
