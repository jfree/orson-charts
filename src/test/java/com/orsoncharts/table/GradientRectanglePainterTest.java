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
import com.orsoncharts.TestUtils;
import com.orsoncharts.TitleAnchor;

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
