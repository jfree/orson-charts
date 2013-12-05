/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.orsoncharts.Range;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link RainbowScale} class.
 */
public class RainbowScaleTest {
    
    @Test
    public void testEquals() {
        RainbowScale rs1 = new RainbowScale(new Range(5.0, 10.0));
        RainbowScale rs2 = new RainbowScale(new Range(5.0, 10.0));
        assertTrue(rs1.equals(rs2));
        assertFalse(rs1.equals(null));
        
    }    
    
    @Test
    public void testSerialization() {
        RainbowScale rs1 = new RainbowScale(new Range(5.0, 10.0));
        RainbowScale rs2 = (RainbowScale) TestUtils.serialized(rs1);
        assertTrue(rs1.equals(rs2));
    }


}
