/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
    private XYZDataset createNewDataset() {
        XYZSeriesCollection dataset = new XYZSeriesCollection();
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
