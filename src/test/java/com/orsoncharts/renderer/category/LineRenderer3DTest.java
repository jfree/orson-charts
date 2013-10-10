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
 * Tests for the {@link LineRenderer3D} class.
 */
public class LineRenderer3DTest {

    @Test
    public void testEquals() {
        LineRenderer3D r1 = new LineRenderer3D();
        LineRenderer3D r2 = new LineRenderer3D();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        r1.setLineWidth(1.1);
        assertFalse(r1.equals(r2));
        r2.setLineWidth(1.1);
        assertTrue(r1.equals(r2));
        
    }

}