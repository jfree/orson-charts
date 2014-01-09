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

package com.orsoncharts.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link Fit2D} class.
 */
public class Fit2DTest {
    
    private static double EPSILON = 0.00000001;
    
    @Test
    public void testEquals() {
        Fit2D f1 = new Fit2D(TitleAnchor.TOP_LEFT, Scale2D.SCALE_BOTH);
        Fit2D f2 = new Fit2D(TitleAnchor.TOP_LEFT, Scale2D.SCALE_BOTH);
        assertTrue(f1.equals(f2));
        
        f1 = new Fit2D(TitleAnchor.CENTER_LEFT, Scale2D.SCALE_BOTH);
        assertFalse(f1.equals(f2));
        f2 = new Fit2D(TitleAnchor.CENTER_LEFT, Scale2D.SCALE_BOTH);
        assertTrue(f1.equals(f2));
        
        f1 = new Fit2D(TitleAnchor.CENTER_LEFT, Scale2D.NONE);
        assertFalse(f1.equals(f2));
        f2 = new Fit2D(TitleAnchor.CENTER_LEFT, Scale2D.NONE);
        assertTrue(f1.equals(f2));
    }
    
    /**
     * For SCALE_BOTH the result will always fill the target - the anchor is
     * not important.
     */
    @Test
    public void testFit_ScaleBoth() {
        Fit2D f = new Fit2D(TitleAnchor.TOP_LEFT, Scale2D.SCALE_BOTH);
        Rectangle2D rect = f.fit(new Dimension(4, 5), 
                new Rectangle(10, 10, 30, 20));
        assertEquals(10, rect.getX(), EPSILON);
        assertEquals(10, rect.getY(), EPSILON);
        assertEquals(30, rect.getWidth(), EPSILON);
        assertEquals(20, rect.getHeight(), EPSILON);
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization() {
        Fit2D f1 = new Fit2D(TitleAnchor.BOTTOM_CENTER, Scale2D.SCALE_BOTH);
        Fit2D f2 = (Fit2D) TestUtils.serialized(f1);
        assertEquals(f1, f2);
    }
}
