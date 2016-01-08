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

import java.io.Serializable;
import com.orsoncharts.Range;
import com.orsoncharts.data.xyz.XYZDatasetUtils;

/**
 * Represents a function {@code y = f(x, z)}.  
 * <br><br>
 * A dataset can be created by sampling a function - see the 
 * {@link XYZDatasetUtils#sampleFunction(Function3D, String, Range, double, Range, double)} 
 * method.  In addition, any {@code Function3D} instance can be plotted
 * by a {@link com.orsoncharts.renderer.xyz.SurfaceRenderer}.
 */
public interface Function3D extends Serializable {

    /**
     * Returns the value of the function ('y') for the specified inputs ('x' 
     * and 'z').
     *
     * @param x  the x-value.
     * @param z  the z-value.
     *
     * @return The function value.
     */
    public double getValue(double x, double z);

}
