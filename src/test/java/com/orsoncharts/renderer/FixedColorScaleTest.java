/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.renderer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.orsoncharts.TestUtils;
import java.awt.Color;

/**
 * Tests for the {@link FixedColorScale} class.
 */
public class FixedColorScaleTest {
    
    @Test
    public void testEquals() {
        FixedColorScale fcs1 = new FixedColorScale(Color.YELLOW);
        FixedColorScale fcs2 = new FixedColorScale(Color.YELLOW);
        assertTrue(fcs1.equals(fcs2));
        assertFalse(fcs1.equals(null));
        
        fcs1 = new FixedColorScale(Color.BLUE);
        assertFalse(fcs1.equals(fcs2));
        fcs2 = new FixedColorScale(Color.BLUE);
        assertTrue(fcs1.equals(fcs2));
    }    
    
    @Test
    public void testSerialization() {
        FixedColorScale fcs1 = new FixedColorScale(Color.YELLOW);
        FixedColorScale fcs2 = (FixedColorScale) TestUtils.serialized(fcs1);
        assertTrue(fcs1.equals(fcs2));
    }

}
