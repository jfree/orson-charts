/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
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

import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.xyz.DefaultXYZDataset;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.renderer.xyz.XYZRenderer;

/**
 * Checks for the {@link XYZPlot} class.
 */
public class XYZPlotTests implements Plot3DChangeListener {
    
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
        DefaultXYZDataset dataset1 = createNewDataset();
        XYZPlot plot = createXYZPlot();
        plot.setDataset(dataset1);
        assertTrue(dataset1.hasListener(plot));
        DefaultXYZDataset dataset2 = createNewDataset();
        plot.setDataset(dataset2);
        assertFalse(dataset1.hasListener(plot));
        assertTrue(dataset2.hasListener(plot));
    }
  
    @Test
    public void checkEquals() {
        XYZPlot plot1 = createXYZPlot();
        XYZPlot plot2 = createXYZPlot();
        assertEquals(plot1, plot2);
    
        plot1.setDimensions(new Dimension3D(1, 2, 3));
        assertNotEquals(plot1, plot2);
        plot2.setDimensions(new Dimension3D(1, 2, 3));
        assertEquals(plot1, plot2);
    }

    /**
     * Returns a new dataset.
     * 
     * @return A new dataset.
     */
    private DefaultXYZDataset createNewDataset() {
        DefaultXYZDataset dataset = new DefaultXYZDataset(3, 10);
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
