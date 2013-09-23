/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import java.util.List;

/**
 * A default implementation of the {@link CategoryDataset3D} interface.
 */
public class DefaultCategoryDataset3D extends AbstractDataset3D  
    implements CategoryDataset3D {
  
    private DefaultKeyedValues3D data;
    
    public DefaultCategoryDataset3D() {
        this.data = new DefaultKeyedValues3D();  
    }

    @Override
    public Comparable getSeriesKey(int seriesIndex) {
        return this.data.getSeriesKey(seriesIndex);
    }

    @Override
    public Comparable getRowKey(int rowIndex) {
        return this.data.getRowKey(rowIndex);
    }

    @Override
    public Comparable getColumnKey(int columnIndex) {
        return this.data.getColumnKey(columnIndex);
    }

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
    public int getSeriesCount() {
        return this.data.getSeriesCount();
    }

    @Override
    public int getRowCount() {
        return this.data.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return this.data.getColumnCount();
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

    @Override
    public double getDoubleValue(int seriesIndex, int rowIndex, 
            int columnIndex) {
        return this.data.getDoubleValue(seriesIndex, rowIndex, columnIndex);
    }
 
    public void addSeries(Comparable seriesKey, KeyedValues data) {
        for (Comparable key : data.getKeys()) {
            setValue(data.getValue(key), seriesKey, key, seriesKey);
        }
    }
}
