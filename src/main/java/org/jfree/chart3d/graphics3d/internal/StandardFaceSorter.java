/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.graphics3d.internal;

import java.io.Serializable;
import java.util.List;
import org.jfree.chart3d.graphics3d.Face;
import org.jfree.chart3d.graphics3d.Point3D;

/**
 * A face sorter that orders the faces by z-value.
 * 
 * @since 1.3
 */
public class StandardFaceSorter implements FaceSorter, Serializable {

    /**
     * Creates a new instance.
     */
    public StandardFaceSorter() {
        // nothing to do    
    }
    
    @Override
    public List<Face> sort(List<Face> faces, Point3D[] eyePts) {
        faces.sort(new ZOrderComparator(eyePts));
        return faces;
    }
    
}
