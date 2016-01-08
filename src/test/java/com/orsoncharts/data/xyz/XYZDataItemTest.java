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

package com.orsoncharts.data.xyz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link XYZDataItem} class.
 */
public class XYZDataItemTest {
    
    @Test
    public void testEquals() {
        XYZDataItem d1 = new XYZDataItem(1.0, 2.0, 3.0);
        XYZDataItem d2 = new XYZDataItem(1.0, 2.0, 3.0);
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1 = new XYZDataItem(1.1, 2.0, 3.0);
        assertFalse(d1.equals(d2));
        d2 = new XYZDataItem(1.1, 2.0, 3.0);
        assertTrue(d1.equals(d2));

        d1 = new XYZDataItem(1.1, 2.2, 3.0);
        assertFalse(d1.equals(d2));
        d2 = new XYZDataItem(1.1, 2.2, 3.0);
        assertTrue(d1.equals(d2));

        d1 = new XYZDataItem(1.1, 2.2, 3.3);
        assertFalse(d1.equals(d2));
        d2 = new XYZDataItem(1.1, 2.2, 3.3);
        assertTrue(d1.equals(d2));
    }
    
    @Test
    public void testSerialization() {
        XYZDataItem d1 = new XYZDataItem(1.0, 2.0, 3.0);
        XYZDataItem d2 = (XYZDataItem) TestUtils.serialized(d1);
        assertEquals(d1, d2);
    }

}
