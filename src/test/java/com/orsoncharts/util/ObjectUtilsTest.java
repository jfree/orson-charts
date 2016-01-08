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
