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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.awt.GradientPaint;
import org.jfree.chart3d.data.DefaultKeyedValues;
import org.jfree.chart3d.data.PieDataset3D;
import org.jfree.chart3d.data.StandardPieDataset3D;
import org.jfree.chart3d.data.category.CategoryDataset3D;
import org.jfree.chart3d.data.category.StandardCategoryDataset3D;
import org.jfree.chart3d.data.xyz.XYZDataset;
import org.jfree.chart3d.data.xyz.XYZSeries;
import org.jfree.chart3d.data.xyz.XYZSeriesCollection;
import org.jfree.chart3d.legend.LegendAnchor;
import org.jfree.chart3d.legend.StandardLegendBuilder;
import org.jfree.chart3d.style.ChartStyles;
import org.jfree.chart3d.table.StandardRectanglePainter;

/**
 * Tests for the {@link Chart3D} class.
 */
public class Chart3DTest {
 
    /**
     * Checks for the equals() method.
     */
    @Test
    public void checkEquals() {
        Chart3D c1 = Chart3DFactory.createPieChart("title", "subtitle", 
                createPieDataset());
        Chart3D c2 = Chart3DFactory.createPieChart("title", "subtitle",
                createPieDataset());
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(null));
        
        // background painter
        c1.setBackground(new StandardRectanglePainter(Color.RED));
        assertFalse(c1.equals(c2));
        c2.setBackground(new StandardRectanglePainter(Color.RED));
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
        
        // legend anchor
        c1.setLegendAnchor(LegendAnchor.CENTER_LEFT);
        assertFalse(c1.equals(c2));
        c2.setLegendAnchor(LegendAnchor.CENTER_LEFT);
        assertTrue(c1.equals(c2));

        // legend orientation
        c1.setLegendOrientation(Orientation.VERTICAL);
        assertFalse(c1.equals(c2));
        c2.setLegendOrientation(Orientation.VERTICAL);
        assertTrue(c1.equals(c2));
        
        StandardLegendBuilder slb1 = new StandardLegendBuilder();
        slb1.setHeader("ABC");
        c1.setLegendBuilder(slb1);
        assertFalse(c1.equals(c2));
        StandardLegendBuilder slb2 = new StandardLegendBuilder();
        slb2.setHeader("ABC");
        c2.setLegendBuilder(slb2);
        assertTrue(c1.equals(c2));
        
        // chart box color
        c1.setChartBoxColor(Color.CYAN);
        assertFalse(c1.equals(c2));
        c2.setChartBoxColor(Color.CYAN);
        assertTrue(c1.equals(c2));
        
        // projDist
        c1.setProjDistance(123);
        assertFalse(c1.equals(c2));
        c2.setProjDistance(123);
        assertTrue(c1.equals(c2));
        
        // style
        c1.setStyle(ChartStyles.createIceCubeStyle());
        assertFalse(c1.equals(c2));
        c2.setStyle(ChartStyles.createIceCubeStyle());
        assertTrue(c1.equals(c2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerializationPieChart() {
        Chart3D c1 = Chart3DFactory.createPieChart("title", "subtitle", 
                createPieDataset());
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
        
        // check that gradient paint is correctly serialized
        c1.setBackground(new StandardRectanglePainter(new GradientPaint(1f, 2f,
                Color.RED, 3f, 4f, Color.BLUE)));
        c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    /**
     * A check for serialization of area charts.
     */
    @Test
    public void testSerializationAreaChart() {
        Chart3D c1 = Chart3DFactory.createAreaChart("title", "subtitle",
                createCategoryDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    /**
     * A check for serialization of bar charts.
     */
    @Test
    public void testSerializationBarChart() {
        Chart3D c1 = Chart3DFactory.createBarChart("title", "subtitle",
                createCategoryDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }

    /**
     * A check for serialization of line charts.
     */
    @Test
    public void testSerializationLineChart() {
        Chart3D c1 = Chart3DFactory.createLineChart("title", "subtitle",
                createCategoryDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }

    /**
     * A check for serialization of stacked bar charts.
     */
    @Test
    public void testSerializationStackedBarChart() {
        Chart3D c1 = Chart3DFactory.createStackedBarChart("title", "subtitle",
                createCategoryDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }

    /**
     * A check for serialization of scatter charts.
     */
    @Test
    public void testSerializationScatterChart() {
        Chart3D c1 = Chart3DFactory.createScatterChart("title", "subtitle",
                createXYZDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }

    /**
     * A check for serialization of scatter charts.
     */
    @Test
    public void testSerializationXYZBarChart() {
        Chart3D c1 = Chart3DFactory.createXYZBarChart("title", "subtitle",
                createXYZDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    /**
     * After deserializing, check that we can still register listeners.
     */
    @Test
    public void testListenerStorageAfterDeserialization() {
        Chart3D c1 = Chart3DFactory.createPieChart("title", "subtitle", 
                createPieDataset());
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
        
        c2.addChangeListener(new Chart3DChangeListener() {

            @Override
            public void chartChanged(Chart3DChangeEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }

    private PieDataset3D<String> createPieDataset() {
        StandardPieDataset3D<String> dataset = new StandardPieDataset3D<>();
        dataset.add("United States", 30.0);
        dataset.add("France", 20.0);
        return dataset; 
    }
    
    private CategoryDataset3D<String, String, String> createCategoryDataset() {
        StandardCategoryDataset3D<String, String, String> dataset 
                = new StandardCategoryDataset3D<>();
        DefaultKeyedValues<String, Number> s1 = new DefaultKeyedValues<>();
        s1.put("Q1", 1.0);
        s1.put("Q2", 7.0);
        s1.put("Q3", 3.0);
        s1.put("Q4", 4.0);
        dataset.addSeriesAsRow("Acme Widgets Ltd", s1);
        return dataset;
    }
    
    private XYZDataset<String> createXYZDataset() {
        XYZSeries<String> s1 = new XYZSeries<>("S1");
        s1.add(1, 2, 3);
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<>();
        dataset.add(s1);
        return dataset;
    }
}
