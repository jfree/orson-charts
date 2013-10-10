/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.orsoncharts.axis.StandardCategoryAxis3D;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.renderer.category.BarRenderer3D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Tests for the {@link CategoryPlot3D} class.
 */
public class CategoryPlot3DTest {
    
    @Test
    public void testEquals() {
        StandardCategoryDataset3D d1 = new StandardCategoryDataset3D();
        BarRenderer3D r1 = new BarRenderer3D();
        CategoryPlot3D p1 = new CategoryPlot3D(d1, r1, 
                new StandardCategoryAxis3D("R"), new StandardCategoryAxis3D("C"), 
                new NumberAxis3D("N"));
        StandardCategoryDataset3D d2 = new StandardCategoryDataset3D();
        BarRenderer3D r2 = new BarRenderer3D();
        CategoryPlot3D p2 = new CategoryPlot3D(d1, r1, 
                new StandardCategoryAxis3D("R"), new StandardCategoryAxis3D("C"), 
                new NumberAxis3D("N"));
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(null));
        
        p1.setGridlinesVisibleForValues(false);
        assertFalse(p1.equals(p2));
        p2.setGridlinesVisibleForValues(false);
        assertTrue(p1.equals(p2));
        
        p1.setGridlinePaintForValues(Color.RED);
        assertFalse(p1.equals(p2));
        p2.setGridlinePaintForValues(Color.RED);
        assertTrue(p1.equals(p2));
        
        p1.setGridlineStrokeForValues(new BasicStroke(0.5f));
        assertFalse(p1.equals(p2));
        p2.setGridlineStrokeForValues(new BasicStroke(0.5f));
        assertTrue(p1.equals(p2));
        
        p1.setGridlinesVisibleForRows(true);
        assertFalse(p1.equals(p2));
        p2.setGridlinesVisibleForRows(true);
        assertTrue(p1.equals(p2));
        
        p1.setGridlinePaintForRows(Color.GREEN);
        assertFalse(p1.equals(p2));
        p2.setGridlinePaintForRows(Color.GREEN);
        assertTrue(p1.equals(p2));
        
        p1.setGridlineStrokeForRows(new BasicStroke(0.6f));
        assertFalse(p1.equals(p2));
        p2.setGridlineStrokeForRows(new BasicStroke(0.6f));
        assertTrue(p1.equals(p2));
    }
}
