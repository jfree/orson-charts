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
 * Tests for the {@link DefaultXYZPaintSource} class.
 */
public class StandardXYZPaintSourceTest {
    
    @Test
    public void testEquals() {
        StandardXYZPaintSource s1 = new StandardXYZPaintSource();
        StandardXYZPaintSource s2 = new StandardXYZPaintSource();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
    }
}
