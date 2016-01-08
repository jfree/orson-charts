/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
