/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the {@link DefaultCategoryDataset3D} class.
 */
public class DefaultCategoryDataset3DTest {
    
    @Test
    public void checkGeneral() {
        DefaultCategoryDataset3D dataset = new DefaultCategoryDataset3D();
        assertEquals(0, dataset.getSeriesCount());
        assertEquals(0, dataset.getRowCount());
        assertEquals(0, dataset.getColumnCount());
        assertTrue(dataset.getSeriesKeys().isEmpty());
        assertTrue(dataset.getRowKeys().isEmpty());
        assertTrue(dataset.getColumnKeys().isEmpty());
    }
    
    @Test
    public void testEquals() {
        DefaultCategoryDataset3D d1 = new DefaultCategoryDataset3D();
        DefaultCategoryDataset3D d2 = new DefaultCategoryDataset3D();
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1.addValue(1.0, "S1", "R1", "C1");
        assertFalse(d1.equals(d2));
        d2.addValue(1.0, "S1", "R1", "C1");
        assertTrue(d1.equals(d2));
    }
}
