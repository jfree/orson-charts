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
 * Tests for the {@link XYZSeriesCollection} class.
 */
public class XYZSeriesCollectionTest {
    
    @Test
    public void testEquals() {
        XYZSeriesCollection c1 = new XYZSeriesCollection();
        XYZSeriesCollection c2 = new XYZSeriesCollection();
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(null));
    }
    
    @Test
    public void testSerialization() {
        XYZSeries s1 = new XYZSeries("S");
        s1.add(1.0, 2.0, 3.0);
        XYZSeriesCollection c1 = new XYZSeriesCollection();
        c1.add(s1);
        XYZSeriesCollection c2 = (XYZSeriesCollection) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
}
