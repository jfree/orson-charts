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
 * Tests for the {@link AreaRenderer3D} class.
 */
public class AreaRenderer3DTest {

    @Test
    public void testEquals() {
        AreaRenderer3D r1 = new AreaRenderer3D();
        AreaRenderer3D r2 = new AreaRenderer3D();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
    }
}
