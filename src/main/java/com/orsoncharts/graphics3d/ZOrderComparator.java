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

package com.orsoncharts.graphics3d;

import java.util.Comparator;

/**
 * A comparator that orders {@link Face} instances by Z-order.
 */
public class ZOrderComparator implements Comparator<Face> {

    Point3D[] pts;
    
    /**
     * Creates a new comparator.
     * 
     * @param pts  the points. 
     */
    public ZOrderComparator(Point3D[] pts) {
        this.pts = pts;
    }
    
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Face f1, Face f2) {
        double z1 = f1.calculateAverageZValue(this.pts);
        double z2 = f2.calculateAverageZValue(this.pts);
        if (z1 > z2) {
            return 1;
        } else if (z2 > z1) {
            return -1;
        } else {
            return 0;
        }
    }
   
}
