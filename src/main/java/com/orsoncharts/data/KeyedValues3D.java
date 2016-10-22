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
 * A three dimensional cube of data values where each value is uniquely 
 * identified by three keys (the {@code seriesKey}, {@code rowKey} 
 * and {@code columnKey}).
 * 
 * @param <S> The series key type.
 * @param <R> The row key type.
 * @param <C> The column key type.
 * @param <T> The value type.
 */
public interface KeyedValues3D<S extends Comparable<S>, 
        R extends Comparable<R>, C extends Comparable<C>, T> 
        extends Values3D<T> {

    /**
     * Returns a list of the series keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the series keys (possibly empty, but never 
     *     {@code null}). 
     */
    List<S> getSeriesKeys();

    /**
     * Returns a list of the row keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the row keys (possibly empty, but never 
     *     {@code null}). 
     */
    List<R> getRowKeys();
    
    /**
     * Returns a list of the column keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the column keys (possibly empty, but never 
     *     {@code null}). 
     */
    List<C> getColumnKeys();
    
    /**
     * Returns the series key with the specified index.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The key. 
     */
    S getSeriesKey(int seriesIndex);

    /**
     * Returns the row key with the specified index.
     * 
     * @param rowIndex  the row index.
     * 
     * @return The key. 
     */    
    R getRowKey(int rowIndex);

    /**
     * Returns the column key with the specified index.
     * 
     * @param columnIndex  the column index.
     * 
     * @return The key. 
     */    
    C getColumnKey(int columnIndex);

    /**
     * Returns the index of the specified series key, or {@code -1} if
     * there is no matching key.
     * 
     * @param serieskey  the series key ({@code null} not permitted).
     * 
     * @return The key index, or {@code -1}. 
     */
    int getSeriesIndex(S serieskey);

    /**
     * Returns the index of the specified row key, or {@code -1} if there
     * is no matching key.
     * 
     * @param rowkey  the row key ({@code null} not permitted).
     * 
     * @return The row index or {@code -1}. 
     */
    int getRowIndex(R rowkey);

    /**
     * Returns the index of the specified column key, or {@code -1} if 
     * there is no matching key.
     * 
     * @param columnkey  the column key ({@code null} not permitted).
     * 
     * @return The column index or {@code -1}. 
     */
    int getColumnIndex(C columnkey);

    /**
     * Returns the value for a given series, row and column.
     * 
     * @param seriesKey the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}).
     */
    T getValue(S seriesKey, R rowKey, C columnKey);
    
}
