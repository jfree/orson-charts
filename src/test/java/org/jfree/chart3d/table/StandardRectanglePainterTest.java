/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2020, by Object Refinery Limited.  All rights reserved.
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

package org.jfree.chart3d.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.GradientPaint;
import org.jfree.chart3d.TestUtils;

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

