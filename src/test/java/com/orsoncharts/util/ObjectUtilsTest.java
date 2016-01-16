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

package com.orsoncharts.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

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
 
    @Test
    public void testEqualsPaint_LinearGradientPaint() {
        Point2D start1 = new Point2D.Float(0, 0);
        Point2D end1 = new Point2D.Float(50, 50);
        float[] dist1 = {0.0f, 0.2f, 1.0f};
        Color[] colors1 = {Color.RED, Color.WHITE, Color.BLUE};
        LinearGradientPaint p1 = new LinearGradientPaint(start1, end1, dist1, 
                colors1);    

        Point2D start2 = new Point2D.Float(0, 0);
        Point2D end2 = new Point2D.Float(50, 50);
        float[] dist2 = {0.0f, 0.2f, 1.0f};
        Color[] colors2 = {Color.RED, Color.WHITE, Color.BLUE};
        LinearGradientPaint p2 = new LinearGradientPaint(start2, end2, dist2, 
                colors2);
        assertTrue(ObjectUtils.equalsPaint(p1, p2));
        assertFalse(ObjectUtils.equalsPaint(p1, Color.RED));
        assertFalse(ObjectUtils.equalsPaint(p1, null));
        assertFalse(ObjectUtils.equalsPaint(null, p1));
        
        // check ColorSpaceType
        p1 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB, 
                new AffineTransform());
        p2 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.SRGB, 
                new AffineTransform());
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB, 
                new AffineTransform());
        assertTrue(ObjectUtils.equalsPaint(p1, p2));

        // check transform
        p1 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB, 
                AffineTransform.getTranslateInstance(1.0, 2.0));
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB, 
                AffineTransform.getTranslateInstance(1.0, 2.0));
        assertTrue(ObjectUtils.equalsPaint(p1, p2));
    }
    
    @Test
    public void testEqualsPaint_RadialGradientPaint() {
        RadialGradientPaint p1 = new RadialGradientPaint(1.0f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        RadialGradientPaint p2 = new RadialGradientPaint(1.0f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(ObjectUtils.equalsPaint(p1, p2));
        assertFalse(ObjectUtils.equalsPaint(p1, Color.RED));
        assertFalse(ObjectUtils.equalsPaint(p1, null));
        assertFalse(ObjectUtils.equalsPaint(null, p1));

        p1 = new RadialGradientPaint(1.1f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(ObjectUtils.equalsPaint(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(ObjectUtils.equalsPaint(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(ObjectUtils.equalsPaint(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(ObjectUtils.equalsPaint(p1, p2));
    
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE});
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE});
        assertTrue(ObjectUtils.equalsPaint(p1, p2));
        
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE},
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertTrue(ObjectUtils.equalsPaint(p1, p2));
        
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 4.4f, 5.5f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 4.4f, 5.5f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE},
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertTrue(ObjectUtils.equalsPaint(p1, p2));

        // check ColorSpaceType
        Point2D center = new Point2D.Float(1.1f, 2.2f);
        float radius = 3.3f;
        Point2D focus = new Point2D.Float(4.4f, 5.5f);
        p1 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.SRGB, 
                new AffineTransform());
        p2 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB,
                new AffineTransform());
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.SRGB,
                new AffineTransform());        
        assertTrue(ObjectUtils.equalsPaint(p1, p2));

        // check transform
        p1 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.SRGB, 
                AffineTransform.getTranslateInstance(1.0, 2.0));
        assertFalse(ObjectUtils.equalsPaint(p1, p2));
        p2 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.SRGB, 
                AffineTransform.getTranslateInstance(1.0, 2.0));
        assertTrue(ObjectUtils.equalsPaint(p1, p2));
    }
}
