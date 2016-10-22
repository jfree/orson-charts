/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.data.category;

import java.util.List;
import java.io.Serializable;

import com.orsoncharts.data.AbstractDataset3D;
import com.orsoncharts.data.DefaultKeyedValues3D;
import com.orsoncharts.data.JSONUtils;
import com.orsoncharts.data.KeyedValues;
import com.orsoncharts.util.ArgChecks;

/**
 * A standard implementation of the {@link CategoryDataset3D} interface.
 * This dataset is typically used to create bar charts and stacked bar charts.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public final class StandardCategoryDataset3D
        <S extends Comparable<S>, R extends Comparable<R>, C extends Comparable<C>> 
        extends AbstractDataset3D  
        implements CategoryDataset3D<S, R, C>, Serializable {

    /**
     * Storage for the data.
     */
    private DefaultKeyedValues3D<S, R, C, Number> data;
    
    /**
     * Creates a new (empty) dataset.
     */
    public StandardCategoryDataset3D() {
        this.data = new DefaultKeyedValues3D<S, R, C, Number>();  
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
    public S getSeriesKey(int seriesIndex) {
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
    public R getRowKey(int rowIndex) {
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
    public C getColumnKey(int columnIndex) {
        return this.data.getColumnKey(columnIndex);
    }

    /**
     * Returns the index for the specified series key, or {@code -1} if the 
     * key is not defined in the dataset.
     * 
     * @param serieskey  the series key ({@code null} not permitted).
     * 
     * @return The series index or {@code -1}.
     */
    @Override
    public int getSeriesIndex(S serieskey) {
        return this.data.getSeriesIndex(serieskey);
    }

    /**
     * Returns the index of the specified row key, or {@code -1} if there
     * is no matching key.
     * 
     * @param rowkey  the row key ({@code null} not permitted).
     * 
     * @return The row index or {@code -1}. 
     */
    @Override
    public int getRowIndex(R rowkey) {
        // arg checking is covered
        return this.data.getRowIndex(rowkey);
    }

    /**
     * Returns the index of the specified column key, or {@code -1} if 
     * there is no matching key.
     * 
     * @param columnkey  the column key ({@code null} not permitted).
     * 
     * @return The column index or {@code -1}. 
     */
    @Override
    public int getColumnIndex(C columnkey) {
        // arg checking is covered
        return this.data.getColumnIndex(columnkey);
    }

    /**
     * Returns a list of the series keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the series keys (possibly empty, but never 
     *     {@code null}). 
     */
    @Override
    public List<S> getSeriesKeys() {
        return this.data.getSeriesKeys();
    }

    /**
     * Returns a list of the row keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the row keys (possibly empty, but never 
     *     {@code null}). 
     */
    @Override
    public List<R> getRowKeys() {
        return this.data.getRowKeys();
    }

    /**
     * Returns a list of the column keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the column keys (possibly empty, but never 
     *     {@code null}). 
     */
    @Override
    public List<C> getColumnKeys() {
        return this.data.getColumnKeys();
    }

    /**
     * Returns the value for a series at the specified cell (referenced by
     * row key and column key).
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}). 
     */
    @Override
    public Number getValue(S seriesKey, R rowKey, C columnKey) {
        return this.data.getValue(seriesKey, rowKey, columnKey);
    }

    /**
     * Returns the value for a series at the specified cell (referenced by 
     * row index and column index).
     * 
     * @param seriesIndex  the series index.
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The value (possibly {@code null}).
     */
    @Override
    public Number getValue(int seriesIndex, int rowIndex, int columnIndex) {
        return this.data.getValue(seriesIndex, rowIndex, columnIndex);
    }
    
    /**
     * Sets the value for a series at the specified cell (referenced by row
     * key and column key).
     * 
     * @param n  the value ({@code null} permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public void setValue(Number n, S seriesKey, R rowKey, C columnKey) {
        this.data.setValue(n, seriesKey, rowKey, columnKey);
        fireDatasetChanged();
    }
    
    /**
     * Adds a value for a series at the specified cell (referenced by row key
     * and column key).  This method simply calls {@link #setValue(
     * java.lang.Number, java.lang.Comparable, java.lang.Comparable, 
     * java.lang.Comparable) }.
     * 
     * @param n  the value ({@code null} permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public void addValue(Number n, S seriesKey, R rowKey, C columnKey) {
        setValue(n, seriesKey, rowKey, columnKey);
    }

    /**
     * Returns the value for a series at the specified cell (referenced by row
     * index and column index) as a double primitive.  If the stored data 
     * value is {@code null}, this method returns {@code Double.NaN}.
     * 
     * @param seriesIndex  the series index.
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The value (possibly {@code Double.NaN}).
     */
    @Override
    public double getDoubleValue(int seriesIndex, int rowIndex, 
            int columnIndex) {
        return this.data.getDoubleValue(seriesIndex, rowIndex, columnIndex);
    }
 
    /**
     * Adds a data series as a single row in the dataset.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param data  the data ({@code null} not permitted).
     */
    @SuppressWarnings("unchecked")
    public void addSeriesAsRow(S seriesKey, 
            KeyedValues<C, ? extends Number> data) {
        addSeriesAsRow(seriesKey, (R) seriesKey, data);
    }
    
    /**
     * Adds a data series as a single row in the dataset.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param data  the data ({@code null} not permitted).
     */
    public void addSeriesAsRow(S seriesKey, R rowKey, 
            KeyedValues<C, ? extends Number> data) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(data, "data");
        for (C key : data.getKeys()) {
            setValue(data.getValue(key), seriesKey, rowKey, key);
        }
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardCategoryDataset3D)) {
            return false;
        }
        StandardCategoryDataset3D that = (StandardCategoryDataset3D) obj;
        if (!this.data.equals(that.data)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a string representation of this instance, primarily for 
     * debugging purposes.
     * <br><br>
     * Implementation note: the current implementation (which is subject to 
     * change) writes the dataset in JSON format using 
     * {@link JSONUtils#writeKeyedValues3D(com.orsoncharts.data.KeyedValues3D)}.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return JSONUtils.writeKeyedValues3D(this);
    }
}
