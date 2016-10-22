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

package com.orsoncharts.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.util.ArgChecks;

/**
 * A three dimensional table of numerical values, implementing the 
 * {@link KeyedValues3D} interface.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @param <S>  the series key (must implement Comparable).
 * @param <R>  the row key (must implement Comparable).
 * @param <C>  the column key (must implement Comparable).
 * @param <V>  the value type.
 */
@SuppressWarnings("serial")
public final class DefaultKeyedValues3D<S extends Comparable<S>, R extends Comparable<R>, C extends Comparable<C>, V> 
        implements KeyedValues3D<S, R, C, V>, Serializable {

    /** The series keys. */
    private List<S> seriesKeys;
  
    /** The row keys. */
    private List<R> rowKeys;
  
    /** The column keys. */
    private List<C> columnKeys;

    /**
     * The data, one entry per series.  Each series *must* contain the same
     * row and column keys.
     */
    private List<DefaultKeyedValues2D<R, C, V>> data; // one entry per series
  
    /**
     * Creates a new (empty) table.
     */
    public DefaultKeyedValues3D() {
        this.seriesKeys = new ArrayList<S>();
        this.rowKeys = new ArrayList<R>();
        this.columnKeys = new ArrayList<C>();
        this.data = new ArrayList<DefaultKeyedValues2D<R, C, V>>();
    }
  
    /**
     * Returns the series key with the specified index.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The series key. 
     */
    @Override
    public S getSeriesKey(int seriesIndex) {
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
    public R getRowKey(int rowIndex) {
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
    public C getColumnKey(int columnIndex) {
        return this.columnKeys.get(columnIndex);
    }

    /**
     * Returns the index for the specified series key, or {@code -1} if 
     * the key is not present in this data structure.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * 
     * @return The series index or {@code -1}. 
     */
    @Override
    public int getSeriesIndex(S seriesKey) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        return this.seriesKeys.indexOf(seriesKey);
    }

    /**
     * Returns the index for the specified row key, or {@code -1} if 
     * the key is not present in this data structure.
     * 
     * @param rowKey  the row key ({@code null} not permitted).
     * 
     * @return The row index or {@code -1}. 
     */
    @Override
    public int getRowIndex(R rowKey) {
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        return this.rowKeys.indexOf(rowKey);
    }

    /**
     * Returns the index for the specified column key, or {@code -1} if 
     * the key is not present in this data structure.
     * 
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The column index or {@code -1}. 
     */
    @Override
    public int getColumnIndex(C columnKey) {
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        return this.columnKeys.indexOf(columnKey);
    }

    /**
     * Returns a list of the series keys for the data.  Modifying this
     * list will have no impact on the underlying data.
     * 
     * @return A list of the series keys (possibly empty, but never 
     *     {@code null}). 
     */
    @Override
    public List<S> getSeriesKeys() {
        return new ArrayList<S>(this.seriesKeys);
    }

    /**
     * Returns a list of the row keys for the data.  Modifying this
     * list will have no impact on the underlying data.
     * 
     * @return A list of the row keys (possibly empty, but never 
     *     {@code null}). 
     */
    @Override
    public List<R> getRowKeys() {
        return new ArrayList<R>(this.rowKeys);
    }

    /**
     * Returns a list of the column keys for the data.  Modifying this
     * list will have no impact on the underlying data.
     * 
     * @return A list of the column keys (possibly empty, but never 
     *     {@code null}). 
     */
    @Override
    public List<C> getColumnKeys() {
        return new ArrayList<C>(this.columnKeys);
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
    
    /**
     * Returns the value for the specified data item.  This method will 
     * throw an {@code IllegalArgumentException} if the dataset does not 
     * contain the specified keys.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}). 
     */
    @Override
    public V getValue(S seriesKey, R rowKey, C columnKey) {
        int seriesIndex = getSeriesIndex(seriesKey);
        if (seriesIndex < 0) {
            throw new IllegalArgumentException("Series '" + seriesKey.toString() 
                    + "' is not found.");
        }
        int rowIndex = getRowIndex(rowKey);
        if (rowIndex < 0) {
            throw new IllegalArgumentException("Row key '" + rowKey.toString() 
                    + "' is not found.");
        }
        int columnIndex = getColumnIndex(columnKey);
        if (columnIndex < 0) {
            throw new IllegalArgumentException("Column key '" 
                    + columnKey.toString() + "' is not found.");
        }
        return getValue(seriesIndex, rowIndex, columnIndex);
    }

    @Override
    public double getDoubleValue(int seriesIndex, int rowIndex, 
            int columnIndex) {
        V n = getValue(seriesIndex, rowIndex, columnIndex);
        if (n != null && n instanceof Number) {
            return ((Number) n).doubleValue();
        }
        return Double.NaN;
    }
    
    /**
     * Sets the value for an item in a series, overwriting any existing value.
     * 
     * @param n  the value ({@code null} permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public void setValue(V n, S seriesKey, R rowKey, C columnKey) {
        
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        
        // cases:
        // 1 - the dataset is empty, so we just need to add a new layer with the
        //     given keys;
        if (this.data.isEmpty()) {
            this.seriesKeys.add(seriesKey);
            this.rowKeys.add(rowKey);
            this.columnKeys.add(columnKey);
            DefaultKeyedValues2D<R, C, V> d 
                    = new DefaultKeyedValues2D<R, C, V>();
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
            for (DefaultKeyedValues2D<R, C, V> d : this.data) {
                d.setValue(null, rowKey, columnKey);
            } 
        } 
        if (seriesIndex >= 0) {
            DefaultKeyedValues2D<R, C, V> d = this.data.get(seriesIndex);
            d.setValue(n, rowKey, columnKey);
        } else {
            this.seriesKeys.add(seriesKey);
            DefaultKeyedValues2D<R, C, V> d 
                    = new DefaultKeyedValues2D<R, C, V>(this.rowKeys, 
                    this.columnKeys);
            d.setValue(n, rowKey, columnKey);
            this.data.add(d);
        }
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultKeyedValues3D)) {
            return false;
        }
        DefaultKeyedValues3D that = (DefaultKeyedValues3D) obj;
        if (!this.seriesKeys.equals(that.seriesKeys)) {
            return false;
        }
        if (!this.rowKeys.equals(that.rowKeys)) {
            return false;
        }
        if (!this.columnKeys.equals(that.columnKeys)) {
            return false;
        }
        if (!this.data.equals(that.data)) {
            return false;
        }
        return true;
    }

}
