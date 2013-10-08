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
 * Tests for the {@link DefaultXYZPaintSource} class.
 */
public class DefaultXYZPaintSourceTest {
    
    @Test
    public void testEquals() {
        DefaultXYZPaintSource s1 = new DefaultXYZPaintSource();
        DefaultXYZPaintSource s2 = new DefaultXYZPaintSource();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
    }
}
