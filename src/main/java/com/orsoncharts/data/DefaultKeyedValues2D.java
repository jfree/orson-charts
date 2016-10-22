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
 * A two dimensional grid of (typically numerical) data that is accessible by 
 * row and column keys.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @param <R> the row key type
 * @param <C> the column key type
 * @param <T> the value type.
 * 
 */
@SuppressWarnings("serial")
public final class DefaultKeyedValues2D<R extends Comparable<R>, C extends Comparable<C>, T> 
        implements KeyedValues2D<R, C, T>, Serializable {

    /** The row keys. */
    List<R> rowKeys;
    
    /** The column keys. */
    List<C> columnKeys;
    
    /** The data values. */
    List<DefaultKeyedValues<C, T>> data;  // one entry per row key
  
    /**
     * Creates a new (empty) instance.
     */
    public DefaultKeyedValues2D() {
        this(new ArrayList<R>(), new ArrayList<C>());
    }
    
    /**
     * Creates a new instance with the specified keys and all data values 
     * initialized to {@code null}.
     * 
     * @param rowKeys  the xKeys ({@code null} not permitted).
     * @param columnKeys  the yKeys ({@code null} not permitted).
     */
    public DefaultKeyedValues2D(List<R> rowKeys, List<C> columnKeys) {
        ArgChecks.nullNotPermitted(rowKeys, "rowKeys");
        ArgChecks.nullNotPermitted(columnKeys, "columnKeys");
        this.rowKeys = new ArrayList<R>(rowKeys);
        this.columnKeys = new ArrayList<C>(columnKeys);
        this.data = new ArrayList<DefaultKeyedValues<C, T>>();    
        for (int i = 0; i < rowKeys.size(); i++) {
            this.data.add(new DefaultKeyedValues<C, T>(columnKeys));
        }
    }

    /**
     * Returns the row key corresponding to the specified index.
     * 
     * @param rowIndex  the row index.
     * 
     * @return The key. 
     */
    @Override
    public R getRowKey(int rowIndex) {
        return this.rowKeys.get(rowIndex);
    }

    /**
     * Returns the column key corresponding to the specified index.
     * 
     * @param columnIndex  the column index.
     * 
     * @return The key. 
     */
    @Override
    public C getColumnKey(int columnIndex) {
        return this.columnKeys.get(columnIndex);
    }

    /**
     * Returns the index corresponding to the specified row key.
     * 
     * @param rowKey  the row key ({@code null} not permitted).
     * 
     * @return The index. 
     */
    @Override
    public int getRowIndex(R rowKey) {
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        return this.rowKeys.indexOf(rowKey);
    }

    /**
     * Returns the index corresponding to the specified column key.
     * 
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The index. 
     */
    @Override
    public int getColumnIndex(C columnKey) {
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        return this.columnKeys.indexOf(columnKey);
    }

    /**
     * Returns a copy of the list of row keys.
     * 
     * @return A copy of the list of row keys (never {@code null}). 
     */
    @Override
    public List<R> getRowKeys() {
        return new ArrayList<R>(this.rowKeys);
    }

    /**
     * Returns a copy of the list of column keys.
     * 
     * @return A copy of the list of column keys (never {@code null}). 
     */
    @Override
    public List<C> getColumnKeys() {
        return new ArrayList<C>(this.columnKeys);
    }

    /**
     * Returns the number of row keys in the table.
     * 
     * @return The number of row keys in the table.
     */
    @Override
    public int getRowCount() {
        return this.rowKeys.size();
    }
    
    /**
     * Returns the number of column keys in the data structure.
     * 
     * @return The number of column keys.
     */
    @Override
    public int getColumnCount() {
        return this.columnKeys.size();
    }

    /**
     * Returns a value from one cell in the table.
     * 
     * @param rowKey  the row-key ({@code null} not permitted).
     * @param columnKey  the column-key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}).
     */
    @Override
    public T getValue(R rowKey, C columnKey) {
        // arg checking is handled in getXIndex() and getYIndex()
        int rowIndex = getRowIndex(rowKey);
        int columnIndex = getColumnIndex(columnKey);
        return getValue(rowIndex, columnIndex);
    }

    /**
     * Returns the value from one cell in the table.
     * 
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The value (possibly {@code null}). 
     */
    @Override
    public T getValue(int rowIndex, int columnIndex) {
        return this.data.get(rowIndex).getValue(columnIndex);
    }

    /**
     * Returns the data item at the specified position as a double primitive.
     * Where the {@link #getValue(int, int)} method returns {@code null}, 
     * this method returns {@code Double.NaN}.
     * 
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The data value.
     */
    @Override
    public double getDoubleValue(int rowIndex, int columnIndex) {
        T n = getValue(rowIndex, columnIndex);
        if (n != null && n instanceof Number) {
            return ((Number) n).doubleValue();
        }
        return Double.NaN;
    } 

    /**
     * Sets a value for one cell in the table.
     * 
     * @param n  the value ({@code null} permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public void setValue(T n, R rowKey, C columnKey) {
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        
        if (this.data.isEmpty()) {  // 1. no data - just add one new entry
            this.rowKeys.add(rowKey);
            this.columnKeys.add(columnKey);
            DefaultKeyedValues<C, T> dkvs = new DefaultKeyedValues<C, T>();
            dkvs.put(columnKey, n);
            this.data.add(dkvs);
        } else {
            int rowIndex = getRowIndex(rowKey);
            int columnIndex = getColumnIndex(columnKey);
            if (rowIndex >= 0) {
                DefaultKeyedValues<C, T> dkvs = this.data.get(rowIndex);
                if (columnIndex >= 0) {
                    // 2.  Both keys exist - just update the value
                    dkvs.put(columnKey, n);
                } else {
                    // 3.  rowKey exists, but columnKey does not (add the 
                    //     columnKey to each series)
                    this.columnKeys.add(columnKey);
                    for (DefaultKeyedValues<C, T> kv : this.data) {
                        kv.put(columnKey, null);
                    }
                    dkvs.put(columnKey, n);
                }
            } else {
                if (columnIndex >= 0) {
                    // 4.  rowKey does not exist, but columnKey does
                    this.rowKeys.add(rowKey);
                    DefaultKeyedValues<C, T> d = new DefaultKeyedValues<C, T>(
                            this.columnKeys);
                    d.put(columnKey, n);
                    this.data.add(d);
                } else {
                    // 5.  neither key exists, need to create the new series, 
                    //     plus the new entry in every series
                    this.rowKeys.add(rowKey);
                    this.columnKeys.add(columnKey);
                    for (DefaultKeyedValues<C, T> kv : this.data) {
                        kv.put(columnKey, null);
                    }
                    DefaultKeyedValues<C, T> d = new DefaultKeyedValues<C, T>(
                            this.columnKeys);
                    d.put(columnKey, n);
                    this.data.add(d);
                }
            }
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultKeyedValues2D)) {
            return false;
        }
        DefaultKeyedValues2D that = (DefaultKeyedValues2D) obj;
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
