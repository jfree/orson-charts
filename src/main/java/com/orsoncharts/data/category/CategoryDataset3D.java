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

import com.orsoncharts.data.Dataset3D;
import com.orsoncharts.data.KeyedValues3D;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * An interface for a dataset with multiple series of data in the form of
 * {@code (rowKey, columnKey, value)}.  This is the standard data 
 * interface used by the {@link CategoryPlot3D} class. 
 * 
 * @param <S>  the series key type (must implement Comparable)
 * @param <R>  the row key type (must implement Comparable)
 * @param <C>  the column key type (must implement Comparable)
 */
public interface CategoryDataset3D<S extends Comparable<S>, 
        R extends Comparable<R>, C extends Comparable<C>> 
        extends KeyedValues3D<S, R, C, Number>, Dataset3D {

}
