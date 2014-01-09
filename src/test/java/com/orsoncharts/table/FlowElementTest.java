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

package com.orsoncharts.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link FlowElement} class.
 */
public class FlowElementTest {
    
    @Test
    public void testEquals() {
        FlowElement fe1 = new FlowElement(HAlign.LEFT, 11);
        FlowElement fe2 = new FlowElement(HAlign.LEFT, 11);
        assertTrue(fe1.equals(fe2));
        
        fe1.addElement(new TextElement("ABC"));
        assertFalse(fe1.equals(fe2));
        fe2.addElement(new TextElement("ABC"));
        assertTrue(fe1.equals(fe2));
        
        fe1.setHGap(33);
        assertFalse(fe1.equals(fe2));
        fe2.setHGap(33);
        assertTrue(fe1.equals(fe2));
        
        fe1.setHorizontalAlignment(HAlign.RIGHT);
        assertFalse(fe1.equals(fe2));
        fe2.setHorizontalAlignment(HAlign.RIGHT);
        assertTrue(fe1.equals(fe2));
    }
    
    @Test
    public void testSerialization() {
        FlowElement fe1 = new FlowElement();
        FlowElement fe2 = (FlowElement) TestUtils.serialized(fe1);
        assertTrue(fe1.equals(fe2));
    }
    
    private static final double EPSILON = 0.00000001;
    
    @Test
    public void testPreferredSize1() {
        BufferedImage img = new BufferedImage(100, 50, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        FlowElement fe = new FlowElement();
        ShapeElement se1 = new ShapeElement(new Rectangle(10, 5), Color.BLACK);
        se1.setInsets(new Insets(0, 0, 0, 0));
        fe.addElement(se1);
        Dimension2D dim = fe.preferredSize(g2, new Rectangle(100, 50));
        assertEquals(14.0, dim.getWidth(), EPSILON);
        assertEquals(9.0, dim.getHeight(), EPSILON);
        
        // although this element looks at the width of the bounds to determine
        // the layout, it will still return the minimum required size as the
        // preferred size, even if this exceeds the bounds
        dim = fe.preferredSize(g2, new Rectangle(10, 5));
        assertEquals(14.0, dim.getWidth(), EPSILON);
        assertEquals(9.0, dim.getHeight(), EPSILON);
    }
    
    /**
     * Here we check to ensure that the first item on a new line is taken
     * into consideration for the width even if it is the last item in the
     * layout (this was a bug in the 1.0 release).
     */
    @Test
    public void testPreferredSize2() {
        BufferedImage img = new BufferedImage(100, 50, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        FlowElement fe = new FlowElement();
        ShapeElement se1 = new ShapeElement(new Rectangle(10, 5), Color.BLACK);
        se1.setInsets(new Insets(0, 0, 0, 0));
        ShapeElement se2 = new ShapeElement(new Rectangle(20, 5), Color.BLACK);
        se2.setInsets(new Insets(0, 0, 0, 0));
        fe.addElement(se1);
        fe.addElement(se2);
        Dimension2D dim = fe.preferredSize(g2, new Rectangle(24, 20));
        assertEquals(24.0, dim.getWidth(), EPSILON);
        assertEquals(14.0, dim.getHeight(), EPSILON);
    }
}
