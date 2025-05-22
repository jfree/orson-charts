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

package org.jfree.chart3d.graphics2d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.jfree.chart3d.TestUtils;
import org.jfree.chart3d.graphics3d.Offset2D;

/**
 * Some checks for the {@link Offset2D} class.
 */
public class Offset2DTest {
    
    @Test
    public void testEquals() {
        Offset2D off1 = new Offset2D(1.0, 2.0);
        Offset2D off2 = new Offset2D(1.0, 2.0);
        assertTrue(off1.equals(off2));
        assertFalse(off1.equals(null));
        
        off1 = new Offset2D(3.0, 2.0);
        assertFalse(off1.equals(off2));
        off2 = new Offset2D(3.0, 2.0);
        assertTrue(off1.equals(off2));

        off1 = new Offset2D(3.0, 4.0);
        assertFalse(off1.equals(off2));
        off2 = new Offset2D(3.0, 4.0);
        assertTrue(off1.equals(off2));
    }
    
    @Test
    public void testSerialization() {
        Offset2D off1 = new Offset2D(1.0, 2.0);
        Offset2D off2 = (Offset2D) TestUtils.serialized(off1);
        assertEquals(off1, off2);
    }
}
