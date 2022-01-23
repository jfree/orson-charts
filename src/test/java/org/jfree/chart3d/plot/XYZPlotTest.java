/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2022, by David Gilbert.  All rights reserved.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jfree.chart3d.data.xyz.XYZSeries;
import org.junit.jupiter.api.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import org.jfree.chart3d.TestUtils;
import org.jfree.chart3d.axis.NumberAxis3D;
import org.jfree.chart3d.axis.ValueAxis3D;
import org.jfree.chart3d.data.Range;
import org.jfree.chart3d.data.xyz.XYZDataset;
import org.jfree.chart3d.data.xyz.XYZSeriesCollection;
import org.jfree.chart3d.graphics3d.Dimension3D;
import org.jfree.chart3d.label.StandardXYZLabelGenerator;
import org.jfree.chart3d.renderer.xyz.ScatterXYZRenderer;
import org.jfree.chart3d.renderer.xyz.XYZRenderer;

/**
 * Checks for the {@link XYZPlot} class.
 */
public class XYZPlotTest implements Plot3DChangeListener {
    
    @Test
    public void checkSetDatasetFiresChangeEvent() {
        XYZPlot plot = createXYZPlot();
        plot.addChangeListener(this);
        assertNull(this.lastEvent);
    
        plot.setDataset(createNewDataset());
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    public void checkSetDatasetRemovesPreviousListener() {
        XYZDataset<String> dataset1 = createNewDataset();
        XYZPlot plot = createXYZPlot();
        plot.setDataset(dataset1);
        assertTrue(dataset1.hasListener(plot));
        XYZDataset<String> dataset2 = createNewDataset();
        plot.setDataset(dataset2);
        assertFalse(dataset1.hasListener(plot));
        assertTrue(dataset2.hasListener(plot));
    }
  
    @Test
    public void checkEquals() {
        XYZPlot p1 = createXYZPlot();
        XYZPlot p2 = createXYZPlot();
        assertEquals(p1, p2);
    
        p1.setDimensions(new Dimension3D(1, 2, 3));
        assertNotEquals(p1, p2);
        p2.setDimensions(new Dimension3D(1, 2, 3));
        assertEquals(p1, p2);
        
        p1.setGridlinesVisibleX(false);
        assertNotEquals(p1, p2);
        p2.setGridlinesVisibleX(false);
        assertEquals(p1, p2);
        
        p1.setGridlinePaintX(Color.YELLOW);
        assertNotEquals(p1, p2);
        p2.setGridlinePaintX(Color.YELLOW);
        assertEquals(p1, p2);
        
        p1.setGridlineStrokeX(new BasicStroke(0.3f));
        assertNotEquals(p1, p2);
        p2.setGridlineStrokeX(new BasicStroke(0.3f));
        assertEquals(p1, p2);
        
        p1.setGridlinesVisibleY(false);
        assertNotEquals(p1, p2);
        p2.setGridlinesVisibleY(false);
        assertEquals(p1, p2);

        p1.setGridlinePaintY(Color.GREEN);
        assertNotEquals(p1, p2);
        p2.setGridlinePaintY(Color.GREEN);
        assertEquals(p1, p2);
        
        p1.setGridlineStrokeY(new BasicStroke(0.36f));
        assertNotEquals(p1, p2);
        p2.setGridlineStrokeY(new BasicStroke(0.36f));
        assertEquals(p1, p2);

        p1.setGridlinesVisibleZ(false);
        assertNotEquals(p1, p2);
        p2.setGridlinesVisibleZ(false);
        assertEquals(p1, p2);

        p1.setGridlinePaintZ(Color.BLUE);
        assertNotEquals(p1, p2);
        p2.setGridlinePaintZ(Color.BLUE);
        assertEquals(p1, p2);
        
        p1.setGridlineStrokeZ(new BasicStroke(0.6f));
        assertNotEquals(p1, p2);
        p2.setGridlineStrokeZ(new BasicStroke(0.6f));
        assertEquals(p1, p2);
        
        p1.setLegendLabelGenerator(new StandardXYZLabelGenerator("%s XX"));
        assertNotEquals(p1, p2);
        p2.setLegendLabelGenerator(new StandardXYZLabelGenerator("%s XX"));
        assertEquals(p1, p2);
    }

    /**
     * Checks for serialization.
     */
    @Test
    public void testSerialization() {
        XYZPlot p1 = createXYZPlot();
        XYZPlot p2 = (XYZPlot) TestUtils.serialized(p1);
        assertEquals(p1, p2);
        
        p1.setGridlinePaintX(new GradientPaint(1f, 2f, Color.RED, 3f, 4f, 
                Color.BLUE));
        p2 = (XYZPlot) TestUtils.serialized(p1);
        assertEquals(p1, p2);

        p1.setGridlinePaintY(new GradientPaint(5f, 6f, Color.GRAY, 7f, 8f, 
                Color.YELLOW));
        p2 = (XYZPlot) TestUtils.serialized(p1);
        assertEquals(p1, p2);
        
        p1.setGridlinePaintZ(new GradientPaint(9f, 10f, Color.GREEN, 11f, 12f, 
                Color.LIGHT_GRAY));
        p2 = (XYZPlot) TestUtils.serialized(p1);
        assertEquals(p1, p2);
    }

    /**
     * https://github.com/jfree/orson-charts/issues/9
     */
    @Test
    public void testBug9() {
        XYZSeries<String> series = new XYZSeries<>("S1");
        series.add(5.0, 6.0, 7.0);
        series.add(8.0, 9.0, 10.0);
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<>();
        dataset.add(series);

        NumberAxis3D xAxis = new NumberAxis3D("X", new Range(0, 10));
        NumberAxis3D yAxis = new NumberAxis3D("Y", new Range(0, 10));
        NumberAxis3D zAxis = new NumberAxis3D("Z", new Range(0, 10));
        XYZRenderer renderer = new ScatterXYZRenderer();
        XYZPlot plot = new XYZPlot(dataset, renderer, xAxis, yAxis, zAxis);
        assertEquals(new Range(4.85, 8.15), xAxis.getRange());
        xAxis.setAutoRangeIncludeZero(true);
        assertEquals(new Range(0.0, 8.15), xAxis.getRange());
        assertEquals(new Range(5.85, 9.15), yAxis.getRange());
        yAxis.setAutoRangeIncludeZero(true);
        assertEquals(new Range(0.0, 9.15), yAxis.getRange());
        assertEquals(new Range(6.85, 10.15), zAxis.getRange());
        zAxis.setAutoRangeIncludeZero(true);
        assertEquals(new Range(0.0, 10.15), zAxis.getRange());
    }
    
    /**
     * Returns a new dataset.
     * 
     * @return A new dataset.
     */
    private XYZDataset<String> createNewDataset() {
        return new XYZSeriesCollection<>();
    }
  
    /**
     * Returns a new plot with no assigned dataset.
     * 
     * @return A new plot. 
     */
    private XYZPlot createXYZPlot() {
        ValueAxis3D xAxis = new NumberAxis3D("X", new Range(0, 10));
        ValueAxis3D yAxis = new NumberAxis3D("Y", new Range(0, 10));
        ValueAxis3D zAxis = new NumberAxis3D("Z", new Range(0, 10));
        XYZRenderer renderer = new ScatterXYZRenderer();
        return new XYZPlot(createNewDataset(), renderer, xAxis, yAxis, zAxis);
    }

    private Plot3DChangeEvent lastEvent = null;
  
    @Override
    public void plotChanged(Plot3DChangeEvent event) {
        this.lastEvent = event;
    }
}
