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
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Tests for the {@link VerticalFlowElement} class.
 */
public class VerticalFlowElementTest {
    
    @Test
    public void testEquals() {
        VerticalFlowElement vfe1 = new VerticalFlowElement(VAlign.TOP, 11);
        VerticalFlowElement vfe2 = new VerticalFlowElement(VAlign.TOP, 11);
        assertTrue(vfe1.equals(vfe2));
        
        vfe1.addElement(new TextElement("ABC"));
        assertFalse(vfe1.equals(vfe2));
        vfe2.addElement(new TextElement("ABC"));
        assertTrue(vfe1.equals(vfe2));
        
        vfe1.setVGap(33);
        assertFalse(vfe1.equals(vfe2));
        vfe2.setVGap(33);
        assertTrue(vfe1.equals(vfe2));
        
        vfe1.setVerticalAlignment(VAlign.MIDDLE);
        assertFalse(vfe1.equals(vfe2));
        vfe2.setVerticalAlignment(VAlign.MIDDLE);
        assertTrue(vfe1.equals(vfe2));
    }
    
    @Test
    public void testSerialization() {
        VerticalFlowElement vfe1 = new VerticalFlowElement();
        VerticalFlowElement vfe2 
                = (VerticalFlowElement) TestUtils.serialized(vfe1);
        assertTrue(vfe1.equals(vfe2));
    }
    
    private static final double EPSILON = 0.00000001;
    
    @Test
    public void testPreferredSize1() {
        BufferedImage img = new BufferedImage(100, 50, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        VerticalFlowElement vfe = new VerticalFlowElement();
        ShapeElement se1 = new ShapeElement(new Rectangle(10, 5), Color.BLACK);
        se1.setInsets(new Insets(0, 0, 0, 0));
        vfe.addElement(se1);
        Dimension2D dim = vfe.preferredSize(g2, new Rectangle(100, 50));
        assertEquals(14.0, dim.getWidth(), EPSILON);
        assertEquals(9.0, dim.getHeight(), EPSILON);
        
        // although this element looks at the width of the bounds to determine
        // the layout, it will still return the minimum required size as the
        // preferred size, even if this exceeds the bounds
        dim = vfe.preferredSize(g2, new Rectangle(10, 5));
        assertEquals(14.0, dim.getWidth(), EPSILON);
        assertEquals(9.0, dim.getHeight(), EPSILON);
    }
    
    /**
     * Here we check to ensure that the first item in a new column is taken
     * into consideration for the height even if it is the last item in the
     * layout.
     */
    @Test
    public void testPreferredSize2() {
        BufferedImage img = new BufferedImage(100, 50, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        VerticalFlowElement vfe = new VerticalFlowElement();
        ShapeElement se1 = new ShapeElement(new Rectangle(10, 5), Color.BLACK);
        se1.setInsets(new Insets(0, 0, 0, 0));
        ShapeElement se2 = new ShapeElement(new Rectangle(20, 10), Color.BLACK);
        se2.setInsets(new Insets(0, 0, 0, 0));
        vfe.addElement(se1);
        vfe.addElement(se2);
        Dimension2D dim = vfe.preferredSize(g2, new Rectangle(24, 14));
        assertEquals(34.0, dim.getWidth(), EPSILON);
        assertEquals(14.0, dim.getHeight(), EPSILON);
    }
    
    @Test
    public void testForLayoutRoundingBug() {
        VerticalFlowElement vfe = new VerticalFlowElement(VAlign.TOP, 2);
        ShapeElement s1 = new ShapeElement(new Rectangle2D.Double(0, 0, 11.25, 7.25), Color.RED);
        s1.setInsets(new Insets(0, 0, 0, 0));
        ShapeElement s2 = new ShapeElement(new Rectangle2D.Double(0, 0, 11.25, 7.25), Color.RED);
        s2.setInsets(new Insets(0, 0, 0, 0));
        GridElement<String, String> grid1 = new GridElement<String, String>();
        grid1.setInsets(new Insets(0, 0, 0, 0));
        grid1.setElement(s1, "R1", "C1");
        grid1.setElement(s2, "R1", "C2");
        ShapeElement s3 = new ShapeElement(new Rectangle2D.Double(0, 0, 11.25, 7.25), Color.RED);
        s3.setInsets(new Insets(0, 0, 0, 0));
        ShapeElement s4 = new ShapeElement(new Rectangle2D.Double(0, 0, 11.25, 7.25), Color.RED);
        s4.setInsets(new Insets(0, 0, 0, 0));
        GridElement<String, String> grid2 = new GridElement<String, String>();
        grid2.setInsets(new Insets(0, 0, 0, 0));
        grid2.setElement(s3, "R1", "C1");
        grid2.setElement(s4, "R1", "C2");
        vfe.addElement(grid1);
        vfe.addElement(grid2);
        
        // now test that the preferred size and the layout match
        BufferedImage img = new BufferedImage(100, 50, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        Dimension2D prefSize = vfe.preferredSize(g2, new Rectangle(300, 200));
        // 11.25 * 2 + insets (8 = 4 * 2)
        //assertEquals(30.5, prefSize.getWidth(), EPSILON);
        // 7.25 * 2 + vgap (2) + insets (2 + 2)?
        //assertEquals(20.5, prefSize.getHeight(), EPSILON);
        
        List<Rectangle2D> layout = vfe.layoutElements(g2, 
                new Rectangle2D.Double(0.0, 0.0, prefSize.getWidth(), prefSize.getHeight()), null);
    }
}
