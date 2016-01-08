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

package com.orsoncharts.data.function;

import com.orsoncharts.Range;
import com.orsoncharts.util.ArgChecks;

/**
 * Utility methods related to {@link Function3D}.
 * 
 * @since 1.1
 */
public class Function3DUtils {
    
    private Function3DUtils() {
        // no need to instantiate this
    }
    
    /**
     * Returns the range of y-values in the function by sampling.
     * 
     * @param f  the function ({@code null} not permitted).
     * @param xRange  the x-range to sample ({@code null} not permitted).
     * @param zRange  the z-range to sample ({@code null} not permitted).
     * @param xSamples  the number of x-samples (must be at least 2).
     * @param zSamples  the number of z-samples (must be at least 2).
     * @param ignoreNaN  if {@code true}, any {@code NaN} values will
     *     be ignored.
     * 
     * @return The range ({@code null} in the case that the function 
     *     returns no valid values). 
     */
    public static Range findYRange(Function3D f, Range xRange, Range zRange, 
            int xSamples, int zSamples, boolean ignoreNaN) {
        ArgChecks.nullNotPermitted(f, "f");
        ArgChecks.nullNotPermitted(xRange, "xRange");
        ArgChecks.nullNotPermitted(zRange, "zRange");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int xIndex = 0; xIndex <= xSamples - 1; xIndex++) {
            double fracX = xIndex / (xSamples - 1.0);
            double x = xRange.value(fracX);
            for (int zIndex = 0; zIndex <= zSamples - 1; zIndex++) {
                double fracZ = zIndex / (zSamples - 1.0);
                double z = zRange.value(fracZ);
                double y = f.getValue(x, z);
                if (Double.isNaN(y) && ignoreNaN) {
                    continue;
                }
                min = Math.min(y, min);
                max = Math.max(y, max);
            }
        }
        if (min <= max) {
            return new Range(min, max);
        }
        return null;
    }
}
