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

/**
 * A generic representation of a two dimensional grid of data values.
 * 
 * @param <T> the type of value stored in the grid.
 */
public interface Values2D<T> {
    
    /**
     * Returns the number of rows in the grid.
     * 
     * @return The number of rows in the grid. 
     */
    int getRowCount();
  
    /**
     * Returns the number of columns in the grid.
     * 
     * @return The number of columns in the grid. 
     */
    int getColumnCount();
  
    /**
     * Returns the data item at the specified position.
     * 
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The data value (possibly {@code null}).
     */
    T getValue(int rowIndex, int columnIndex);

    /**
     * Returns the data value at the specified position as a double primitive,
     * or {@code Double.NaN} if the value is not an instance of 
     * {@code Number}.  Where the {@link #getValue(int, int)} method 
     * returns {@code null}, this method returns {@code Double.NaN}.
     * 
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The data value.
     */
    double getDoubleValue(int rowIndex, int columnIndex);

}
