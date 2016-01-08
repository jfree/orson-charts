/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.plot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;

import com.orsoncharts.axis.StandardCategoryAxis3D;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.renderer.category.BarRenderer3D;
import com.orsoncharts.TestUtils;
import com.orsoncharts.label.StandardCategoryLabelGenerator;

/**
 * Tests for the {@link CategoryPlot3D} class.
 */
public class CategoryPlot3DTest {
    
    @Test
    public void testEquals() {
        StandardCategoryDataset3D d1 = new StandardCategoryDataset3D();
        BarRenderer3D r1 = new BarRenderer3D();
        CategoryPlot3D p1 = new CategoryPlot3D(d1, r1, 
                new StandardCategoryAxis3D("R"), 
                new StandardCategoryAxis3D("C"), 
                new NumberAxis3D("N"));
        StandardCategoryDataset3D d2 = new StandardCategoryDataset3D();
        BarRenderer3D r2 = new BarRenderer3D();
        CategoryPlot3D p2 = new CategoryPlot3D(d2, r2, 
                new StandardCategoryAxis3D("R"), 
                new StandardCategoryAxis3D("C"), 
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
        
        p1.setLegendLabelGenerator(new StandardCategoryLabelGenerator("%s XX"));
        assertFalse(p1.equals(p2));
        p2.setLegendLabelGenerator(new StandardCategoryLabelGenerator("%s XX"));
        assertTrue(p1.equals(p2));
        
        p1.setYDimensionOverride(new Double(123));
        assertFalse(p1.equals(p2));
        p2.setYDimensionOverride(new Double(123));
        assertTrue(p1.equals(p2));
    }
        
    /**
     * Checks for serialization.
     */
    @Test
    public void testSerialization() {
        CategoryPlot3D p1 = createCategory3DPlot();
        CategoryPlot3D p2 = (CategoryPlot3D) TestUtils.serialized(p1);
        assertTrue(p1.equals(p2));
        
        p1.setGridlinePaintForRows(new GradientPaint(1f, 2f, Color.RED, 3f, 4f, 
                Color.BLUE));
        p2 = (CategoryPlot3D) TestUtils.serialized(p1);
        assertTrue(p1.equals(p2));

        p1.setGridlinePaintForColumns(new GradientPaint(5f, 6f, Color.GRAY, 7f,
                8f, Color.YELLOW));
        p2 = (CategoryPlot3D) TestUtils.serialized(p1);
        assertTrue(p1.equals(p2));
        
        p1.setGridlinePaintForValues(new GradientPaint(9f, 10f, Color.GREEN, 
                11f, 12f, Color.LIGHT_GRAY));
        p2 = (CategoryPlot3D) TestUtils.serialized(p1);
        assertTrue(p1.equals(p2));
    }

    private CategoryPlot3D createCategory3DPlot() {
        CategoryDataset3D dataset = new StandardCategoryDataset3D();
        BarRenderer3D renderer = new BarRenderer3D();
        StandardCategoryAxis3D rowAxis = new StandardCategoryAxis3D("rowAxis");
        StandardCategoryAxis3D colAxis = new StandardCategoryAxis3D("colAxis");
        NumberAxis3D valueAxis = new NumberAxis3D("Value");
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                colAxis, valueAxis);
        return plot;
    }
}
