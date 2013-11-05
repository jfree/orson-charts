/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.Color;
import java.awt.GradientPaint;

/**
 * Tests for the {@link StandardRectanglePainter} class.
 */
public class StandardRectanglePainterTest {
    
    @Test
    public void testEquals() {
        StandardRectanglePainter srp1 = new StandardRectanglePainter(Color.RED);
        StandardRectanglePainter srp2 = new StandardRectanglePainter(Color.RED);
        assertTrue(srp1.equals(srp2));
        
        srp1 = new StandardRectanglePainter(Color.BLUE);
        assertFalse(srp1.equals(srp2));
        srp2 = new StandardRectanglePainter(Color.BLUE);
        assertTrue(srp1.equals(srp2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization() {
        StandardRectanglePainter srp1 = new StandardRectanglePainter(Color.RED);
        StandardRectanglePainter srp2 
                = (StandardRectanglePainter) TestUtils.serialized(srp1);
        assertEquals(srp1, srp2);
        
        srp1 = new StandardRectanglePainter(new GradientPaint(1f, 2f, Color.RED,
                3f, 4f, Color.BLUE));
        srp2 = (StandardRectanglePainter) TestUtils.serialized(srp1);
        assertEquals(srp1, srp2);
    }

}

