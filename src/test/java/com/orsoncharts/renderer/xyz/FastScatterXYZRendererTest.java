/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the {@link FastScatterXYZRendererTest}.
 */
public class FastScatterXYZRendererTest {
    
    @Test
    public void testEquals() {
        ScatterXYZRenderer r1 = new ScatterXYZRenderer();
        ScatterXYZRenderer r2 = new ScatterXYZRenderer();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
    }
 
}
