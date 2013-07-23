/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import org.jfree.chart3d.event.Axis3DChangeEvent;
import org.jfree.chart3d.event.Axis3DChangeListener;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Checks for the {@link NumberAxis3D} class.
 */
public class NumberAxis3DTests implements Axis3DChangeListener {
    
  @Test
  public void checkSetLabelFiresChangeEvent() {
    NumberAxis3D axis1 = new NumberAxis3D("X", new Range(0, 10));
    axis1.addChangeListener(this);
    assertNull(this.lastEvent);
    
    axis1.setLabel("XXX");
    assertNotNull(this.lastEvent);
    this.lastEvent = null;
  }
  
  @Test
  public void checkSetLabelFontFiresChangeEvent() {
    NumberAxis3D axis1 = new NumberAxis3D("X", new Range(0, 10));
    axis1.addChangeListener(this);
    assertNull(this.lastEvent);
    
    axis1.setLabelFont(new Font("Dialog", Font.BOLD, 7));
    assertNotNull(this.lastEvent);
    this.lastEvent = null;
  }
  
  @Test
  public void checkSetLineStrokeFiresChangeEvent() {
    NumberAxis3D axis1 = new NumberAxis3D("X", new Range(0, 10));
    axis1.addChangeListener(this);
    assertNull(this.lastEvent);
    
    axis1.setLineStroke(new BasicStroke(1.0f));
    assertNotNull(this.lastEvent);
    this.lastEvent = null;
  }
  
  @Test
  public void checkSetLineColorFiresChangeEvent() {
    NumberAxis3D axis1 = new NumberAxis3D("X", new Range(0, 10));
    axis1.addChangeListener(this);
    assertNull(this.lastEvent);
    
    axis1.setLineColor(Color.RED);
    assertNotNull(this.lastEvent);
    this.lastEvent = null;
  }
  
  @Test
  public void checkSetRangeFiresChangeEvent() {
    NumberAxis3D axis1 = new NumberAxis3D("X", new Range(0, 10));
    axis1.addChangeListener(this);
    assertNull(this.lastEvent);
    
    axis1.setRange(new Range(1, 2));
    assertNotNull(this.lastEvent);
    this.lastEvent = null;
  }
  
  
  @Test
  public void checkSetTickSizeFiresChangeEvent() {
    NumberAxis3D axis1 = new NumberAxis3D("X", new Range(0, 10));
    axis1.addChangeListener(this);
    assertNull(this.lastEvent);
    
    axis1.setTickSize(5.0);
    assertNotNull(this.lastEvent);
    this.lastEvent = null;
  }  

  @Test
  public void checkEquals() {
    NumberAxis3D axis1 = new NumberAxis3D("X", new Range(0, 10));
    NumberAxis3D axis2 = new NumberAxis3D("X", new Range(0, 10));
    assertEquals(axis1, axis2);
    
    axis1.setLabel("ZZZ");
    assertNotEquals(axis1, axis2);
    axis2.setLabel("ZZZ");
    assertEquals(axis1, axis2);
    
    axis1.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
    assertNotEquals(axis1, axis2);
    axis2.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
    assertEquals(axis1, axis2);
    
    axis1.setLineStroke(new BasicStroke(2.0f));
    assertNotEquals(axis1, axis2);
    axis2.setLineStroke(new BasicStroke(2.0f));
    assertEquals(axis1, axis2);

    axis1.setLineColor(Color.RED);
    assertNotEquals(axis1, axis2);
    axis2.setLineColor(Color.RED);
    assertEquals(axis1, axis2);
    
    axis1.setRange(new Range(1, 2));
    assertNotEquals(axis1, axis2);
    axis2.setRange(new Range(1, 2));
    assertEquals(axis1, axis2);

    axis1.setTickSize(1.23);
    assertNotEquals(axis1, axis2);
    axis2.setTickSize(1.23);
    assertEquals(axis1, axis2);

  }
  
  private Axis3DChangeEvent lastEvent;

  @Override
  public void axisChanged(Axis3DChangeEvent event) {
    this.lastEvent = event;
  }

}
