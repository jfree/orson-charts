/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.category;

import java.util.List;
import com.orsoncharts.data.AbstractDataset3D;
import com.orsoncharts.data.DefaultKeyedValues3D;
import com.orsoncharts.data.KeyedValues;
import com.orsoncharts.util.ArgChecks;

/**
 * A default implementation of the {@link CategoryDataset3D} interface.
 * This dataset is typically used to create bar charts and stacked bar charts.
 */
public final class DefaultCategoryDataset3D extends AbstractDataset3D  
        implements CategoryDataset3D {
  
    /**
     * Storage for the data.
     */
    private DefaultKeyedValues3D<Number> data;
    
    /**
     * Creates a new (empty) dataset.
     */
    public DefaultCategoryDataset3D() {
        this.data = new DefaultKeyedValues3D();  
    }

    /**
     * Returns the number of data series in the dataset.
     * 
     * @return The number of data series.
     */
    @Override
    public int getSeriesCount() {
        return this.data.getSeriesCount();
    }

    /**
     * Returns the number of rows in the dataset.
     * 
     * @return The number of rows. 
     */
    @Override
    public int getRowCount() {
        return this.data.getRowCount();
    }

    /**
     * Returns the number of columns in the dataset.
     * 
     * @return The number of columns. 
     */
    @Override
    public int getColumnCount() {
        return this.data.getColumnCount();
    }
    
    /**
     * Returns the key for the specified series.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The series key. 
     */
    @Override
    public Comparable getSeriesKey(int seriesIndex) {
        return this.data.getSeriesKey(seriesIndex);
    }

    /**
     * Returns the key for the specified row.
     * 
     * @param rowIndex The row index.
     * 
     * @return The row key. 
     */
    @Override
    public Comparable getRowKey(int rowIndex) {
        return this.data.getRowKey(rowIndex);
    }

    /**
     * Returns the key for the specified column.
     * 
     * @param columnIndex  the column index.
     * 
     * @return The column key. 
     */
    @Override
    public Comparable getColumnKey(int columnIndex) {
        return this.data.getColumnKey(columnIndex);
    }

    /**
     * Returns the index for the specified series key, or -1 if the key is
     * not defined in the dataset.
     * 
     * @param serieskey  the series key (<code>null</code> not permitted).
     * 
     * @return The series index or -1. 
     */
    @Override
    public int getSeriesIndex(Comparable serieskey) {
        return this.data.getSeriesIndex(serieskey);
    }

    @Override
    public int getRowIndex(Comparable rowkey) {
        return this.data.getRowIndex(rowkey);
    }

    @Override
    public int getColumnIndex(Comparable columnkey) {
        return this.data.getColumnIndex(columnkey);
    }

    @Override
    public List<Comparable> getSeriesKeys() {
        return this.data.getSeriesKeys();
    }

    @Override
    public List<Comparable> getRowKeys() {
        return this.data.getRowKeys();
    }

    @Override
    public List<Comparable> getColumnKeys() {
        return this.data.getColumnKeys();
    }

    @Override
    public Number getValue(Comparable seriesKey, Comparable rowKey, 
            Comparable columnKey) {
        return this.data.getValue(seriesKey, rowKey, columnKey);
    }

    @Override
    public Number getValue(int seriesIndex, int rowIndex, int columnIndex) {
        return this.data.getValue(seriesIndex, rowIndex, columnIndex);
    }
    
    public void setValue(Number n, Comparable seriesKey, Comparable rowKey, 
            Comparable columnKey) {
        this.data.setValue(n, seriesKey, rowKey, columnKey);
        fireDatasetChanged();
    }
    
    public void addValue(Number n, Comparable seriesKey, Comparable rowKey, 
            Comparable columnKey) {
        setValue(n, seriesKey, rowKey, columnKey);
    }

    @Override
    public double getDoubleValue(int seriesIndex, int rowIndex, 
            int columnIndex) {
        return this.data.getDoubleValue(seriesIndex, rowIndex, columnIndex);
    }
 
    /**
     * Adds a data series as a single row in the dataset.
     * 
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param data  the data (<code>null</code> not permitted).
     */
    public void addSeriesAsRow(Comparable seriesKey, KeyedValues<Number> data) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(data, "data");
        for (Comparable key : data.getKeys()) {
            setValue(data.getValue(key), seriesKey, seriesKey, key);
        }
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultCategoryDataset3D)) {
            return false;
        }
        DefaultCategoryDataset3D that = (DefaultCategoryDataset3D) obj;
        if (!this.data.equals(that.data)) {
            return false;
        }
        return super.equals(obj);
    }
}
