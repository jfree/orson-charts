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

import com.orsoncharts.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Some tests for the {@link ViewPoint3D} class.
 */
public class ViewPoint3DTest {
    
    private static final double EPSILON = 0.000001;
    
    @Test
    public void testGeneral() {
        ViewPoint3D v1 = new ViewPoint3D(0, 0, 1, 0);
        assertEquals(0, v1.getX(), EPSILON);
        assertEquals(0, v1.getY(), EPSILON);
        assertEquals(1, v1.getZ(), EPSILON);
        assertEquals(0, v1.calcRollAngle(), EPSILON);
        
        v1 = new ViewPoint3D(Point3D.UNIT_X, 0.0);
        assertEquals(0, v1.getTheta(), EPSILON);
        assertEquals(Math.PI / 2, v1.getPhi(), EPSILON);
        assertEquals(1, v1.getRho(), EPSILON);
        assertEquals(0, v1.calcRollAngle(), EPSILON);

        v1 = new ViewPoint3D(Point3D.UNIT_Y, 0.0);
        assertEquals(Math.PI / 2, v1.getTheta(), EPSILON);
        assertEquals(Math.PI / 2, v1.getPhi(), EPSILON);
        assertEquals(1, v1.getRho(), EPSILON);
        assertEquals(0, v1.calcRollAngle(), EPSILON);

        v1 = new ViewPoint3D(Point3D.UNIT_Z, 0.0);
        assertEquals(0, v1.getTheta(), EPSILON);
        assertEquals(0, v1.getPhi(), EPSILON);
        assertEquals(1, v1.getRho(), EPSILON);
        assertEquals(0, v1.calcRollAngle(), EPSILON);
    
        v1 = new ViewPoint3D(1, 2, 3, 4);
        ViewPoint3D v2 = new ViewPoint3D(v1.getPoint(), v1.calcRollAngle());
        assertEquals(v1.getTheta(), v2.getTheta(), EPSILON);
        assertEquals(v1.getPhi(), v2.getPhi(), EPSILON);
        assertEquals(v1.getRho(), v2.getRho(), EPSILON);
    }
    
    @Test
    public void testConstructor() {
        ViewPoint3D v1 = new ViewPoint3D(-1.675516f, -2.6179938f, 25.874374f, 
                0);
        ViewPoint3D v2 = new ViewPoint3D(v1.getPoint(), v1.calcRollAngle());
        assertEquals(v1.getX(), v2.getX(), EPSILON);
        assertEquals(v1.getY(), v2.getY(), EPSILON);
        assertEquals(v1.getZ(), v2.getZ(), EPSILON);
    }
    
    @Test
    public void testEquals() {
        ViewPoint3D vp1 = new ViewPoint3D(1.0, 2.0, 3.0, 4.0);
        ViewPoint3D vp2 = new ViewPoint3D(1.0, 2.0, 3.0, 4.0);
        assertTrue(vp1.equals(vp2));
        
        vp1 = new ViewPoint3D(1.1, 2.0, 3.0, 4.0);
        assertFalse(vp1.equals(vp2));
        vp2 = new ViewPoint3D(1.1, 2.0, 3.0, 4.0);
        assertTrue(vp1.equals(vp2));
        
        vp1 = new ViewPoint3D(1.1, 2.2, 3.0, 4.0);
        assertFalse(vp1.equals(vp2));
        vp2 = new ViewPoint3D(1.1, 2.2, 3.0, 4.0);
        assertTrue(vp1.equals(vp2));
        
        vp1 = new ViewPoint3D(1.1, 2.2, 3.3, 4.0);
        assertFalse(vp1.equals(vp2));
        vp2 = new ViewPoint3D(1.1, 2.2, 3.3, 4.0);
        assertTrue(vp1.equals(vp2));

        vp1 = new ViewPoint3D(1.1, 2.2, 3.3, 4.4);
        assertFalse(vp1.equals(vp2));
        vp2 = new ViewPoint3D(1.1, 2.2, 3.3, 4.4);
        assertTrue(vp1.equals(vp2));
        
        vp1.moveUpDown(1.0);
        assertFalse(vp1.equals(vp2));
        vp2.moveUpDown(1.0);
        assertTrue(vp1.equals(vp2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization() {
        ViewPoint3D vp1 = new ViewPoint3D(1.0, 2.0, 3.0, 4.0);
        ViewPoint3D vp2 = (ViewPoint3D) TestUtils.serialized(vp1);
        assertEquals(vp1, vp2);
    }

    @Test
    public void testZ() {
        Point3D p = new Point3D(0, 0, 25);
        ViewPoint3D v1 = new ViewPoint3D(p, 0);
        assertEquals(0, v1.getX(), EPSILON);
        assertEquals(0, v1.getY(), EPSILON);
        assertEquals(25, v1.getZ(), EPSILON);
        assertEquals(0, v1.getTheta(), EPSILON); 
    }
}
