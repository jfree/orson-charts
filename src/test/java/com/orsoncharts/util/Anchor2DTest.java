/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.util;

import com.orsoncharts.TestUtils;
import com.orsoncharts.graphics3d.Offset2D;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Some checks for the {@link Anchor2D} class.
 */
public class Anchor2DTest {
    
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
