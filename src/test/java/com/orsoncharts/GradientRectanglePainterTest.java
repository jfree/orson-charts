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

package com.orsoncharts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.Color;

/**
 * Tests for the {@link GradientRectanglePainter} class.
 */
public class GradientRectanglePainterTest {
    
    @Test
    public void testEquals() {
        GradientRectanglePainter grp1 = new GradientRectanglePainter(Color.RED,
                TitleAnchor.TOP_CENTER, Color.BLUE, TitleAnchor.BOTTOM_CENTER);
        GradientRectanglePainter grp2 = new GradientRectanglePainter(Color.RED,
                TitleAnchor.TOP_CENTER, Color.BLUE, TitleAnchor.BOTTOM_CENTER);
        assertTrue(grp1.equals(grp2));
        
        grp1 = new GradientRectanglePainter(Color.GRAY, TitleAnchor.TOP_CENTER, 
                Color.BLUE, TitleAnchor.BOTTOM_CENTER);
        assertFalse(grp1.equals(grp2));
        grp2 = new GradientRectanglePainter(Color.GRAY, TitleAnchor.TOP_CENTER, 
                Color.BLUE, TitleAnchor.BOTTOM_CENTER);
        assertTrue(grp1.equals(grp2));
 
        grp1 = new GradientRectanglePainter(Color.GRAY, TitleAnchor.TOP_LEFT,
                Color.BLUE, TitleAnchor.BOTTOM_CENTER);
        assertFalse(grp1.equals(grp2));
        grp2 = new GradientRectanglePainter(Color.GRAY, TitleAnchor.TOP_LEFT, 
                Color.BLUE, TitleAnchor.BOTTOM_CENTER);
        assertTrue(grp1.equals(grp2));

        grp1 = new GradientRectanglePainter(Color.GRAY, TitleAnchor.TOP_LEFT,
                Color.YELLOW, TitleAnchor.BOTTOM_CENTER);
        assertFalse(grp1.equals(grp2));
        grp2 = new GradientRectanglePainter(Color.GRAY, TitleAnchor.TOP_LEFT, 
                Color.YELLOW, TitleAnchor.BOTTOM_CENTER);
        assertTrue(grp1.equals(grp2));

        grp1 = new GradientRectanglePainter(Color.GRAY, TitleAnchor.TOP_LEFT,
                Color.YELLOW, TitleAnchor.BOTTOM_RIGHT);
        assertFalse(grp1.equals(grp2));
        grp2 = new GradientRectanglePainter(Color.GRAY, TitleAnchor.TOP_LEFT, 
                Color.YELLOW, TitleAnchor.BOTTOM_RIGHT);
        assertTrue(grp1.equals(grp2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization() {
        GradientRectanglePainter grp1 = new GradientRectanglePainter(Color.RED,
                TitleAnchor.TOP_CENTER, Color.BLUE, TitleAnchor.BOTTOM_CENTER);
        GradientRectanglePainter grp2 
                = (GradientRectanglePainter) TestUtils.serialized(grp1);
        assertEquals(grp1, grp2);
    }

}
