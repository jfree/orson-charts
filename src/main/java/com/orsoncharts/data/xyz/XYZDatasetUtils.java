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

import com.orsoncharts.Range;
import com.orsoncharts.data.function.Function3D;

/**
 * A collection of utility methods related to {@link XYZDataset}.
 */
public class XYZDatasetUtils {
    
    /**
     * Creates an {@link XYZDataset} by sampling a {@link Function3D} over
     * a specified range.
     * 
     * @param f  the function ({@code null} not permitted).
     * @param key  the series key ({@code null} not permitted).
     * @param xrange  the range of x values ({@code null} not permitted).
     * @param xcount  the number of x samples (must be at least 2).
     * @param zrange  the range of z values ({@code null} not permitted).
     * @param zcount  the number of z-samples (must be at least 2).
     * 
     * @return A dataset containing sampled values from the function.
     */
    public static XYZDataset<String> sampleFunction(Function3D f, String key, 
            Range xrange, double xcount, Range zrange, double zcount) {
        return sampleFunction(f, key, xrange.getMin(), xrange.getMax(),
                xcount, zrange.getMin(), zrange.getMax(), zcount);
    }
    
    /**
     * Creates an {@link XYZDataset} by sampling a {@link Function3D} over
     * a specified range.
     * 
     * @param f  the function ({@code null} not permitted).
     * @param key  the series key ({@code null} not permitted).
     * @param xmin  the lower bound of the x-range.
     * @param xmax  the upper bound of the x-range.
     * @param xcount  the number of x samples (must be at least 2).
     * @param zmin  the lower bound of the z-range.
     * @param zmax  the upper bound of the z-range.
     * @param zcount  the number of z-samples (must be at least 2).
     * 
     * @return A dataset containing sampled values from the function.
     */
    public static XYZDataset<String> sampleFunction(Function3D f, String key, 
            double xmin, double xmax, double xcount, 
            double zmin, double zmax, double zcount) {
        XYZSeries<String> s = new XYZSeries<String>(key);
        for (double x = xmin; x <= xmax; x += (xmax - xmin) / xcount) {
            for (double z = zmin; z <= zmax; z += (zmax - zmin) / zcount) {
                s.add(x, f.getValue(x, z), z);
            }
        }
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        dataset.add(s);
        return dataset;
    }
    
}
