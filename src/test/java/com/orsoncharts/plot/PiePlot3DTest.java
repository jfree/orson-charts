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
import com.orsoncharts.data.StandardPieDataset3D;

/** 
 * Some tests for the {@link PiePlot3D} class.
 */
public class PiePlot3DTest implements Plot3DChangeListener {

    @Test
    public void checkSetRadiusFiresChangeEvent() {
        PiePlot3D plot = createPiePlot3D();
        plot.addChangeListener(this);
        assertNull(this.lastEvent);
    
        plot.setRadius(10.2);
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    public void checkSetDepthFiresChangeEvent() {
        PiePlot3D plot = createPiePlot3D();
        plot.addChangeListener(this);
        assertNull(this.lastEvent);
    
        plot.setDepth(0.123);
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    public void checkSetDatasetFiresChangeEvent() {
        PiePlot3D plot = createPiePlot3D();
        plot.addChangeListener(this);
        assertNull(this.lastEvent);
     
        plot.setDataset(createNewDataset());
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    public void checkSetDatasetRemovesPreviousListener() {
        StandardPieDataset3D dataset1 = createNewDataset();
        PiePlot3D plot = new PiePlot3D(dataset1);
        assertTrue(dataset1.hasListener(plot));
        StandardPieDataset3D dataset2 = createNewDataset();
        plot.setDataset(dataset2);
        assertFalse(dataset1.hasListener(plot));
        assertTrue(dataset2.hasListener(plot));
    }
  
    /**
     * Check that all the required fields are taken into a account by the
     * equals() method.
     */
    @Test
    public void checkEquals() {
        StandardPieDataset3D dataset1 = createNewDataset();
        PiePlot3D p1 = new PiePlot3D(dataset1);
        PiePlot3D p2 = new PiePlot3D(dataset1);
        assertEquals(p1, p2);
    
        // radius
        p1.setRadius(12.3);
        assertFalse(p1.equals(p2));
        p2.setRadius(12.3);
        assertTrue(p1.equals(p2));
        
        // depth
        p1.setDepth(3.21);
        assertFalse(p1.equals(p2));
        p2.setDepth(3.21);
        assertTrue(p1.equals(p2));
    }
  
    /**
     * Creates and returns a new dataset (it is important for the tests that 
     * this method always returns a new dataset).
     * 
     * @return A new dataset. 
     */
    private StandardPieDataset3D createNewDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("A", 123.4);
        return dataset;
    }
  
    private PiePlot3D createPiePlot3D() {
        return new PiePlot3D(createNewDataset());
    }

    private Plot3DChangeEvent lastEvent = null;
  
    @Override
    public void plotChanged(Plot3DChangeEvent event) {
        this.lastEvent = event;
    }
}
