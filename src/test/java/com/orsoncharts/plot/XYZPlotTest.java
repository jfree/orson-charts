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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.Range;
import com.orsoncharts.TestUtils;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.label.StandardXYZLabelGenerator;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.renderer.xyz.XYZRenderer;

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
        XYZDataset dataset1 = createNewDataset();
        XYZPlot plot = createXYZPlot();
        plot.setDataset(dataset1);
        assertTrue(dataset1.hasListener(plot));
        XYZDataset dataset2 = createNewDataset();
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
        assertFalse(p1.equals(p2));
        p2.setGridlinesVisibleX(false);
        assertTrue(p1.equals(p2));
        
        p1.setGridlinePaintX(Color.YELLOW);
        assertFalse(p1.equals(p2));
        p2.setGridlinePaintX(Color.YELLOW);
        assertTrue(p1.equals(p2));
        
        p1.setGridlineStrokeX(new BasicStroke(0.3f));
        assertFalse(p1.equals(p2));
        p2.setGridlineStrokeX(new BasicStroke(0.3f));
        assertTrue(p1.equals(p2));
        
        p1.setGridlinesVisibleY(false);
        assertFalse(p1.equals(p2));
        p2.setGridlinesVisibleY(false);
        assertTrue(p1.equals(p2));

        p1.setGridlinePaintY(Color.GREEN);
        assertFalse(p1.equals(p2));
        p2.setGridlinePaintY(Color.GREEN);
        assertTrue(p1.equals(p2));
        
        p1.setGridlineStrokeY(new BasicStroke(0.36f));
        assertFalse(p1.equals(p2));
        p2.setGridlineStrokeY(new BasicStroke(0.36f));
        assertTrue(p1.equals(p2));

        p1.setGridlinesVisibleZ(false);
        assertFalse(p1.equals(p2));
        p2.setGridlinesVisibleZ(false);
        assertTrue(p1.equals(p2));

        p1.setGridlinePaintZ(Color.BLUE);
        assertFalse(p1.equals(p2));
        p2.setGridlinePaintZ(Color.BLUE);
        assertTrue(p1.equals(p2));
        
        p1.setGridlineStrokeZ(new BasicStroke(0.6f));
        assertFalse(p1.equals(p2));
        p2.setGridlineStrokeZ(new BasicStroke(0.6f));
        assertTrue(p1.equals(p2));
        
        p1.setLegendLabelGenerator(new StandardXYZLabelGenerator("%s XX"));
        assertFalse(p1.equals(p2));
        p2.setLegendLabelGenerator(new StandardXYZLabelGenerator("%s XX"));
        assertTrue(p1.equals(p2));
    }

    /**
     * Checks for serialization.
     */
    @Test
    public void testSerialization() {
        XYZPlot p1 = createXYZPlot();
        XYZPlot p2 = (XYZPlot) TestUtils.serialized(p1);
        assertTrue(p1.equals(p2));
        
        p1.setGridlinePaintX(new GradientPaint(1f, 2f, Color.RED, 3f, 4f, 
                Color.BLUE));
        p2 = (XYZPlot) TestUtils.serialized(p1);
        assertTrue(p1.equals(p2));

        p1.setGridlinePaintY(new GradientPaint(5f, 6f, Color.GRAY, 7f, 8f, 
                Color.YELLOW));
        p2 = (XYZPlot) TestUtils.serialized(p1);
        assertTrue(p1.equals(p2));
        
        p1.setGridlinePaintZ(new GradientPaint(9f, 10f, Color.GREEN, 11f, 12f, 
                Color.LIGHT_GRAY));
        p2 = (XYZPlot) TestUtils.serialized(p1);
        assertTrue(p1.equals(p2));
    }
    
    /**
     * Returns a new dataset.
     * 
     * @return A new dataset.
     */
    private XYZDataset<String> createNewDataset() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        return dataset;
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
