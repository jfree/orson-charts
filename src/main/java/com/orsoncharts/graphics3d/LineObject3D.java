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

import java.awt.Color;

/**
 * A line between two points in 3D space.
 * 
 * @since 1.5
 */
public class LineObject3D extends Object3D {
    
    /**
     * Creates a new {@code Line3D} instance.
     * 
     * @param x0  the x-coordinate for the start of the line.
     * @param y0  the y-coordinate for the start of the line.
     * @param z0  the z-coordinate for the start of the line.
     * @param x1  the x-coordinate for the end of the line.
     * @param y1  the y-coordinate for the end of the line.
     * @param z1  the z-coordinate for the end of the line.
     * @param color  the color ({@code null} not permitted).
     */
    public LineObject3D(float x0, float y0, float z0, float x1, float y1, float z1, 
            Color color) {
        super(color);
        addVertex(x0, y0, z0);
        addVertex(x1, y1, z1);
        addFace(new Face(this, new int[] {0, 1}));
    }

}
