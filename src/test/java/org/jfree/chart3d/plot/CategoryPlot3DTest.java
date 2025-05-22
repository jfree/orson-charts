/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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

package org.jfree.chart3d.plot;

import org.junit.jupiter.api.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import org.jfree.chart3d.TestUtils;

import org.jfree.chart3d.axis.NumberAxis3D;
import org.jfree.chart3d.axis.StandardCategoryAxis3D;
import org.jfree.chart3d.data.category.CategoryDataset3D;
import org.jfree.chart3d.data.category.StandardCategoryDataset3D;
import org.jfree.chart3d.label.StandardCategoryLabelGenerator;
import org.jfree.chart3d.renderer.category.BarRenderer3D;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CategoryPlot3D} class.
 */
public class CategoryPlot3DTest {
    
    @Test
    public void testEquals() {
        StandardCategoryDataset3D<String, String, String> d1 = new StandardCategoryDataset3D<>();
        BarRenderer3D r1 = new BarRenderer3D();
        CategoryPlot3D p1 = new CategoryPlot3D(d1, r1, 
                new StandardCategoryAxis3D("R"), 
                new StandardCategoryAxis3D("C"), 
                new NumberAxis3D("N"));
        StandardCategoryDataset3D<String, String, String> d2 = new StandardCategoryDataset3D<>();
        BarRenderer3D r2 = new BarRenderer3D();
        CategoryPlot3D p2 = new CategoryPlot3D(d2, r2, 
                new StandardCategoryAxis3D("R"), 
                new StandardCategoryAxis3D("C"), 
                new NumberAxis3D("N"));
        assertEquals(p1, p2);
        assertNotEquals(null, p1);
        
        p1.setGridlinesVisibleForValues(false);
        assertNotEquals(p1, p2);
        p2.setGridlinesVisibleForValues(false);
        assertEquals(p1, p2);
        
        p1.setGridlinePaintForValues(Color.RED);
        assertNotEquals(p1, p2);
        p2.setGridlinePaintForValues(Color.RED);
        assertEquals(p1, p2);
        
        p1.setGridlineStrokeForValues(new BasicStroke(0.5f));
        assertNotEquals(p1, p2);
        p2.setGridlineStrokeForValues(new BasicStroke(0.5f));
        assertEquals(p1, p2);
        
        p1.setGridlinesVisibleForRows(true);
        assertNotEquals(p1, p2);
        p2.setGridlinesVisibleForRows(true);
        assertEquals(p1, p2);
        
        p1.setGridlinePaintForRows(Color.GREEN);
        assertNotEquals(p1, p2);
        p2.setGridlinePaintForRows(Color.GREEN);
        assertEquals(p1, p2);
        
        p1.setGridlineStrokeForRows(new BasicStroke(0.6f));
        assertNotEquals(p1, p2);
        p2.setGridlineStrokeForRows(new BasicStroke(0.6f));
        assertEquals(p1, p2);
        
        p1.setLegendLabelGenerator(new StandardCategoryLabelGenerator<>("%s XX"));
        assertNotEquals(p1, p2);
        p2.setLegendLabelGenerator(new StandardCategoryLabelGenerator<>("%s XX"));
        assertEquals(p1, p2);
        
        p1.setYDimensionOverride(123.0);
        assertNotEquals(p1, p2);
        p2.setYDimensionOverride(123.0);
        assertEquals(p1, p2);
    }
        
    /**
     * Checks for serialization.
     */
    @Test
    public void testSerialization() {
        CategoryPlot3D p1 = createCategory3DPlot();
        CategoryPlot3D p2 = (CategoryPlot3D) TestUtils.serialized(p1);
        assertEquals(p1, p2);
        
        p1.setGridlinePaintForRows(new GradientPaint(1f, 2f, Color.RED, 3f, 4f, 
                Color.BLUE));
        p2 = (CategoryPlot3D) TestUtils.serialized(p1);
        assertEquals(p1, p2);

        p1.setGridlinePaintForColumns(new GradientPaint(5f, 6f, Color.GRAY, 7f,
                8f, Color.YELLOW));
        p2 = (CategoryPlot3D) TestUtils.serialized(p1);
        assertEquals(p1, p2);
        
        p1.setGridlinePaintForValues(new GradientPaint(9f, 10f, Color.GREEN, 
                11f, 12f, Color.LIGHT_GRAY));
        p2 = (CategoryPlot3D) TestUtils.serialized(p1);
        assertEquals(p1, p2);
    }

    private CategoryPlot3D createCategory3DPlot() {
        CategoryDataset3D<String, String, String> dataset = new StandardCategoryDataset3D<>();
        BarRenderer3D renderer = new BarRenderer3D();
        StandardCategoryAxis3D rowAxis = new StandardCategoryAxis3D("rowAxis");
        StandardCategoryAxis3D colAxis = new StandardCategoryAxis3D("colAxis");
        NumberAxis3D valueAxis = new NumberAxis3D("Value");
        return new CategoryPlot3D(dataset, renderer, rowAxis, colAxis, valueAxis);
    }
}
