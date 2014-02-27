/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.marker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import com.orsoncharts.TestUtils;
import com.orsoncharts.util.Anchor2D;

/**
 * Tests for the {@link CategoryMarker} class.
 */
public class CategoryMarkerTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        CategoryMarker cm1 = new CategoryMarker("A"); 
        CategoryMarker cm2 = new CategoryMarker("A"); 
        assertTrue(cm1.equals(cm2));
        assertFalse(cm1.equals(null));
        
        cm1 = new CategoryMarker("B");
        assertFalse(cm1.equals(cm2));
        cm2 = new CategoryMarker("B");
        assertTrue(cm1.equals(cm2));
        
        // CategoryMarkerType type;
        cm1.setType(CategoryMarkerType.LINE);
        assertFalse(cm1.equals(cm2));
        cm2.setType(CategoryMarkerType.LINE);
        assertTrue(cm1.equals(cm2));
        
        // String label;
        cm1.setLabel("ABC");
        assertFalse(cm1.equals(cm2));
        cm2.setLabel("ABC");
        assertTrue(cm1.equals(cm2));
        
        // Font font;
        cm1.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        assertFalse(cm1.equals(cm2));
        cm2.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        assertTrue(cm1.equals(cm2));

        // Color labelColor;
        cm1.setLabelColor(Color.CYAN);
        assertFalse(cm1.equals(cm2));
        cm2.setLabelColor(Color.CYAN);
        assertTrue(cm1.equals(cm2));
        
        // Anchor2D labelAnchor;
        cm1.setLabelAnchor(Anchor2D.BOTTOM_LEFT);
        assertFalse(cm1.equals(cm2));
        cm2.setLabelAnchor(Anchor2D.BOTTOM_LEFT);
        assertTrue(cm1.equals(cm2));
        
        // transient Stroke lineStroke;
        cm1.setLineStroke(new BasicStroke(0.6f));
        assertFalse(cm1.equals(cm2));
        cm2.setLineStroke(new BasicStroke(0.6f));
        assertTrue(cm1.equals(cm2));
        
        // Color lineColor;
        cm1.setLineColor(Color.YELLOW);
        assertFalse(cm1.equals(cm2));
        cm2.setLineColor(Color.YELLOW);
        assertTrue(cm1.equals(cm2));
        
        // Color fillColor;
        cm1.setFillColor(Color.GREEN);
        assertFalse(cm1.equals(cm2));
        cm2.setFillColor(Color.GREEN);
        assertTrue(cm1.equals(cm2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        CategoryMarker cm1 = new CategoryMarker("A");
        cm1.setLabel("ABC");
        cm1.setLabelAnchor(Anchor2D.BOTTOM_RIGHT);
        cm1.setLabelColor(Color.GREEN);
        cm1.setFillColor(Color.DARK_GRAY);
        cm1.setLineStroke(new BasicStroke(0.6f));
        cm1.setLineColor(Color.RED);
        CategoryMarker cm2 = (CategoryMarker) TestUtils.serialized(cm1);
        assertTrue(cm1.equals(cm2));
    }
}
