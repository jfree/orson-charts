/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.util.ArgChecks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A three dimensional table of numerical values, implementing the 
 * {@link KeyedValues3D} interface.
 */
public final class DefaultKeyedValues3D<V> implements KeyedValues3D {

    /** The series keys. */
    private List<Comparable> seriesKeys;
  
    /** The row keys. */
    private List<Comparable> rowKeys;
  
    /** The column keys. */
    private List<Comparable> columnKeys;

    /**
     * The data, one entry per series.  Each series *must* contain the same
     * row and column keys.
     */
    private List<DefaultKeyedValues2D<V>> data; // one entry per series
  
    /**
     * Creates a new (empty) table.
     */
    public DefaultKeyedValues3D() {
        this.seriesKeys = new ArrayList<Comparable>();
        this.rowKeys = new ArrayList<Comparable>();
        this.columnKeys = new ArrayList<Comparable>();
        this.data = new ArrayList<DefaultKeyedValues2D<V>>();
    }
  
    /**
     * Returns the series key with the specified index.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The series key. 
     */
    @Override
    public Comparable getSeriesKey(int seriesIndex) {
        return this.seriesKeys.get(seriesIndex);
    }

    /**
     * Returns the row key with the specified index.
     * 
     * @param rowIndex  the row index.
     * 
     * @return The row key. 
     */
    @Override
    public Comparable getRowKey(int rowIndex) {
        return this.rowKeys.get(rowIndex);
    }

    /**
     * Returns the column key with the specified index.
     * 
     * @param columnIndex  the column index.
     * 
     * @return The column key. 
     */
    @Override
    public Comparable getColumnKey(int columnIndex) {
        return this.columnKeys.get(columnIndex);
    }

    /**
     * Returns the index for the specified series key, or <code>-1</code> if 
     * the key is not present in this data structure.
     * 
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * 
     * @return The series index or <code>-1</code>. 
     */
    @Override
    public int getSeriesIndex(Comparable seriesKey) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        return this.seriesKeys.indexOf(seriesKey);
    }

    /**
     * Returns the index for the specified row key, or <code>-1</code> if 
     * the key is not present in this data structure.
     * 
     * @param rowKey  the row key (<code>null</code> not permitted).
     * 
     * @return The row index or <code>-1</code>. 
     */
    @Override
    public int getRowIndex(Comparable rowKey) {
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        return this.rowKeys.indexOf(rowKey);
    }

    /**
     * Returns the index for the specified column key, or <code>-1</code> if 
     * the key is not present in this data structure.
     * 
     * @param columnKey  the column key (<code>null</code> not permitted).
     * 
     * @return The column index or <code>-1</code>. 
     */
    @Override
    public int getColumnIndex(Comparable columnKey) {
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        return this.columnKeys.indexOf(columnKey);
    }

    @Override
    public List<Comparable> getSeriesKeys() {
        return Collections.unmodifiableList(this.seriesKeys);
    }

    @Override
    public List<Comparable> getRowKeys() {
        return Collections.unmodifiableList(this.rowKeys);
    }

    @Override
    public List<Comparable> getColumnKeys() {
        return Collections.unmodifiableList(this.columnKeys);
    }

    @Override
    public int getSeriesCount() {
        return this.seriesKeys.size();
    }

    @Override
    public int getRowCount() {
        return this.rowKeys.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnKeys.size();
    }

    @Override
    public V getValue(int seriesIndex, int rowIndex, int columnIndex) {
        return this.data.get(seriesIndex).getValue(rowIndex, columnIndex);
    }
    
    @Override
    public V getValue(Comparable seriesKey, Comparable rowKey, 
            Comparable columnKey) {
        return getValue(getSeriesIndex(seriesKey), getRowIndex(rowKey), 
                getColumnIndex(columnKey));
    }

    @Override
    public double getDoubleValue(int seriesIndex, int rowIndex, int columnIndex) {
        V n = getValue(seriesIndex, rowIndex, columnIndex);
        if (n != null && n instanceof Number) {
            return ((Number) n).doubleValue();
        }
        return Double.NaN;
    }
    
    public void setValue(V n, Comparable seriesKey, Comparable rowKey, 
            Comparable columnKey) {
        // cases:
        // 1 - the dataset is empty, so we just need to add a new layer with the
        //     given keys;
        if (this.data.isEmpty()) {
            this.seriesKeys.add(seriesKey);
            this.rowKeys.add(rowKey);
            this.columnKeys.add(columnKey);
            DefaultKeyedValues2D d = new DefaultKeyedValues2D();
            d.setValue(n, rowKey, columnKey);
            this.data.add(d);
        }
        
        int seriesIndex = getSeriesIndex(seriesKey);
        int rowIndex = getRowIndex(rowKey);
        int columnIndex = getColumnIndex(columnKey);
        if (rowIndex < 0) {
            this.rowKeys.add(rowKey);
        }
        if (columnIndex < 0) {
            this.columnKeys.add(columnKey);
        }
        if (rowIndex < 0 || columnIndex < 0) {
            for (DefaultKeyedValues2D d : this.data) {
                d.setValue(null, rowKey, columnKey);
            } 
        } 
        if (seriesIndex >= 0) {
            DefaultKeyedValues2D d = this.data.get(seriesIndex);
            d.setValue(n, rowKey, columnKey);
        } else {
            this.seriesKeys.add(seriesKey);
            DefaultKeyedValues2D d = new DefaultKeyedValues2D(this.rowKeys, 
                    this.columnKeys);
            d.setValue(n, rowKey, columnKey);
            this.data.add(d);
        }
    }

}
