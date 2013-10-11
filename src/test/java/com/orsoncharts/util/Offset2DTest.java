/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.util;

import com.orsoncharts.graphics3d.Offset2D;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Some checks for the {@link Offset2D} class.
 */
public class Offset2DTest {
    
    @Test
    public void testEquals() {
        Offset2D off1 = new Offset2D(1.0, 2.0);
        Offset2D off2 = new Offset2D(1.0, 2.0);
        assertTrue(off1.equals(off2));
        assertFalse(off1.equals(null));
        
        off1 = new Offset2D(3.0, 2.0);
        assertFalse(off1.equals(off2));
        off2 = new Offset2D(3.0, 2.0);
        assertTrue(off1.equals(off2));

        off1 = new Offset2D(3.0, 4.0);
        assertFalse(off1.equals(off2));
        off2 = new Offset2D(3.0, 4.0);
        assertTrue(off1.equals(off2));
    }
    
    @Test
    public void testSerialization() {
        Offset2D off1 = new Offset2D(1.0, 2.0);
        Offset2D off2 = (Offset2D) TestUtils.serialized(off1);
        assertEquals(off1, off2);
    }
}
