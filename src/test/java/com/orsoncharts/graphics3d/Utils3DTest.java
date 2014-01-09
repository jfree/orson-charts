/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.graphics3d;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Some tests for the methods in {@link Utils3D}.
 */
public class Utils3DTest {
   
    private static final double EPSILON = 0.00000001;

    @Test
    public void testLength() {
        assertEquals(1, Utils3D.length(Point3D.UNIT_X), EPSILON);
        assertEquals(1, Utils3D.length(Point3D.UNIT_Y), EPSILON);
        assertEquals(1, Utils3D.length(Point3D.UNIT_Z), EPSILON);
        assertEquals(0, Utils3D.length(Point3D.ORIGIN), EPSILON);
    }
    
    @Test
    public void testAngle() {
        assertEquals(Math.PI / 2, Utils3D.angle(Point3D.UNIT_X, Point3D.UNIT_Y),
                EPSILON);
    }
}
