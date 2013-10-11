/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link StackedBarRenderer3D} class.
 */
public class StackedBarRenderer3DTest {

    @Test
    public void testEquals() {
        StackedBarRenderer3D r1 = new StackedBarRenderer3D();
        StackedBarRenderer3D r2 = new StackedBarRenderer3D();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        // ensure that we distinguish from the base class
        assertFalse(r1.equals(new BarRenderer3D()));
    }
    
    @Test
    public void testSerialization() {
        StackedBarRenderer3D r1 = new StackedBarRenderer3D();
        StackedBarRenderer3D r2 = (StackedBarRenderer3D) 
                TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }

}
