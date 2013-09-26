/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.data.category.DefaultCategoryDataset3D;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the {@link DefaultCategoryDataset3D} implementation.
 */
public class DefaultCategoryDataset3DTests {
  
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
}
