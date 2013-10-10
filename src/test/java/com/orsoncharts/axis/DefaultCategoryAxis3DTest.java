/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Tests for the {@link DefaultCategoryAxis3D} class.
 */
public class DefaultCategoryAxis3DTest {
    
    @Test
    public void testEquals() {
        StandardCategoryAxis3D axis1 = new StandardCategoryAxis3D("Label");   
        StandardCategoryAxis3D axis2 = new StandardCategoryAxis3D("Label"); 
        assertTrue(axis1.equals(axis2));
        assertFalse(axis1.equals(null));
        
        // axis label
        axis1.setLabel("Label2");
        assertFalse(axis1.equals(axis2));
        axis2.setLabel("Label2");
        assertTrue(axis1.equals(axis2));
        
        // axis label can be null also
        axis1.setLabel(null);
        assertFalse(axis1.equals(axis2));
        axis2.setLabel(null);
        assertTrue(axis1.equals(axis2));

        // label font
        axis1.setLabelFont(new Font("Dialog", Font.PLAIN, 2));
        assertFalse(axis1.equals(axis2));
        axis2.setLabelFont(new Font("Dialog", Font.PLAIN, 2));
        assertTrue(axis1.equals(axis2));
        
        // label paint
        axis1.setLabelPaint(Color.RED);
        assertFalse(axis1.equals(axis2));
        axis2.setLabelPaint(Color.RED);
        assertTrue(axis1.equals(axis2));
        
        // line stroke
        axis1.setLineStroke(new BasicStroke(0.5f));
        assertFalse(axis1.equals(axis2));
        axis2.setLineStroke(new BasicStroke(0.5f));
        assertTrue(axis1.equals(axis2));
        
        // line color
        axis1.setLineColor(Color.BLUE);
        assertFalse(axis1.equals(axis2));
        axis2.setLineColor(Color.BLUE);
        assertTrue(axis1.equals(axis2));
        
        // tickLabelsVisible
        axis1.setTickLabelsVisible(false);
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelsVisible(false);
        assertTrue(axis1.equals(axis2));
        
        // tick label font
        axis1.setTickLabelFont(new Font("Dialog", Font.PLAIN, 5));
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelFont(new Font("Dialog", Font.PLAIN, 5));
        assertTrue(axis1.equals(axis2));
        
        // tick label paint
        GradientPaint gp1 = new GradientPaint(1f, 2f, Color.RED, 3f, 4f, 
                Color.CYAN);
        GradientPaint gp2 = new GradientPaint(1f, 2f, Color.RED, 3f, 4f, 
                Color.CYAN);
        axis1.setTickLabelPaint(gp1);
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelPaint(gp2);
        assertTrue(axis1.equals(axis2));

        // axis visible
        axis1.setVisible(false);
        assertFalse(axis1.equals(axis2));
        axis2.setVisible(false);
        assertTrue(axis1.equals(axis2));
        
        axis1.setFirstCategoryHalfWidth(true);
        assertFalse(axis1.equals(axis2));
        axis2.setFirstCategoryHalfWidth(true);
        assertTrue(axis1.equals(axis2));
        
        axis1.setLastCategoryHalfWidth(true);
        assertFalse(axis1.equals(axis2));
        axis2.setLastCategoryHalfWidth(true);
        assertTrue(axis1.equals(axis2));
       
    }
}
