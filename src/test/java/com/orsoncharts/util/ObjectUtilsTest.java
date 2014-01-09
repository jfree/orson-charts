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

import java.awt.Color;
import java.awt.GradientPaint;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Some tests for the {@link ObjectUtils} class.
 */
public class ObjectUtilsTest {

    @Test
    public void testEquals() {
        assertTrue(ObjectUtils.equals(null, null));
        assertFalse(ObjectUtils.equals(null, "A"));
        assertFalse(ObjectUtils.equals("A", null));
        assertTrue(ObjectUtils.equals("A", "A"));
        assertFalse(ObjectUtils.equals("A", "B"));
    }
    
    @Test
    public void testEqualsPaint() {
        assertTrue(ObjectUtils.equalsPaint(null, null));
        assertFalse(ObjectUtils.equalsPaint(null, Color.RED));
        assertFalse(ObjectUtils.equals(Color.RED, null));
        assertTrue(ObjectUtils.equals(Color.RED, Color.RED));
        assertFalse(ObjectUtils.equals(Color.RED, Color.GREEN));
    }
    
    @Test
    public void testEqualsPaint_GradientPaint() {
        GradientPaint gp1 = new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE);
        GradientPaint gp2 = new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE);
        assertTrue(ObjectUtils.equalsPaint(gp1, gp2));
        assertTrue(ObjectUtils.equalsPaint(null, null));
        assertFalse(ObjectUtils.equalsPaint(gp1, null));
        assertFalse(ObjectUtils.equalsPaint(null, gp2));
        
        gp1 = new GradientPaint(1.1f, 2.0f, Color.RED, 3.0f, 4.0f, Color.BLUE);
        assertFalse(ObjectUtils.equalsPaint(gp1, gp2));
        gp2 = new GradientPaint(1.1f, 2.0f, Color.RED, 3.0f, 4.0f, Color.BLUE);
        assertTrue(ObjectUtils.equalsPaint(gp1, gp2));

        gp1 = new GradientPaint(1.1f, 2.2f, Color.RED, 3.0f, 4.0f, Color.BLUE);
        assertFalse(ObjectUtils.equalsPaint(gp1, gp2));
        gp2 = new GradientPaint(1.1f, 2.2f, Color.RED, 3.0f, 4.0f, Color.BLUE);
        assertTrue(ObjectUtils.equalsPaint(gp1, gp2));

        gp1 = new GradientPaint(1.1f, 2.2f, Color.GRAY, 3.0f, 4.0f, Color.BLUE);
        assertFalse(ObjectUtils.equalsPaint(gp1, gp2));
        gp2 = new GradientPaint(1.1f, 2.2f, Color.GRAY, 3.0f, 4.0f, Color.BLUE);
        assertTrue(ObjectUtils.equalsPaint(gp1, gp2));

        gp1 = new GradientPaint(1.1f, 2.2f, Color.GRAY, 3.3f, 4.0f, Color.BLUE);
        assertFalse(ObjectUtils.equalsPaint(gp1, gp2));
        gp2 = new GradientPaint(1.1f, 2.2f, Color.GRAY, 3.3f, 4.0f, Color.BLUE);
        assertTrue(ObjectUtils.equalsPaint(gp1, gp2));

        gp1 = new GradientPaint(1.1f, 2.2f, Color.GRAY, 3.3f, 4.4f, Color.BLUE);
        assertFalse(ObjectUtils.equalsPaint(gp1, gp2));
        gp2 = new GradientPaint(1.1f, 2.2f, Color.GRAY, 3.3f, 4.4f, Color.BLUE);
        assertTrue(ObjectUtils.equalsPaint(gp1, gp2));

        gp1 = new GradientPaint(1.1f, 2.2f, Color.GRAY, 3.3f, 4.4f, Color.RED);
        assertFalse(ObjectUtils.equalsPaint(gp1, gp2));
        gp2 = new GradientPaint(1.1f, 2.2f, Color.GRAY, 3.3f, 4.4f, Color.RED);
        assertTrue(ObjectUtils.equalsPaint(gp1, gp2));
    }
 
}
