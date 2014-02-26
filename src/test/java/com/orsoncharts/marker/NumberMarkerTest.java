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
 * Tests for the {@link NumberMarker} class.
 */
public class NumberMarkerTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        NumberMarker nm1 = new NumberMarker(1.0); 
        NumberMarker nm2 = new NumberMarker(1.0); 
        assertTrue(nm1.equals(nm2));
        assertFalse(nm1.equals(null));
        
        nm1 = new NumberMarker(1.1);
        assertFalse(nm1.equals(nm2));
        nm2 = new NumberMarker(1.1);
        assertTrue(nm1.equals(nm2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        NumberMarker nm1 = new NumberMarker(1.0);  
        NumberMarker nm2 = (NumberMarker) TestUtils.serialized(nm1);
        assertTrue(nm1.equals(nm2));
    }
}
