/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import com.orsoncharts.Range;

/**
 * Checks for the {@link NumberAxis3D} class.
 */
public class NumberAxis3DTests implements Axis3DChangeListener {
    
    @Test
    public void checkSetVisibleFiresChangeEvent() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        axis1.addChangeListener(this);
        assertNull(this.lastEvent);
    
        axis1.setVisible(false);
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }

    @Test
    public void checkSetLabelFiresChangeEvent() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        axis1.addChangeListener(this);
        assertNull(this.lastEvent);
    
        axis1.setLabel("XXX");
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    public void checkSetLabelFontFiresChangeEvent() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        axis1.addChangeListener(this);
        assertNull(this.lastEvent);
    
        axis1.setLabelFont(new Font("Dialog", Font.BOLD, 7));
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    public void checkSetLineStrokeFiresChangeEvent() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        axis1.addChangeListener(this);
        assertNull(this.lastEvent);
    
        axis1.setLineStroke(new BasicStroke(1.0f));
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
   
    @Test
    public void checkSetLineColorFiresChangeEvent() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        axis1.addChangeListener(this);
        assertNull(this.lastEvent);
    
        axis1.setLineColor(Color.RED);
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    public void checkSetRangeFiresChangeEvent() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        axis1.addChangeListener(this);
        assertNull(this.lastEvent);
    
        axis1.setRange(new Range(1, 2));
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }

    @Test
    public void checkSetTickSizeFiresChangeEvent() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        axis1.addChangeListener(this);
        assertNull(this.lastEvent);
    
        axis1.setTickSize(5.0);
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }  

    @Test
    public void checkSetTickLabelFontFiresChangeEvent() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        axis1.addChangeListener(this);
        assertNull(this.lastEvent);
    
        axis1.setTickLabelFont(new Font("Dialog", Font.PLAIN, 11));
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }  

    @Test
    public void checkEquals() {
        NumberAxis3D axis1 = new NumberAxis3D("X");
        NumberAxis3D axis2 = new NumberAxis3D("X");
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
    
        axis1.setTickLabelFont(new Font("Dialog", Font.BOLD, 20));
        assertNotEquals(axis1, axis2);
        axis2.setTickLabelFont(new Font("Dialog", Font.BOLD, 20));
        assertEquals(axis1, axis2);

        axis1.setVisible(false);
        assertNotEquals(axis1, axis2);
        axis2.setVisible(false);
        assertEquals(axis1, axis2);
        
    }
  
    private Axis3DChangeEvent lastEvent;

    @Override
    public void axisChanged(Axis3DChangeEvent event) {
        this.lastEvent = event;
    }

}
