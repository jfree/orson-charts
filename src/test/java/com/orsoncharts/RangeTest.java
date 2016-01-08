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

package com.orsoncharts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Some tests for the {@link Range} class.
 */
public class RangeTest {

    @Test
    public void testEquals() {
        Range range1 = new Range(0, 10);
        Range range2 = new Range(0, 10);
        assertEquals(range1, range2);
    
        range1 = new Range(2, 4);
        assertNotEquals(range1, range2);
        range2 = new Range(2, 4);
        assertEquals(range1, range2);
    }
   
    @Test
    public void testContains() {
        Range r = new Range(0, 10);
        assertFalse(r.contains(-0.5));
        assertTrue(r.contains(0.0));
        assertTrue(r.contains(5.0));
        assertTrue(r.contains(10.0));
        assertFalse(r.contains(10.5));
    }
    
    @Test
    public void testIntersects() {
        Range r = new Range(0, 10);
        assertFalse(r.intersects(-0.5, -0.1));
        assertTrue(r.intersects(-0.5, 0.0));
        assertTrue(r.intersects(0.0, 5.0));
        assertTrue(r.intersects(0.0, 10.0));
        assertTrue(r.intersects(0.0, 11.0));
        assertTrue(r.intersects(5.0, 9.0));
        assertTrue(r.intersects(5.0, 10.0));
        assertTrue(r.intersects(5.0, 11.0));
        assertTrue(r.intersects(10.0, 11.0));
        assertFalse(r.intersects(10.5, 11.0));        
    }
    
    private static final double EPSILON = 0.0000001;
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization() {
        Range r1 = new Range(0, 10);
        Range r2 = (Range) TestUtils.serialized(r1);
        assertEquals(r1, r2);
    }
}
