/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import com.orsoncharts.TitleAnchor;

/**
 * Tests for the {@link Fit2D} class.
 */
public class Fit2DTest {
    
    private static double EPSILON = 0.00000001;
    
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
}
