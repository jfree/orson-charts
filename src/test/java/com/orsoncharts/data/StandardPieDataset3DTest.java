/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the {@link StandardPieDataset} class.
 */
public class StandardPieDataset3DTest {
    
    @Test
    public void testEquals() {
        StandardPieDataset3D d1 = new StandardPieDataset3D();
        StandardPieDataset3D d2 = new StandardPieDataset3D();
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
    }
}
