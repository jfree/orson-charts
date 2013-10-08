/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.xyz;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the {@link XYZDataItem} class.
 */
public class XYZSeriesCollectionTest {
    
    @Test
    public void testEquals() {
        XYZSeriesCollection c1 = new XYZSeriesCollection();
        XYZSeriesCollection c2 = new XYZSeriesCollection();
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(null));
    }
}
