/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
