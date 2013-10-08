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

/**
 * Tests for the {@link DefaultCategory3DPaintSource} class.
 */
public class DefaultCategory3DPaintSourceTest {
    
    @Test
    public void testEquals() {
        DefaultCategory3DPaintSource s1 = new DefaultCategory3DPaintSource();
        DefaultCategory3DPaintSource s2 = new DefaultCategory3DPaintSource();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
    }
        
}
