/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.orsoncharts.TestUtils;
import java.awt.Color;

/**
 * Tests for the {@link LineRenderer3D} class.
 */
public class LineRenderer3DTest {

    @Test
    public void testEquals() {
        LineRenderer3D r1 = new LineRenderer3D();
        LineRenderer3D r2 = new LineRenderer3D();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        r1.setLineWidth(1.1);
        assertFalse(r1.equals(r2));
        r2.setLineWidth(1.1);
        assertTrue(r1.equals(r2));

        r1.setLineHeight(2.2);
        assertFalse(r1.equals(r2));
        r2.setLineHeight(2.2);
        assertTrue(r1.equals(r2));

        r1.setClipColorSource(new StandardCategoryColorSource(Color.BLUE));
        assertFalse(r1.equals(r2));
        r2.setClipColorSource(new StandardCategoryColorSource(Color.BLUE));
        assertTrue(r1.equals(r2));

        // notify
        r1.setNotify(false);
        assertFalse(r1.equals(r2));
        r2.setNotify(false);
        assertTrue(r1.equals(r2));
    }
    
    @Test
    public void testSerialization() {
        LineRenderer3D r1 = new LineRenderer3D();
        LineRenderer3D r2 = (LineRenderer3D) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }

}