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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {link Point3D} class.
 */
public class Point3DTest {
    
    @Test
    public void testEquals() {
        Point3D p1 = new Point3D(1.0, 0.0, 0.0);
        Point3D p2 = new Point3D(1.0, 0.0, 0.0);
        assertTrue(p1.equals(p2));
        
        p1 = new Point3D(1.1, 0.0, 0.0);
        assertFalse(p1.equals(p2));
        p2 = new Point3D(1.1, 0.0, 0.0);
        assertTrue(p1.equals(p2));

        p1 = new Point3D(1.1, 2.2, 0.0);
        assertFalse(p1.equals(p2));
        p2 = new Point3D(1.1, 2.2, 0.0);
        assertTrue(p1.equals(p2));
    
        p1 = new Point3D(1.1, 2.2, 3.3);
        assertFalse(p1.equals(p2));
        p2 = new Point3D(1.1, 2.2, 3.3);
        assertTrue(p1.equals(p2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization() {
        Point3D p1 = new Point3D(1.0, 2.0, 3.0);
        Point3D p2 = (Point3D) TestUtils.serialized(p1);
        assertEquals(p1, p2);
    }
}
