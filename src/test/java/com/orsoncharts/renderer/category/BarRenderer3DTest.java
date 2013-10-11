/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link BarRenderer3D} class.
 */
public class BarRenderer3DTest {

    @Test
    public void testEquals() {
        BarRenderer3D r1 = new BarRenderer3D();
        BarRenderer3D r2 = new BarRenderer3D();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
    }
    
    @Test
    public void testSerialization() {
        BarRenderer3D r1 = new BarRenderer3D();
        BarRenderer3D r2 = (BarRenderer3D) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }

}