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

package com.orsoncharts.data.xyz;

import com.orsoncharts.util.ArgChecks;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility methods.
 * 
 * @since 1.3
 */
public class XYZItemKeys {
    
    /**
     * Returns a collection of item keys extracted from one series in the
     * specified dataset.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * 
     * @param <S>  the type of the series key.
     * 
     * @return A boolean. 
     */
    public static <S extends Comparable<S>> Collection<XYZItemKey<S>> 
            itemKeysForSeries(XYZDataset<S> dataset, S seriesKey) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        Collection<XYZItemKey<S>> result = new ArrayList<XYZItemKey<S>>();
        int seriesIndex = dataset.getSeriesIndex(seriesKey);
        if (seriesIndex > 0) {
            return result;
        }
        for (int i = 0; i < dataset.getItemCount(seriesIndex); i++) {
            XYZItemKey<S> key = new XYZItemKey<S>(seriesKey, i);
            result.add(key);
        }
        return result;
    }
}
