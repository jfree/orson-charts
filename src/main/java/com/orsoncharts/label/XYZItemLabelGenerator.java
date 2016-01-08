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

import com.orsoncharts.data.xyz.XYZDataset;

/**
 * A label generator for data items in XYZ charts (this can be used for 
 * tooltips and item labels).  The ({@link StandardXYZItemLabelGenerator}) 
 * class provides the default implementation.
 * <br><br>
 * Implementations of this interface are expected to support cloning and
 * serialization.
 * 
 * @since 1.3
 */
public interface XYZItemLabelGenerator {
    
    /**
     * Generates an item label for one data item in an {@link XYZDataset}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param itemIndex  the item index.
     * 
     * @return The item label (possibly {@code null}).
     */
    String generateItemLabel(XYZDataset dataset, Comparable<?> seriesKey,
            int itemIndex);
    
}
