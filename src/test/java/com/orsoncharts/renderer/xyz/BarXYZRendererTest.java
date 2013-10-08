/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the {@link BarXYZRenderer} class.
 */
public class BarXYZRendererTest {
 
    @Test
    public void testEquals() {
        BarXYZRenderer r1 = new BarXYZRenderer();
        BarXYZRenderer r2 = new BarXYZRenderer();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
    }
}
