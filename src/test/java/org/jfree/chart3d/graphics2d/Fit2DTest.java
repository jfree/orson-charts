/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2022, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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

package org.jfree.chart3d.graphics2d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.jfree.chart3d.TestUtils;
import org.jfree.chart3d.TitleAnchor;

/**
 * Tests for the {@link Fit2D} class.
 */
public class Fit2DTest {
    
    private static final double EPSILON = 0.00000001;
    
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
