/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
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

package org.jfree.chart3d.renderer.category;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import org.jfree.chart3d.TestUtils;
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