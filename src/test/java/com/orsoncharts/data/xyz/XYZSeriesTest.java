/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.xyz;

import com.orsoncharts.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the {@link XYZSeries} class.
 */
public class XYZSeriesTest {
    
    @Test
    public void testEquals() {
        XYZSeries s1 = new XYZSeries("S");
        XYZSeries s2 = new XYZSeries("S");
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
        
        s1.add(1.0, 2.0, 3.0);
        assertFalse(s1.equals(s2));
        s2.add(1.0, 2.0, 3.0);
        assertTrue(s1.equals(s2));
    }

    @Test
    public void testSerialization() {
        XYZSeries s1 = new XYZSeries("S");
        XYZSeries s2 = (XYZSeries) TestUtils.serialized(s1);
        assertEquals(s1, s2);

        s1.add(1.0, 2.0, 3.0);
        s2 = (XYZSeries) TestUtils.serialized(s1);
        assertEquals(s1, s2);
    }

}
