/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.plot;

import org.jfree.chart3d.axis.Axis3D;
import org.jfree.chart3d.axis.NumberAxis3D;
import org.jfree.chart3d.axis.Range;
import org.jfree.chart3d.data.DefaultXYZDataset;
import org.jfree.chart3d.event.Plot3DChangeEvent;
import org.jfree.chart3d.event.Plot3DChangeListener;
import org.jfree.graphics3d.Dimension3D;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
    Axis3D xAxis = new NumberAxis3D("X", new Range(0, 10));
    Axis3D yAxis = new NumberAxis3D("Y", new Range(0, 10));
    Axis3D zAxis = new NumberAxis3D("Z", new Range(0, 10));
    return new XYZPlot(createNewDataset(), xAxis, yAxis, zAxis);
  }

  private Plot3DChangeEvent lastEvent = null;
  
  @Override
  public void plotChanged(Plot3DChangeEvent event) {
    this.lastEvent = event;
  }
}
