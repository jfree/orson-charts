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

package com.orsoncharts.data.xyz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link XYZItemKey} class.
 */
public class XYZItemKeyTest {
    
    @Test
    public void testEquals() {
        XYZItemKey k1 = new XYZItemKey("S1", 1);
        XYZItemKey k2 = new XYZItemKey("S1", 1);
        assertTrue(k1.equals(k2));
        assertFalse(k1.equals(null));
        
        k1 = new XYZItemKey("S2", 1);
        assertFalse(k1.equals(k2));
        k2 = new XYZItemKey("S2", 1);
        assertTrue(k1.equals(k2));
        
        k1 = new XYZItemKey("S2", 2);
        assertFalse(k1.equals(k2));
        k2 = new XYZItemKey("S2", 2);
        assertTrue(k1.equals(k2));
    }

    /**
     * Check for serialization support.
     */
    @Test
    public void testSerialization() {
        XYZItemKey k1 = new XYZItemKey("S1", 1);
        XYZItemKey k2 = (XYZItemKey) TestUtils.serialized(k1);
        assertEquals(k1, k2);
    }
    
}
