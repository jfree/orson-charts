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

package com.orsoncharts.marker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link RangeMarker} class.
 */
public class RangeMarkerTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        RangeMarker rm1 = new RangeMarker(1.0, 2.0); 
        RangeMarker rm2 = new RangeMarker(1.0, 2.0); 
        assertTrue(rm1.equals(rm2));
        assertFalse(rm1.equals(null));
        
        rm1 = new RangeMarker(1.1, 2.0);
        assertFalse(rm1.equals(rm2));
        rm2 = new RangeMarker(1.1, 2.0);
        assertTrue(rm1.equals(rm2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        RangeMarker rm1 = new RangeMarker(1.0, 2.0); 
        RangeMarker rm2 = (RangeMarker) TestUtils.serialized(rm1);
        assertTrue(rm1.equals(rm2));
    }
}

