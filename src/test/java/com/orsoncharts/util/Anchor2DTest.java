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

package com.orsoncharts.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import com.orsoncharts.TestUtils;
import com.orsoncharts.graphics3d.Offset2D;

import org.junit.Test;

/**
 * Some checks for the {@link Anchor2D} class.
 */
public class Anchor2DTest {
    
    private static final double EPSILON = 0.00000001;
    @Test
    public void testGeneral() {
        Anchor2D anchor = new Anchor2D(RefPt2D.BOTTOM_LEFT, 
                new Offset2D(5.0, 10.0));
        Point2D pt = anchor.getAnchorPoint(new Rectangle(10, 20, 30, 40));
        assertEquals(new Point2D.Double(15, 50), pt);
    }
    
    /**
     * Checks for the equals() method.
     */
    @Test
    public void testEquals() {
        Anchor2D a1 = new Anchor2D(RefPt2D.BOTTOM_CENTER, 
                new Offset2D(1.0, 2.0));
        Anchor2D a2 = new Anchor2D(RefPt2D.BOTTOM_CENTER, 
                new Offset2D(1.0, 2.0));
        assertTrue(a1.equals(a2));
        assertFalse(a1.equals(null));
        
        a1 = new Anchor2D(RefPt2D.CENTER, new Offset2D(1.0, 2.0));
        assertFalse(a1.equals(a2));
        a2 = new Anchor2D(RefPt2D.CENTER, new Offset2D(1.0, 2.0));
        assertTrue(a1.equals(a2));
        
        a1 = new Anchor2D(RefPt2D.CENTER, new Offset2D(3.0, 2.0));
        assertFalse(a1.equals(a2));
        a2 = new Anchor2D(RefPt2D.CENTER, new Offset2D(3.0, 2.0));
        assertTrue(a1.equals(a2));
    }
    
    @Test
    public void testSerialization() {
        Anchor2D a1 = new Anchor2D(RefPt2D.BOTTOM_CENTER, 
                new Offset2D(1.0, 2.0));
        Anchor2D a2 = (Anchor2D) TestUtils.serialized(a1);
        assertEquals(a1, a2);
    }
}
