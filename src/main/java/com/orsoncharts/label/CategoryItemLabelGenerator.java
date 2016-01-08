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

package com.orsoncharts.label;

import com.orsoncharts.data.category.CategoryDataset3D;

/**
 * An item label generator for category charts (this is used for tooltips and
 * data item labels).  Note that this generator is used for individual data
 * items, and is distinct from the {@link CategoryLabelGenerator} which
 * generates labels for series keys, column keys and row keys.  
 * The {@link StandardCategoryItemLabelGenerator} class provides the default 
 * implementation.
 * 
 * @since 1.3
 */
public interface CategoryItemLabelGenerator {

    /**
     * Generates a label for one series in a {@link CategoryDataset3D}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The series label (possibly {@code null}).
     */
    String generateItemLabel(CategoryDataset3D dataset, 
            Comparable<?> seriesKey, Comparable<?> rowKey, 
            Comparable<?> columnKey);

}