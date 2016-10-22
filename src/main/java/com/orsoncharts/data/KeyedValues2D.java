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

import java.util.List;

/**
 * A two dimensional grid of data values where each value is uniquely 
 * identified by two keys (the {@code rowKey} and the 
 * {@code columnKey}).  Any instance of {@code Comparable} can be 
 * used as a key ({@code String} objects are instances of 
 * {@code Comparable}, making them convenient key objects).
 * 
 * @param <R>  The row key type.
 * @param <C>  The column key type.
 * @param <T>  The value type.
 */
public interface KeyedValues2D<R extends Comparable<R>, 
        C extends Comparable<C>, T> extends Values2D<T> {

    /**
     * Returns the row key with the specified index.
     * 
     * @param rowIndex  the row index.
     * 
     * @return The key. 
     */
    public R getRowKey(int rowIndex);

    /**
     * Returns the column key with the specified index.
     * 
     * @param columnIndex  the index.
     * 
     * @return The key. 
     */
    public C getColumnKey(int columnIndex);

    /**
     * Returns the index of the specified key, or {@code -1} if there
     * is no such key.
     * 
     * @param rowKey  the row key ({@code null} not permitted).
     * 
     * @return The index, or {@code -1}. 
     */
    public int getRowIndex(R rowKey);

    /**
     * Returns the index of the specified key, or {@code -1} if there
     * is no such key.
     * 
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The index, or {@code -1}. 
     */
    public int getColumnIndex(C columnKey);
  
    /**
     * Returns a list of the row keys (the order is significant, since data 
     * values can be accessed by index as well as by key).
     * <br><br>
     * NOTE: this method must be implemented so that modifications to the
     * returned list do not impact the underlying data structure.
     * 
     * @return A list of row keys.
     */
    public List<R> getRowKeys();

    /**
     * Returns a list of the column keys (the order is significant, since data 
     * values can be accessed by index as well as by key).
     * <br><br>
     * NOTE: this method must be implemented so that modifications to the
     * returned list do not impact the underlying data structure.
     * 
     * @return A list of column keys.
     */
    public List<C> getColumnKeys();

    /**
     * Returns the value (possibly {@code null}) associated with the 
     * specified keys.  If either or both of the keys is not defined in this
     * data structure, a runtime exception will be thrown.
     * 
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}). 
     */
    public T getValue(R rowKey, C columnKey);

}
