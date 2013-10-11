/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.Color;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.xyz.XYZDataItem;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Chart3D} class.
 */
public class Chart3DTest {
 
    @Test
    public void checkEquals() {
        Chart3D c1 = Chart3DFactory.createPieChart("title", createDataset());
        Chart3D c2 = Chart3DFactory.createPieChart("title", createDataset());
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(null));
        
        // background paint
        c1.setBackgroundPaint(Color.RED);
        assertFalse(c1.equals(c2));
        c2.setBackgroundPaint(Color.RED);
        assertTrue(c1.equals(c2));
        
        // title
        c1.setTitle("ABC");
        assertFalse(c1.equals(c2));
        c2.setTitle("ABC");
        assertTrue(c1.equals(c2));
        
        c1.setTitle((String) null);
        assertFalse(c1.equals(c2));
        c2.setTitle((String) null);
        assertTrue(c1.equals(c2));
        
        // title anchor
        c1.setTitleAnchor(TitleAnchor.BOTTOM_LEFT);
        assertFalse(c1.equals(c2));
        c2.setTitleAnchor(TitleAnchor.BOTTOM_LEFT);
        assertTrue(c1.equals(c2));
        
        // legend builder
        c1.setLegendBuilder(null);
        assertFalse(c1.equals(c2));
        c2.setLegendBuilder(null);
        assertTrue(c1.equals(c2));
        
        // chart box color
        c1.setChartBoxColor(Color.CYAN);
        assertFalse(c1.equals(c2));
        c2.setChartBoxColor(Color.CYAN);
        assertTrue(c1.equals(c2));
    }
    
    @Test
    public void testSerialization() {
        Chart3D c1 = Chart3DFactory.createPieChart("title", createDataset());
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    private PieDataset3D createDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("United States", new Double(30.0));
        dataset.add("France", new Double(20.0));
        return dataset; 
    }
}
