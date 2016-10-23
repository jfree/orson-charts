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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.orsoncharts.TestUtils;
import com.orsoncharts.data.Series3DChangeEvent;
import com.orsoncharts.data.Series3DChangeListener;

/**
 * Tests for the {@link XYZSeries} class.
 */
public class XYZSeriesTest implements Series3DChangeListener {
    
    private static final double EPSILON = 0.00000001;
    
    private Series3DChangeEvent lastEvent;
    
    @Test
    public void testGeneral() {
        XYZSeries<String> s = new XYZSeries<String>("S1");
        assertEquals("S1", s.getKey());
        assertEquals(0, s.getItemCount());
        
        s.add(1.0, 2.0, 3.0);
        assertEquals(1, s.getItemCount());
        assertEquals(1.0, s.getXValue(0), EPSILON);
        assertEquals(2.0, s.getYValue(0), EPSILON);
        assertEquals(3.0, s.getZValue(0), EPSILON);

        s.add(4.0, 5.0, 6.0);
        assertEquals(2, s.getItemCount());
        assertEquals(4.0, s.getXValue(1), EPSILON);
        assertEquals(5.0, s.getYValue(1), EPSILON);
        assertEquals(6.0, s.getZValue(1), EPSILON);
        
        s.remove(0);
        assertEquals(1, s.getItemCount());
        assertEquals(4.0, s.getXValue(0), EPSILON);
        assertEquals(5.0, s.getYValue(0), EPSILON);
        assertEquals(6.0, s.getZValue(0), EPSILON);        
    }
    
    @Test
    public void testEventNotification() {
        XYZSeries<String> s = new XYZSeries<String>("S1");
        this.lastEvent = null;
        s.addChangeListener(this);
        s.add(1.0, 2.0, 3.0);
        assertNotNull(this.lastEvent);
        
        this.lastEvent = null;
        s.remove(0);
        assertNotNull(this.lastEvent);
    }

    /**
     * Tests for the equals() method.
     */
    @Test
    public void testEquals() {
        XYZSeries<String> s1 = new XYZSeries<String>("S");
        XYZSeries<String> s2 = new XYZSeries<String>("S");
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
        
        // key
        s1 = new XYZSeries<String>("SS");
        assertFalse(s1.equals(s2));
        s2 = new XYZSeries<String>("SS");
        assertTrue(s1.equals(s2));
        
        // data items
        s1.add(1.0, 2.0, 3.0);
        assertFalse(s1.equals(s2));
        s2.add(1.0, 2.0, 3.0);
        assertTrue(s1.equals(s2));
    }

    /**
     * Checks for serialization support.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSerialization() {
        XYZSeries<String> s1 = new XYZSeries<String>("S");
        XYZSeries<String> s2 = (XYZSeries) TestUtils.serialized(s1);
        assertEquals(s1, s2);

        s1.add(1.0, 2.0, 3.0);
        s2 = (XYZSeries) TestUtils.serialized(s1);
        assertEquals(s1, s2);
    }

    @Override
    public void seriesChanged(Series3DChangeEvent event) {
        this.lastEvent = event;
    }

}
