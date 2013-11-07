/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.xyz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link XYZDataItem} class.
 */
public class XYZDataItemTest {
    
    @Test
    public void testEquals() {
        XYZDataItem d1 = new XYZDataItem(1.0, 2.0, 3.0);
        XYZDataItem d2 = new XYZDataItem(1.0, 2.0, 3.0);
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1 = new XYZDataItem(1.1, 2.0, 3.0);
        assertFalse(d1.equals(d2));
        d2 = new XYZDataItem(1.1, 2.0, 3.0);
        assertTrue(d1.equals(d2));

        d1 = new XYZDataItem(1.1, 2.2, 3.0);
        assertFalse(d1.equals(d2));
        d2 = new XYZDataItem(1.1, 2.2, 3.0);
        assertTrue(d1.equals(d2));

        d1 = new XYZDataItem(1.1, 2.2, 3.3);
        assertFalse(d1.equals(d2));
        d2 = new XYZDataItem(1.1, 2.2, 3.3);
        assertTrue(d1.equals(d2));
    }
    
    @Test
    public void testSerialization() {
        XYZDataItem d1 = new XYZDataItem(1.0, 2.0, 3.0);
        XYZDataItem d2 = (XYZDataItem) TestUtils.serialized(d1);
        assertEquals(d1, d2);
    }

}
