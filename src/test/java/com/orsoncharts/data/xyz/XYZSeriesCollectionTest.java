/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2015, by Object Refinery Limited.  All rights reserved.
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
 * Tests for the {@link XYZSeriesCollection} class.
 */
public class XYZSeriesCollectionTest {
    
    @Test
    public void testEquals() {
        XYZSeriesCollection c1 = new XYZSeriesCollection();
        XYZSeriesCollection c2 = new XYZSeriesCollection();
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(null));
    }
    
    @Test
    public void testSerialization() {
        XYZSeries s1 = new XYZSeries("S");
        s1.add(1.0, 2.0, 3.0);
        XYZSeriesCollection c1 = new XYZSeriesCollection();
        c1.add(s1);
        XYZSeriesCollection c2 = (XYZSeriesCollection) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    @Test
    public void checkToString() {
        XYZSeriesCollection c = new XYZSeriesCollection();
        assertEquals("[]", c.toString());
        
        XYZSeries s1 = new XYZSeries("S1");
        c.add(s1);
        assertEquals("[[\"S1\", []]]", c.toString());
        
        s1.add(1.0, 2.0, 3.0);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0]]]]", c.toString());
     
        s1.add(4.0, 5.0, 6.0);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]]]", 
                c.toString());
        
        XYZSeries s2 = new XYZSeries("S2");
        c.add(s2);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]], "
                + "[\"S2\", []]]", c.toString());
        
        s2.add(7.0, Double.NaN, 9.0);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]], " 
                + "[\"S2\", [[7.0, null, 9.0]]]]", c.toString());
    }
    
}
