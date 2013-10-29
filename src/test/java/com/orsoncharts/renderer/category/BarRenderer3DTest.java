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
import java.awt.Color;
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

        r1.setBase(-1.0);
        assertFalse(r1.equals(r2));
        r2.setBase(-1.0);
        assertTrue(r1.equals(r2));

        r1.setBarXWidth(1.1);
        assertFalse(r1.equals(r2));
        r2.setBarXWidth(1.1);
        assertTrue(r1.equals(r2));

        r1.setBarZWidth(2.2);
        assertFalse(r1.equals(r2));
        r2.setBarZWidth(2.2);
        assertTrue(r1.equals(r2));

        // basePaintSource
        r1.setBasePaintSource(null);
        assertFalse(r1.equals(r2));
        r2.setBasePaintSource(null);
        assertTrue(r1.equals(r2));
        
        r1.setBasePaintSource(new StandardCategory3DPaintSource(Color.CYAN));
        assertFalse(r1.equals(r2));
        r2.setBasePaintSource(new StandardCategory3DPaintSource(Color.CYAN));
        assertTrue(r1.equals(r2));
        
        // topPaintSource
        r1.setTopPaintSource(null);
        assertFalse(r1.equals(r2));
        r2.setTopPaintSource(null);
        assertTrue(r1.equals(r2));
        
        r1.setTopPaintSource(new StandardCategory3DPaintSource(Color.YELLOW));
        assertFalse(r1.equals(r2));
        r2.setTopPaintSource(new StandardCategory3DPaintSource(Color.YELLOW));
        assertTrue(r1.equals(r2));
        
        // notify
        r1.setNotify(false);
        assertFalse(r1.equals(r2));
        r2.setNotify(false);
        assertTrue(r1.equals(r2));
    }
    
    @Test
    public void testSerialization() {
        BarRenderer3D r1 = new BarRenderer3D();
        BarRenderer3D r2 = (BarRenderer3D) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }

}