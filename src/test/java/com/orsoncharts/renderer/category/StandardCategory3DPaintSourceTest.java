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
 * Tests for the {@link DefaultCategory3DPaintSource} class.
 */
public class StandardCategory3DPaintSourceTest {
    
    @Test
    public void testEquals() {
        StandardCategory3DPaintSource s1 = new StandardCategory3DPaintSource();
        StandardCategory3DPaintSource s2 = new StandardCategory3DPaintSource();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
        
        s1 = new StandardCategory3DPaintSource(new Color[] { Color.BLUE });
        assertFalse(s1.equals(s2));
        s2 = new StandardCategory3DPaintSource(new Color[] { Color.BLUE });
        assertTrue(s1.equals(s2)); 
    }
    
    @Test
    public void testSerialization() {
        StandardCategory3DPaintSource s1 = new StandardCategory3DPaintSource();
        StandardCategory3DPaintSource s2 = (StandardCategory3DPaintSource) 
                TestUtils.serialized(s1);
        assertTrue(s1.equals(s2));
    }
        
}
