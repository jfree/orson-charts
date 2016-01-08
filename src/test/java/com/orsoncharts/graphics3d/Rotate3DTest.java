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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author dgilbert
 */
public class Rotate3DTest {
    
    private static final double EPSILON = 0.0000001;
    
    @Test
    public void testGeneral() {
        Rotate3D r = new Rotate3D(Point3D.ORIGIN, Point3D.UNIT_X, Math.PI);
        Point3D p = r.applyRotation(1, 1, 1);
        assertEquals(1, p.x, EPSILON);
        assertEquals(-1, p.y, EPSILON);
        assertEquals(-1, p.z, EPSILON);
    }
    
    @Test
    public void test2() {
        ViewPoint3D v1 = new ViewPoint3D(-1.675516f, -2.6179938f, 25.874374f, 0);
        Rotate3D r = new Rotate3D(Point3D.ORIGIN, v1.getHorizontalRotationAxis(), -Math.PI / 60);
        Point3D p = r.applyRotation(v1.getPoint());
        ViewPoint3D v2 = new ViewPoint3D(p, 0);
    }
}
