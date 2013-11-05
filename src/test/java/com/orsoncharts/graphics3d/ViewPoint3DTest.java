/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

import static org.junit.Assert.assertEquals;
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
        assertEquals(0, v1.getAngle(), EPSILON);
        
        v1 = new ViewPoint3D(Point3D.UNIT_X, 0.0);
        assertEquals(0, v1.getTheta(), EPSILON);
        assertEquals(Math.PI / 2, v1.getPhi(), EPSILON);
        assertEquals(1, v1.getRho(), EPSILON);
        assertEquals(0, v1.getAngle(), EPSILON);

        v1 = new ViewPoint3D(Point3D.UNIT_Y, 0.0);
        assertEquals(Math.PI / 2, v1.getTheta(), EPSILON);
        assertEquals(Math.PI / 2, v1.getPhi(), EPSILON);
        assertEquals(1, v1.getRho(), EPSILON);
        assertEquals(0, v1.getAngle(), EPSILON);

        v1 = new ViewPoint3D(Point3D.UNIT_Z, 0.0);
        assertEquals(0, v1.getTheta(), EPSILON);
        assertEquals(0, v1.getPhi(), EPSILON);
        assertEquals(1, v1.getRho(), EPSILON);
        assertEquals(0, v1.getAngle(), EPSILON);
    
        v1 = new ViewPoint3D(1, 2, 3, 4);
        ViewPoint3D v2 = new ViewPoint3D(v1.getPoint(), v1.getAngle());
        assertEquals(v1.getTheta(), v2.getTheta(), EPSILON);
        assertEquals(v1.getPhi(), v2.getPhi(), EPSILON);
        assertEquals(v1.getRho(), v2.getRho(), EPSILON);
    }
    
    @Test
    public void testX() {
        ViewPoint3D v1 = new ViewPoint3D(-1.675516f, -2.6179938f, 25.874374f, 0);
        ViewPoint3D v2 = new ViewPoint3D(v1.getPoint(), v1.getAngle());
        assertEquals(v1.getX(), v2.getX(), EPSILON);
        assertEquals(v1.getY(), v2.getY(), EPSILON);
        assertEquals(v1.getZ(), v2.getZ(), EPSILON);
    }
    
    @Test
    public void testZ() {
        Point3D p = new Point3D(0, 0, 25);
        ViewPoint3D v1 = new ViewPoint3D(p, 0);
        assertEquals(0, v1.getX(), EPSILON);
        assertEquals(0, v1.getY(), EPSILON);
        assertEquals(25, v1.getZ(), EPSILON);
        assertEquals(Math.PI / 2, v1.getTheta(), EPSILON);
        
        p = new Point3D(1.5308084466174516E-15, -4.592425445731473E-15, 25.0);
        v1 = new ViewPoint3D(p, 0);
        assertEquals(Math.PI / 2, v1.getTheta(), EPSILON);
        assertEquals(0, v1.getX(), EPSILON);
        assertEquals(0, v1.getY(), EPSILON);
        assertEquals(-25, v1.getZ(), EPSILON);
    }
}
