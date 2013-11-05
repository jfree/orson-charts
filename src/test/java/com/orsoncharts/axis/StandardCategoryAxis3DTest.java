/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link StandardCategoryAxis3D} class.
 */
public class StandardCategoryAxis3DTest {
    
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
        
        axis1.setLowerMargin(0.123);
        assertFalse(axis1.equals(axis2));
        axis2.setLowerMargin(0.123);
        assertTrue(axis1.equals(axis2));
        
        axis1.setUpperMargin(0.321);
        assertFalse(axis1.equals(axis2));
        axis2.setUpperMargin(0.321);
        assertTrue(axis1.equals(axis2));

        axis1.setFirstCategoryHalfWidth(true);
        assertFalse(axis1.equals(axis2));
        axis2.setFirstCategoryHalfWidth(true);
        assertTrue(axis1.equals(axis2));
        
        axis1.setLastCategoryHalfWidth(true);
        assertFalse(axis1.equals(axis2));
        axis2.setLastCategoryHalfWidth(true);
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickMarkLength(123);
        assertFalse(axis1.equals(axis2));
        axis2.setTickMarkLength(123);
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickMarkPaint(new GradientPaint(1f, 2f, Color.BLUE, 3f, 4f,
                Color.GREEN));
        assertFalse(axis1.equals(axis2));
        axis2.setTickMarkPaint(new GradientPaint(1f, 2f, Color.BLUE, 3f, 4f,
                Color.GREEN));
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickMarkStroke(new BasicStroke(1.23f));
        assertFalse(axis1.equals(axis2));
        axis2.setTickMarkStroke(new BasicStroke(1.23f));
        assertTrue(axis1.equals(axis2));        
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardCategoryAxis3D axis1 = new StandardCategoryAxis3D("T");
        StandardCategoryAxis3D axis2 = (StandardCategoryAxis3D) 
                TestUtils.serialized(axis1);
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickMarkPaint(new GradientPaint(9f, 10f, Color.PINK, 11f, 12f,
                Color.BLACK));
        axis2 = (StandardCategoryAxis3D) TestUtils.serialized(axis1);
        assertTrue(axis1.equals(axis2));
    }

}
